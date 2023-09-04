package ltd.matrixstudios.alchemist.commands.rank

import co.aikar.commands.BaseCommand
import co.aikar.commands.CommandHelp
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.commands.rank.menu.RankEditor
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.profiles.permissions.packet.PermissionUpdateAllPacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.cache.refresh.RefreshRankPacket
import ltd.matrixstudios.alchemist.commands.rank.menu.RankListMenu
import ltd.matrixstudios.alchemist.commands.rank.menu.RankScanMenu
import ltd.matrixstudios.alchemist.commands.rank.menu.filter.RankListFilter
import ltd.matrixstudios.alchemist.models.ranks.scope.RankScope
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.packets.StaffAuditPacket
import ltd.matrixstudios.alchemist.redis.cache.mutate.UpdateGrantCacheRequest
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("rank")
class GenericRankCommands : BaseCommand() {

    @HelpCommand
    @CommandPermission("rank.admin")
    @Description("Display the help syntax.")
    fun help(help: CommandHelp) {
        help.showHelp()
    }

    @Subcommand("scan")
    @Description("Scan all available profiles to find people with a certain rank.")
    @CommandPermission("rank.admin")
    fun scan(player: Player) {
        RankScanMenu(player).updateMenu()
    }

    @Subcommand("setscope")
    @Description("Set the servers that a rank will be visible and applicable on.")
    @CommandPermission("rank.admin")
    fun setscope(sender: CommandSender, @Name("rank")rank: Rank, @Name("scope") rankScope: RankScope)
    {
        rank.scope = rankScope
        RankService.save(rank).thenAccept {
            AsynchronousRedisSender.send(RefreshRankPacket())
        }

        sender.sendMessage(Chat.format("&aUpdated the rank scope of " + rank.color + rank.displayName + " &ato &f" + if (rank.getRankScope().global)
            "Global" else rank.getRankScope().servers.joinToString(", "))
        )
    }

    @Subcommand("rename-id")
    @Description("Rename the id of a rank to a new one.")
    @CommandPermission("rank.admin")
    fun renameId(sender: CommandSender, @Name("rank")rankString: String, @Name("id") id: String) {
        val rank = RankService.byIdAnyCase(rankString)

        if (rank == null)
        {
            sender.sendMessage(Chat.format("&cThis rank doesn't exist!"))
            return
        }

        //rank logic
        RankService.delete(rank)
        val oldId = rank.id
        sender.sendMessage(Chat.format("&eOld Id: &f$oldId"))
        rank.id = id.toLowerCase()
        sender.sendMessage(Chat.format("&eNew Id: &f${id.toLowerCase()}"))

        RankService.save(rank)
        AsynchronousRedisSender.send(RefreshRankPacket())

        //grants
        RankGrantService.findByRank(oldId).whenComplete { g, e ->
            for (grant in g) {
                grant.rankId = id.toLowerCase()
                grant.rank = id.toLowerCase()

                //only if they are in the cache to prevent loading every single grant into this list
                if (RankGrantService.playerGrants.containsKey(grant.target)) {
                    AsynchronousRedisSender.send(UpdateGrantCacheRequest(grant.target))
                }

                RankGrantService.save(grant)
            }

            sender.sendMessage(Chat.format("&aChanged the id of &f" + g.size + " &agrants"))
        }
    }

    @Subcommand("inheritance")
    @Description("Add groups in which this rank will inherit all permisisons from.")
    @CommandPermission("rank.admin")
    fun inheritance(sender: CommandSender, @Name("name") name: String) {
        if (RankService.byId(name.toLowerCase()) == null) {
            sender.sendMessage(Chat.format("&cThis rank doesn't exist"))
            return
        }

        val rank = RankService.byId(name.toLowerCase())!!
        sender.sendMessage(Chat.format("&7&m-------------------------"))
        sender.sendMessage(Chat.format(rank.color + rank.displayName + " &eInheritance"))
        sender.sendMessage(" ")
        val parents = rank.parents.map { RankService.byId(it) }.filterNotNull()

        for (rank2 in parents) {
            sender.sendMessage(Chat.format("&7• &r" + rank2.color + rank2.displayName))
        }

        sender.sendMessage(Chat.format("&7&m-------------------------"))
    }

