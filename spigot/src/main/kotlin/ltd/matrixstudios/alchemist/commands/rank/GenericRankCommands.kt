package ltd.matrixstudios.alchemist.commands.rank

import co.aikar.commands.BaseCommand
import co.aikar.commands.CommandHelp
import co.aikar.commands.ConditionFailedException
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.commands.rank.menu.RankEditorMenu
import ltd.matrixstudios.alchemist.commands.rank.menu.filter.RankListFilter
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.models.ranks.scope.RankScope
import ltd.matrixstudios.alchemist.packets.StaffAuditPacket
import ltd.matrixstudios.alchemist.profiles.permissions.packet.PermissionUpdateAllPacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.cache.mutate.UpdateGrantCacheRequest
import ltd.matrixstudios.alchemist.redis.cache.refresh.RefreshRankPacket
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

@CommandAlias("rank")
class GenericRankCommands : BaseCommand()
{

    @HelpCommand
    @CommandPermission("rank.admin")
    @Description("Display the help syntax.")
    fun help(help: CommandHelp)
    {
        help.showHelp()
    }

    @Subcommand("scan")
    @Description("Scan all available profiles to find people with a certain rank")
    @CommandPermission("rank.admin")
    fun scan(player: Player, @Name("rank") rank: Rank)
    {
        player.sendMessage(Chat.format("&7Loading current rank scan..."))
        RankService.scanRank(rank).thenAccept { profiles ->
            player.sendMessage(
                Chat.format("&eUsers with active " + rank.color + rank.displayName + " &erank (&6${profiles.size}&e): ${
                    profiles.joinToString { "${it.getCurrentRank().color + it.username}&6," }
                }")
            )
        }
    }


    @Subcommand("create")
    @Description("Create a new rank")
    @CommandPermission("rank.admin")
    fun create(sender: CommandSender, @Name("name") name: String)
    {
        val existantRank = RankService.byIdAnyCase(name)
        if (existantRank != null)
        {
            sender.sendMessage(Chat.format("&cRank &r${existantRank.color + existantRank.displayName}&r &calready exists."))
            return
        }

        val rank = Rank(name.lowercase(), name, name, 1, ArrayList(), ArrayList(), "", "&f", false)

        RankService.save(rank)
        AsynchronousRedisSender.send(RefreshRankPacket())

        sender.sendMessage(Chat.format("&aRank &f$name&r &asuccessfully created."))
        AsynchronousRedisSender.send(StaffAuditPacket("&b[Audit] &3Rank &f$name&r &3successfully &acreated&3."))
    }

    @Subcommand("list|editor")
    @Description("View a list of ranks and edit them")
    @CommandPermission("rank.admin")
    fun list(sender: CommandSender)
    {
        if (sender is Player)
        {
            RankEditorMenu(sender, RankService.getRanksInOrder().toList(), RankListFilter.ALL).updateMenu()
        } else
        {
            sender.sendMessage(Chat.format("&7&m--------------------------"))
            sender.sendMessage(Chat.format("&eLoaded Ranks &7(" + RankService.ranks.size + ")"))
            for (rank in RankService.getAllRanksInOrder())
            {
                val message = rank.color + rank.displayName + " &f[Priority: " + rank.weight + "] &7(" + rank.id + ")"

                sender.sendMessage(Chat.format(message))
            }
            sender.sendMessage(Chat.format("&7&m--------------------------"))
        }
    }

    @Subcommand("set-discord-id")
    @Description("Set Discord role ID for a rank.")
    @CommandPermission("rank.admin")
    fun setDiscordId(sender: CommandSender, @Name("rank") foundRank: Rank, @Name("role ID") discordRoleId: String)
    {
        foundRank.discordRoleId = discordRoleId
        RankService.save(foundRank)
        sender.sendMessage(Chat.format("&aThe discord role of the rank has been set to &f$discordRoleId"))
    }


    @Subcommand("delete-discord-id")
    @Description("Delete Discord role ID for a rank.")
    @CommandPermission("rank.admin")
    fun deleteDiscordId(sender: CommandSender, @Name("rank") foundRank: Rank)
    {
        if (foundRank.discordRoleId.isNullOrEmpty())
        {
            sender.sendMessage(Chat.format("&cThe rank ${foundRank.displayName} does not have a Discord role ID set."))
            return
        }
        foundRank.discordRoleId = null
        RankService.save(foundRank)

        sender.sendMessage(Chat.format("&cThe discord role of the rank has been removed"))
    }

