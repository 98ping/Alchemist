package ltd.matrixstudios.alchemist.commands.grants.menu.grant.scope

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.caches.redis.UpdateGrantCacheRequest
import ltd.matrixstudios.alchemist.commands.grants.menu.grants.GrantsMenu
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.grant.types.scope.GrantScope
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.packets.StaffAuditPacket
import ltd.matrixstudios.alchemist.permissions.packet.PermissionUpdatePacket
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Bukkit
import org.bukkit.DyeColor
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
) : PaginatedMenu(36, player) {

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[3] = SimpleActionButton(
            Material.DIAMOND_SWORD, mutableListOf(
                Chat.format("&6&m----------------------"),
                Chat.format("&eClick to make this grant &6global"),
                Chat.format(" "),
                Chat.format("&eCurrently&7: &f" + if (global) "&aGlobal" else "&cLocal"),
                Chat.format("&6&m----------------------")
            ), Chat.format("&6Global Status"), 0
        ).setBody { player, i, clickType ->
            ScopeSelectionEditorMenu(player, rank, target, duration, reason, equipped, grant, !global).updateMenu()
        }

        buttons[5] = SimpleActionButton(
            Material.PAPER, mutableListOf(
                Chat.format("&6&m----------------------"),
                Chat.format("&eClick to &6finalize &ethis grant"),
                Chat.format("&6&m----------------------")
            ), Chat.format("&6Finalize"), 0
        ).setBody { player, i, clickType ->
            if (!global && equipped.isEmpty()) {
                player.sendMessage(Chat.format("&cYou must select a scope to add this grant to"))
                return@setBody
            }

            grant.scope = GrantScope("Manual Scope Editing", equipped, global)
            RankGrantService.save(grant)
            GrantsMenu(player, target).updateMenu()
            player.sendMessage(Chat.format("&eUpdated the scopes of this &6grant"))
        }

        return buttons
    }

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        var index = 0
        val items = UniqueServerService.getValues()
        val buttons = mutableMapOf<Int, Button>()

        for (item in items) {
            buttons[index++] = ScopeButton(item, target, rank, duration, reason, global, grant, equipped)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
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
    ) : Button() {
        override fun getMaterial(player: Player): Material {
            return Material.WOOL
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = mutableListOf<String>()
            desc.add(Chat.format("&6&m----------------------"))
            desc.add(Chat.format("&eServer&7: &f" + uniqueServer.displayName))
            desc.add(Chat.format(" "))
            desc.add(Chat.format("&6Equipped&7: &f" + if (equipped.contains(uniqueServer.id)) "&aYes" else "&cNo"))
            desc.add(Chat.format("&eClick here to " + if (equipped.contains(uniqueServer.id)) "&cunselect" else "&aselect" + " &ethis server"))
            desc.add(Chat.format("&6&m----------------------"))

            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format("&6" + uniqueServer.displayName)
        }

        override fun getData(player: Player): Short {
            if (equipped.contains(uniqueServer.id)) {
                return DyeColor.LIME.woolData.toShort()
            } else return DyeColor.RED.woolData.toShort()
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            if (!global) {
                if (!equipped.contains(uniqueServer.id)) {
                    equipped.add(uniqueServer.id)
                } else {
                    equipped.remove(uniqueServer.id)
                }

                ScopeSelectionEditorMenu(player, rank, target, duration, reason, equipped, grant, global).updateMenu()
            } else {
                player.sendMessage(Chat.format("&eYou have the &6global &escope selected and cannot add any more"))
            }
        }

    }
}