    @Subcommand("create")
    @Description("Create a new rank.")
    @CommandPermission("rank.admin")
    fun create(sender: CommandSender, @Name("name") name: String) {
        if (RankService.byId(name.toLowerCase()) != null) {
            sender.sendMessage(Chat.format("&cThis rank already exists"))
            return
        }

        val rank = Rank(name.lowercase(), name, name, 1, ArrayList(), ArrayList(), "", "&f", false)

        RankService.save(rank)

        AsynchronousRedisSender.send(RefreshRankPacket())

        sender.sendMessage(Chat.format("&aCreated the &7$name &arank"))
        AsynchronousRedisSender.send(StaffAuditPacket("&b[Audit] &3Added a new rank with the id &b$name"))
    }

    @Subcommand("list")
    @Description("Show a list of all loaded ranks.")
    @CommandPermission("rank.admin")
    fun list(sender: CommandSender) {
        sender.sendMessage(Chat.format("&7&m--------------------------"))
        sender.sendMessage(Chat.format("&eLoaded Ranks &7(" + RankService.ranks.size + ")"))
        for (rank in RankService.getAllRanksInOrder()) {
            val message = rank.color + rank.displayName + " &f[Priority: " + rank.weight + "] &7(" + rank.id + ")"

            sender.sendMessage(Chat.format(message))
        }
        sender.sendMessage(Chat.format("&7&m--------------------------"))
        if (sender is Player) {
            RankListMenu(sender, RankService.getRanksInOrder().toMutableList(), RankListFilter.ALL).updateMenu()
        }
    }

    @Subcommand("editor")
    @Description("Open the rank editor menu.")
    @CommandPermission("rank.admin")
    fun editor(player: Player) {
        RankEditor(player).updateMenu()
    }

    @Subcommand("delete")
    @Description("Delete a rank with a given name.")
    @CommandPermission("rank.admin")
    fun delete(sender: CommandSender, @Name("rank") name: String) {
        if (RankService.byId(name.toLowerCase()) == null) {
            sender.sendMessage(Chat.format("&cThis rank doesn't exist"))
            return
        }

        RankService.ranks.remove(name.toLowerCase())
        RankService.handler.delete(name.toLowerCase())

        AsynchronousRedisSender.send(RefreshRankPacket())
        sender.sendMessage(Chat.format("&cDeleted the rank &f$name"))
        AsynchronousRedisSender.send(StaffAuditPacket("&b[Audit] &3Removed a rank with the id &b$name"))
    }

    @Subcommand("info")
    @Description("Show detailed information about a rank.")
    @CommandPermission("rank.admin")
    fun info(sender: CommandSender, @Name("rank") name: String) {
        if (RankService.byId(name.toLowerCase()) == null) {
            sender.sendMessage(Chat.format("&cThis rank doesnt exist"))
            return
        }


        val rank = RankService.byId(name.toLowerCase())!!

        sender.sendMessage(Chat.format("&7&m--------------------------"))
        sender.sendMessage(Chat.format(rank.color + rank.displayName + " &7❘ &fInformation"))
        sender.sendMessage(Chat.format("&7&m--------------------------"))
        sender.sendMessage(Chat.format("&6Weight: &f" + rank.weight))
        sender.sendMessage(Chat.format("&6Prefix: &f" + rank.prefix))
        sender.sendMessage(Chat.format("&6Color: " + rank.color + "This"))
        sender.sendMessage(Chat.format("&6Permissions: &f" + rank.permissions.toString()))
        sender.sendMessage(Chat.format("&6Staff Rank: &f" + rank.staff))
        sender.sendMessage(Chat.format("&6Default Rank: &f" + rank.default))
        sender.sendMessage(Chat.format("&6Scopes: &f" + if (rank.getRankScope().global) "Global" else rank.getRankScope().servers.joinToString(", ")))
        sender.sendMessage(" ")
        sender.sendMessage(Chat.format("&6Parents &7(${rank.parents.size}):"))
        val parents = rank.parents.map { RankService.byId(it) }.filterNotNull()

        for (rank2 in parents) {
            sender.sendMessage(Chat.format("&7• &r" + rank2.color + rank2.displayName))
        }
        sender.sendMessage(Chat.format("&7&m--------------------------"))
    }