    @Subcommand("delete")
    @Description("Delete a rank with a given name")
    @CommandPermission("rank.admin")
    fun delete(sender: CommandSender, @Name("rank") rank: Rank)
    {
        RankService.delete(rank).whenComplete { _, _ ->
            sender.sendMessage(Chat.format("&aRank &r${rank.color + rank.displayName}&r &asuccessfully deleted."))

            AsynchronousRedisSender.send(RefreshRankPacket())
            AsynchronousRedisSender.send(StaffAuditPacket("&b[Audit] &3Rank &f${rank.color + rank.displayName}&r &3has been &cdeleted&3."))
        }
    }

    /**
     * Removed in favor of using the /rank list menu instead.
     * Keeping the code in here in case 98ping wishes to revert back to the old system.
     *
     * @Subcommand("info")
     * @Description("Show detailed information about a rank")
     *     @CommandPermission("rank.admin")
     *     fun info(sender: CommandSender, @Name("rank") rank: Rank)
     *     {
     *         sender.sendMessage(Chat.format("&7&m--------------------------"))
     *         sender.sendMessage(Chat.format(rank.color + rank.displayName + " &7❘ &fInformation"))
     *         sender.sendMessage(Chat.format("&7&m--------------------------"))
     *         sender.sendMessage(Chat.format("&6Weight: &f" + rank.weight))
     *         sender.sendMessage(Chat.format("&6Prefix: &f" + rank.prefix))
     *         sender.sendMessage(Chat.format("&6Color: " + rank.color + "This"))
     *         sender.sendMessage(Chat.format("&6Permissions: &f" + rank.permissions.toString()))
     *         sender.sendMessage(Chat.format("&6Staff Rank: &f" + rank.staff))
     *         sender.sendMessage(Chat.format("&6Default Rank: &f" + rank.default))
     *         sender.sendMessage(
     *             Chat.format(
     *                 "&6Scopes: &f" + if (rank.getRankScope().global) "Global" else rank.getRankScope().servers.joinToString(
     *                     ", "
     *                 )
     *             )
     *         )
     *         sender.sendMessage(" ")
     *         sender.sendMessage(Chat.format("&6Parents &7(${rank.parents.size}):"))
     *         val parents = rank.parents.map { RankService.byId(it) }.filterNotNull()
     *
     *         for (rank2 in parents)
     *         {
     *             sender.sendMessage(Chat.format("&7• &r" + rank2.color + rank2.displayName))
     *         }
     *         sender.sendMessage(Chat.format("&7&m--------------------------"))
     *
     *     @Subcommand("editor|list")
     *     @Description("Open the rank editor menu")
     *     @CommandPermission("rank.admin")
     *     fun editor(player: Player)
     *     {
     *         RankEditor(player).updateMenu()
     *     }
     *
     */

    @Subcommand("setprefix|prefix")
    @CommandPermission("rank.admin")
    @Description("Set the prefix of a rank")
    fun setRankPrefix(sender: CommandSender, @Name("rank") rank: Rank, @Name("prefix") newPrefix: String)
    {
        rank.prefix = newPrefix
        RankService.save(rank)
        AsynchronousRedisSender.send(RefreshRankPacket())

        sender.sendMessage(Chat.format("&aUpdated prefix of rank &f${rank.color + rank.displayName}&r &ato \"&r$newPrefix&r&a\"."))
    }

    @Subcommand("setcolor|color")
    @CommandPermission("rank.admin")
    @Description("Set the color of a rank")
    fun setRankColor(sender: CommandSender, @Name("rank") rank: Rank, @Single @Name("color") newColor: String)
    {
        val oldColor = rank.color
        rank.color = newColor
        RankService.save(rank)
        AsynchronousRedisSender.send(RefreshRankPacket())

        sender.sendMessage(
            Chat.format("&aUpdated color of rank &f${oldColor + rank.displayName}&r &ato \"$newColor") + newColor + Chat.format(
                "&r&a\"."
            )
        )
    }

    @Subcommand("setweight|weight")
    @CommandPermission("rank.admin")
    @Description("Set the weight of a rank")
    fun setRankWeight(sender: CommandSender, @Name("rank") rank: Rank, @Name("weight") newWeight: Int)
    {
        rank.weight = newWeight
        RankService.save(rank)
        AsynchronousRedisSender.send(RefreshRankPacket())

        sender.sendMessage(Chat.format("&aUpdated weight of rank &f${rank.color + rank.displayName}&r &ato &f$newWeight&a."))
    }

