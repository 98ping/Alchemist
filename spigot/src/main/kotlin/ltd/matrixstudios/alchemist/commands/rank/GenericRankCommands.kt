package ltd.matrixstudios.alchemist.commands.rank

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.permissions.packet.PermissionUpdateAllPacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.staff.packets.StaffAuditPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("rank")
class GenericRankCommands : BaseCommand() {

    @HelpCommand
    @CommandPermission("rank.admin")
    fun help(sender: CommandSender) {
        sender.sendMessage(Chat.format("&7&m-------------------------"))
        sender.sendMessage(Chat.format("&6&lRank Help"))
        sender.sendMessage(" ")
        sender.sendMessage(Chat.format("&e/rank info &f<rank>"))
        sender.sendMessage(Chat.format("&e/rank create &f<rank>"))
        sender.sendMessage(Chat.format("&e/rank delete &f<rank>"))
        sender.sendMessage(Chat.format("&e/rank list"))
        sender.sendMessage(Chat.format("&e/rank module &f<rank> <module> <value>"))
        sender.sendMessage(Chat.format("&7&m-------------------------"))
    }

    @Subcommand("create")
    @CommandPermission("rank.admin")
    fun create(sender: CommandSender, @Name("name") name: String) {
        if (RankService.byId(name) != null) {
            sender.sendMessage(Chat.format("&cThis rank already exists"))
            return
        }

        val rank = Rank(name.lowercase(), name, name, 1, ArrayList(), ArrayList(), "", "&f", false)

        RankService.save(rank)

        sender.sendMessage(Chat.format("&aCreated the &7$name &arank"))
        AsynchronousRedisSender.send(StaffAuditPacket("&b[Audit] &3Added a new rank with the name &b$name"))
    }

    @Subcommand("list")
    @CommandPermission("rank.admin")
    fun list(sender: CommandSender) {
        sender.sendMessage(Chat.format("&7&m--------------------------"))
        sender.sendMessage(Chat.format("&eLoaded Ranks &7(" + RankService.getValues().size + ")"))
        for (rank in RankService.getRanksInOrder()) {
            val message = rank.color + rank.displayName + " &f[Priority: " + rank.weight + "]"

            sender.sendMessage(Chat.format(message))
        }
        sender.sendMessage(Chat.format("&7&m--------------------------"))
    }

    @Subcommand("delete")
    @CommandPermission("rank.admin")
    fun delete(sender: CommandSender, @Name("rank") name: String) {
        if (RankService.byId(name) == null) {
            sender.sendMessage(Chat.format("&cThis rank doesnt exist"))
            return
        }

        RankService.handler.delete(name)
        sender.sendMessage(Chat.format("&cDeleted the rank &f$name"))
        AsynchronousRedisSender.send(StaffAuditPacket("&b[Audit] &3Removed a rank with the id &b$name"))
    }

    @Subcommand("info")
    @CommandPermission("rank.admin")
    fun info(sender: CommandSender, @Name("rank") name: String) {
        if (RankService.byId(name) == null) {
            sender.sendMessage(Chat.format("&cThis rank doesnt exist"))
            return
        }

        val rank = RankService.byId(name)!!

        sender.sendMessage(Chat.format("&7&m--------------------------"))
        sender.sendMessage(Chat.format(rank.color + rank.displayName))
        sender.sendMessage(" ")
        sender.sendMessage(Chat.format("&eWeight: &f" + rank.weight))
        sender.sendMessage(Chat.format("&ePrefix: &f" + rank.prefix))
        sender.sendMessage(Chat.format("&eColor: " + rank.color + "This"))
        sender.sendMessage(Chat.format("&eParents: &f" + rank.parents.size))
        sender.sendMessage(Chat.format("&ePermissions: &f" + rank.permissions.size))
        sender.sendMessage(Chat.format("&eStaff Rank: &f" + rank.staff))
        sender.sendMessage(Chat.format("&eDefault Rank: &f" + rank.default))
        sender.sendMessage(Chat.format("&7&m--------------------------"))
    }

    @Subcommand("module")
    @CommandPermission("rank.admin")
    fun module(sender: CommandSender, @Name("rank") name: String, @Name("module")module: String, @Name("argument")arg: String) {
        if (RankService.byId(name) == null) {
            sender.sendMessage(Chat.format("&cThis rank doesnt exist"))
            return
        }

        val rank = RankService.byId(name)!!

        when (module) {
            "prefix" -> {
                rank.prefix = arg
                RankService.save(rank)

                sender.sendMessage(Chat.format("&aUpdated the prefix of &7$name"))
            }

            "color" -> {
                rank.color = arg
                RankService.save(rank)

                sender.sendMessage(Chat.format("&aUpdated the color of &7$name"))
            }

            "weight" -> {
                rank.weight = arg.toInt()
                RankService.save(rank)

                sender.sendMessage(Chat.format("&aUpdated the weight of &7$name"))
            }

            "parent" -> {
                if (rank.parents.contains(arg)) {
                    rank.parents.remove(arg)
                } else rank.parents.add(arg)

                RankService.save(rank)

                AsynchronousRedisSender.send(PermissionUpdateAllPacket())

                sender.sendMessage(Chat.format("&aUpdated the parents of &7$name"))
            }


            "permission" -> {
                if (rank.permissions.contains(arg)) {
                    rank.permissions.remove(arg)
                } else rank.permissions.add(arg)

                RankService.save(rank)

                AsynchronousRedisSender.send(PermissionUpdateAllPacket())

                sender.sendMessage(Chat.format("&aUpdated the permissions of &7$name"))
            }

            "staff" -> {
                rank.staff = arg.toBoolean()
                RankService.save(rank)

                sender.sendMessage(Chat.format("&aUpdated the staff status of &7$name"))
            }
        }




    }
}