    @Subcommand("module")
    @Description("Manually edit certain internal and external features of a rank.")
    @CommandPermission("rank.admin")
    fun module(
        sender: CommandSender,
        @Name("rank") name: String,
        @Name("module") module: String,
        @Name("argument") arg: String
    ) {
        if (RankService.byId(name.toLowerCase()) == null) {
            sender.sendMessage(Chat.format("&cThis rank doesnt exist"))
            return
        }

        val rank = RankService.byId(name.toLowerCase())!!

        when (module) {
            "prefix" -> {
                rank.prefix = arg
                RankService.save(rank)
                AsynchronousRedisSender.send(RefreshRankPacket())

                sender.sendMessage(Chat.format("&aUpdated the prefix of &f" + rank.color + rank.displayName))
            }

            "color" -> {
                rank.color = arg
                RankService.save(rank)
                AsynchronousRedisSender.send(RefreshRankPacket())

                sender.sendMessage(Chat.format("&aUpdated the color of &f" + rank.color + rank.displayName))
            }

            "weight" -> {
                rank.weight = arg.toInt()
                RankService.save(rank)
                AsynchronousRedisSender.send(RefreshRankPacket())

                sender.sendMessage(Chat.format("&aUpdated the weight of &f" + rank.color + rank.displayName))
            }

            "woolcolor" -> {
                rank.woolColor = arg
                RankService.save(rank)
                AsynchronousRedisSender.send(RefreshRankPacket())

                sender.sendMessage(Chat.format("&aUpdated the wool color of &f" + rank.color + rank.displayName))
            }

            "parent" -> {
                if (rank.parents.contains(arg.toLowerCase()) || rank.parents.contains(arg)) {
                    rank.parents.removeIf { it.equals(arg, ignoreCase = true) }
                    sender.sendMessage(Chat.format("&cRemoved the parent &f$arg &cfrom the rank " + rank.color + rank.displayName))
                } else {
                    rank.parents.add(arg.toLowerCase())
                    sender.sendMessage(Chat.format("&aAdded the parent &f$arg &ato the rank " + rank.color + rank.displayName))
                }

                RankService.save(rank)

                AsynchronousRedisSender.send(PermissionUpdateAllPacket())
                AsynchronousRedisSender.send(RefreshRankPacket())

            }

            "displayname" -> {
                rank.displayName = arg
                RankService.save(rank)
                AsynchronousRedisSender.send(RefreshRankPacket())

                sender.sendMessage(Chat.format("&aUpdated the display name of &f" + rank.color + rank.displayName))
            }


            "permission" -> {
                if (rank.permissions.contains(arg)) {
                    rank.permissions.remove(arg)
                    sender.sendMessage(Chat.format("&cRemoved the permission &f$arg &cfrom the rank " + rank.color + rank.displayName))
                } else {
                    rank.permissions.add(arg)
                    sender.sendMessage(Chat.format("&aAdded the permission &f$arg &ato the rank " + rank.color + rank.displayName))
                }

                RankService.save(rank)

                AsynchronousRedisSender.send(PermissionUpdateAllPacket())
                AsynchronousRedisSender.send(RefreshRankPacket())
            }

            "default" -> {
                rank.default = arg.toBoolean()
                RankService.save(rank)
                AsynchronousRedisSender.send(RefreshRankPacket())

                sender.sendMessage(Chat.format("&aUpdated the default status of &f" + rank.color + rank.displayName))
            }


            "staff" -> {
                rank.staff = arg.toBoolean()
                RankService.save(rank)
                AsynchronousRedisSender.send(RefreshRankPacket())

                sender.sendMessage(Chat.format("&aUpdated the staff status of &f" + rank.color + rank.displayName))
            }

            else -> {
                sender.sendMessage(Chat.format("&cInvalid module type. Please select: permission, staff, default, parent, weight, color, prefix, woolcolor, or displayname."))
            }

        }


    }
}