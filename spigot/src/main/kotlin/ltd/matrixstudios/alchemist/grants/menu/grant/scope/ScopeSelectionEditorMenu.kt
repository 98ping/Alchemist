package ltd.matrixstudios.alchemist.grants.menu.grant.scope

import ltd.matrixstudios.alchemist.grants.menu.grants.GrantsMenu
import ltd.matrixstudios.alchemist.grants.menu.grants.filter.GrantFilter
import ltd.matrixstudios.alchemist.grants.view.GrantsCommand
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.grant.types.scope.GrantScope
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.PlaceholderButton
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

/**
 * Class created on 6/21/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class ScopeSelectionEditorMenu(
    val player: Player,
    val rank: Rank,
    val target: GameProfile,
    val duration: Long,
    val reason: String,
    val equipped: MutableList<String>,
    val grant: RankGrant,
    val global: Boolean
) : PaginatedMenu(36, player)
{

    override fun getHeaderItems(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        val frame = PlaceholderButton(Material.STAINED_GLASS_PANE, mutableListOf(), " ", 7)
        for (slot in intArrayOf(1, 2, 6, 7)) buttons[slot] = frame

        buttons[3] = SimpleActionButton(
            Material.BEACON, mutableListOf(
                Chat.format(" "),
                Chat.format("&7A global grant applies on every"),
                Chat.format("&7server and ignores the list below."),
                Chat.format(" "),
                Chat.format("&7Currently: " + (if (global) "&a&lGLOBAL" else "&e&lPER-SERVER")),
                Chat.format(" "),
                Chat.format("&e&lClick &eto switch to " + (if (global) "per-server" else "global")),
                Chat.format(" ")
            ), Chat.format("&b&lGlobal Scope"), 0
        ).setBody { player, _, _ ->
            ScopeSelectionEditorMenu(player, rank, target, duration, reason, equipped, grant, !global).updateMenu()
        }

        buttons[4] = PlaceholderButton(
            Material.BOOK, mutableListOf(
                Chat.format(" "),
                Chat.format("&7Player&8: &f" + target.username),
                Chat.format("&7Rank&8: " + rank.color + rank.displayName),
                Chat.format("&7Duration&8: &f" + TimeUtil.millisToRoundedTime(duration)),
                Chat.format("&7Reason&8: &f" + reason),
                Chat.format("&7Scope&8: " + scopeSummary()),
                Chat.format(" ")
            ), Chat.format("&e&lGrant Summary"), 0
        )

        buttons[5] = SimpleActionButton(
            Material.EMERALD, mutableListOf(
                Chat.format(" "),
                Chat.format("&7Save the scope shown in the"),
                Chat.format("&7summary to this grant."),
                Chat.format(" "),
                if (!global && equipped.isEmpty())
                    Chat.format("&cSelect a server or enable global first")
                else
                    Chat.format("&e&lClick &eto save changes"),
                Chat.format(" ")
            ), Chat.format("&a&lFinalize"), 0
        ).setBody { player, _, _ ->
            if (!global && equipped.isEmpty())
            {
                player.sendMessage(Chat.format("&cYou must select a scope to add this grant to"))
                return@setBody
            }

            grant.scope = GrantScope("Manual Scope Editing", equipped, global)
            RankGrantService.save(grant)
            GrantsMenu(
                player,
                target,
                GrantsCommand.getViewableGrants(player, RankGrantService.getFromCache(target.uuid).toMutableList()),
                GrantFilter.ALL
            ).updateMenu()
            player.sendMessage(Chat.format("&eUpdated the scopes of this &6grant"))
        }

        return buttons
    }

    private fun scopeSummary(): String
    {
        return when
        {
            global -> "&bGlobal &7(all servers)"
            equipped.isEmpty() -> "&cNo servers selected"
            else -> "&a" + equipped.size + " &7server(s) selected"
        }
    }

    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        var index = 0
        val items = UniqueServerService.getValues()
        val buttons = mutableMapOf<Int, Button>()

        for (item in items)
        {
            buttons[index++] = ScopeButton(item, target, rank, duration, reason, global, grant, equipped)
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Select Scopes To Add"
    }

    class ScopeButton(
        val uniqueServer: UniqueServer,
        val target: GameProfile,
        val rank: Rank,
        val duration: Long,
        val reason: String,
        val global: Boolean,
        val grant: RankGrant,
        val equipped: MutableList<String>
    ) : Button()
    {
        private fun isSelected(): Boolean = equipped.contains(uniqueServer.id)

        override fun getMaterial(player: Player): Material
        {
            return Material.WOOL
        }

        override fun getData(player: Player): Short
        {
            return when
            {
                global -> 8
                isSelected() -> 5
                else -> 14
            }
        }

        override fun getDisplayName(player: Player): String
        {
            val prefix = when
            {
                global -> "&7&o"
                isSelected() -> "&a&l"
                else -> "&c"
            }

            return Chat.format(prefix + uniqueServer.displayName)
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            val desc = mutableListOf<String>()
            desc.add(Chat.format(" "))

            when
            {
                global ->
                {
                    desc.add(Chat.format("&7Global scope is enabled, so this"))
                    desc.add(Chat.format("&7grant already applies here."))
                    desc.add(Chat.format(" "))
                    desc.add(Chat.format("&8Disable global to pick servers."))
                }

                isSelected() ->
                {
                    desc.add(Chat.format("&aIncluded &7in this grant's scope."))
                    desc.add(Chat.format(" "))
                    desc.add(Chat.format("&e&lClick &eto remove this server"))
                }

                else ->
                {
                    desc.add(Chat.format("&cNot included &7in this grant's scope."))
                    desc.add(Chat.format(" "))
                    desc.add(Chat.format("&e&lClick &eto add this server"))
                }
            }

            desc.add(Chat.format(" "))
            return desc
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
            if (!global)
            {
                if (!equipped.contains(uniqueServer.id))
                {
                    equipped.add(uniqueServer.id)
                } else
                {
                    equipped.remove(uniqueServer.id)
                }

                ScopeSelectionEditorMenu(player, rank, target, duration, reason, equipped, grant, global).updateMenu()
            } else
            {
                player.sendMessage(Chat.format("&eYou have the &6global &escope selected and cannot add any more"))
            }
        }

    }
}