    @Subcommand("setwoolcolor|woolcolor")
    @CommandPermission("rank.admin")
    @Description("Set the wool color of a rank")
    fun setRankWoolColor(sender: CommandSender, @Name("rank") rank: Rank, @Name("wool color") newWoolColor: String)
    {
        rank.woolColor = newWoolColor
        RankService.save(rank)
        AsynchronousRedisSender.send(RefreshRankPacket())

        val woolColor = AlchemistAPI.getWoolColorStrict(newWoolColor)
            ?: throw ConditionFailedException("The given wool color does not exist.")

        sender.sendMessage(
            Chat.format(
                "&aUpdated wool color of rank &r${rank.color + rank.displayName}&r &ato ${
                    newWoolColor + woolColor.name
                        .lowercase()
                        .replace("_", " ")
                }&r&a."
            )
        )
    }

    @Subcommand("parent|inherit add")
    @CommandPermission("rank.admin")
    @Description("Add a parent to a rank")
    fun addRankParent(sender: CommandSender, @Name("rank") rank: Rank, @Name("parent") parentRank: Rank)
    {
        if (rank.parents.contains(parentRank.id))
        {
            throw ConditionFailedException("&cRank &f${parentRank.color + parentRank.displayName}&r &cis already a parent of rank &f${rank.color + rank.displayName}&r&c.")
        }

        rank.parents.add(parentRank.id)
        RankService.save(rank)
        AsynchronousRedisSender.send(RefreshRankPacket())
        AsynchronousRedisSender.send(PermissionUpdateAllPacket())

        sender.sendMessage(Chat.format("&aAdded parent &f${parentRank.color + parentRank.displayName}&r &ato rank &r${rank.color + rank.displayName}&r&a."))
    }

    @Subcommand("parent|inherit remove")
    @CommandPermission("rank.admin")
    @Description("Remove a parent from a rank")
    fun removeRankParent(sender: CommandSender, @Name("rank") rank: Rank, @Name("parent") parentRank: Rank)
    {
        if (!rank.parents.contains(parentRank.id))
        {
            throw ConditionFailedException("&cRank &f${parentRank.color + parentRank.displayName}&r &cis not a parent of rank &f${rank.color + rank.displayName}&r&c.")
        }

        rank.parents.remove(parentRank.id)
        RankService.save(rank)
        AsynchronousRedisSender.send(RefreshRankPacket())
        AsynchronousRedisSender.send(PermissionUpdateAllPacket())

        sender.sendMessage(Chat.format("&aRemoved parent &f${parentRank.color + parentRank.displayName}&r &afrom rank &f${rank.color + rank.displayName}&r&a."))
    }

    @Subcommand("permission|perm add")
    @CommandPermission("rank.admin")
    @Description("Add a permission to a rank")
    fun addRankPermission(sender: CommandSender, @Name("rank") rank: Rank, @Name("permission") permission: String)
    {
        val lowercasePermission = permission.lowercase()

        if (rank.permissions.contains(lowercasePermission))
        {
            throw ConditionFailedException("Rank &r${rank.color + rank.displayName}&r &calready has the permission &f$lowercasePermission&r&c.")
        }

        rank.permissions.add(lowercasePermission)
        RankService.save(rank)
        AsynchronousRedisSender.send(RefreshRankPacket())
        AsynchronousRedisSender.send(PermissionUpdateAllPacket())

        sender.sendMessage(Chat.format("&aAdded permission &f$lowercasePermission&r &ato rank &r${rank.color + rank.displayName}&r&a."))
    }

    @Subcommand("permission|perm remove")
    @CommandPermission("rank.admin")
    @Description("Remove a permission from a rank")
    fun removeRankPermission(sender: CommandSender, @Name("rank") rank: Rank, @Name("permission") permission: String)
    {
        val lowercasePermission = permission.lowercase()

        if (!rank.permissions.contains(lowercasePermission))
        {
            throw ConditionFailedException("Rank &r${rank.color + rank.displayName}&r &cdoes not have the permission &f$lowercasePermission&r&c.")
        }

        rank.permissions.remove(lowercasePermission)
        RankService.save(rank)
        AsynchronousRedisSender.send(RefreshRankPacket())
        AsynchronousRedisSender.send(PermissionUpdateAllPacket())

        sender.sendMessage(Chat.format("&aRemoved permission &f$lowercasePermission&r &afrom rank &r${rank.color + rank.displayName}&r&a."))
    }

    @Subcommand("setdisplayname|displayname")
    @CommandPermission("rank.admin")
    @Description("Set the display name of a rank")
    fun setRankDisplayName(
        sender: CommandSender,
        @Name("rank") rank: Rank,
        @Name("display name") newDisplayName: String
    )
    {
        val oldDisplayName = rank.displayName

        rank.displayName = newDisplayName
        RankService.save(rank)
        AsynchronousRedisSender.send(RefreshRankPacket())

        sender.sendMessage(Chat.format("&aUpdated display name of rank &f${rank.color + oldDisplayName}&r &ato \"&f$newDisplayName&r&a\"."))
    }

    @Subcommand("setdefault|default")
    @CommandPermission("rank.admin")
    @Description("Change whether a rank is the default rank")
    fun setRankDefaultState(sender: CommandSender, @Name("rank") rank: Rank, @Name("state") newDefaultState: Boolean)
    {
        rank.default = newDefaultState
        RankService.save(rank)
        AsynchronousRedisSender.send(RefreshRankPacket())

        sender.sendMessage(Chat.format("&aUpdated default state of rank &r${rank.color + rank.displayName}&r &ato ${if (newDefaultState) "&a&ltrue" else "&c&lfalse"}&r&a."))
    }

    @Subcommand("setstaff|staff")
    @CommandPermission("rank.admin")
    @Description("Change whether a rank is a staff rank")
    fun setRankStaffState(sender: CommandSender, @Name("rank") rank: Rank, @Name("state") newStaffState: Boolean)
    {
        rank.staff = newStaffState
        RankService.save(rank)
        AsynchronousRedisSender.send(RefreshRankPacket())

        sender.sendMessage(Chat.format("&aUpdated staff state of rank &r${rank.color + rank.displayName}&r &ato ${if (newStaffState) "&a&ltrue" else "&c&lfalse"}&r&a."))
    }

    @Subcommand("setscope|scope")
    @Description("Set the servers that a rank will be visible and applicable to")
    @CommandPermission("rank.admin")
    fun setRankScope(sender: CommandSender, @Name("rank") rank: Rank, @Name("scope") rankScope: RankScope)
    {
        if (!rankScope.global && rankScope.servers.isEmpty())
        {
            throw ConditionFailedException("There were no applicable scopes found in the given arguments.")
        }

        rank.scope = rankScope
        RankService.save(rank).thenAccept {
            AsynchronousRedisSender.send(RefreshRankPacket())

            sender.sendMessage(
                Chat.format(
                    "&aUpdated scope of rank &r${rank.color + rank.displayName}&r &ato &f${
                        if (rankScope.global)
                        {
                            "Global"
                        } else
                        {
                            "${rankScope.servers.joinToString("&a, &f")} &7(${rankScope.servers.size} scopes total)"
                        }
                    }&r&a."
                )
            )
        }
    }

    @Subcommand("rename|rename-id")
    @Description("Change the ID of a rank")
    @CommandPermission("rank.admin")
    fun renameRank(sender: CommandSender, @Name("rank") rank: Rank, @Name("new name") @Single id: String)
    {
        val existentRank = RankService.byIdAnyCase(id)

        if (existentRank != null)
        {
            throw ConditionFailedException("Rank &f${rank.color + rank.displayName}&r &calready exists with the given ID.")
        }

        //rank logic
        RankService.delete(rank)
        val oldId = rank.id
        sender.sendMessage(Chat.format("&eOutdated Identifier: &f$oldId"))
        rank.id = id.lowercase(Locale.getDefault())
        sender.sendMessage(Chat.format("&eNew Identifier: &f${id.lowercase(Locale.getDefault())}"))

        RankService.save(rank)
        AsynchronousRedisSender.send(RefreshRankPacket())

        //grants
        RankGrantService.findByRank(oldId).whenComplete { g, e ->
            for (grant in g)
            {
                grant.rankId = id.lowercase(Locale.getDefault())
                grant.rank = id.lowercase(Locale.getDefault())

                //only if they are in the cache to prevent loading every single grant into this list
                if (RankGrantService.playerGrants.containsKey(grant.target))
                {
                    AsynchronousRedisSender.send(UpdateGrantCacheRequest(grant.target))
                }

                RankGrantService.save(grant)
            }

            sender.sendMessage(Chat.format("&aChanged the id of &f" + g.size + " &agrants"))
        }
    }
}
