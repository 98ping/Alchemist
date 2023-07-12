package ltd.matrixstudios.alchemist.commands.rank.menu.sub.permission

import ltd.matrixstudios.alchemist.caches.redis.RefreshRankPacket
import ltd.matrixstudios.alchemist.commands.rank.menu.RankListMenu
import ltd.matrixstudios.alchemist.commands.rank.menu.filter.RankListFilter
import ltd.matrixstudios.alchemist.commands.rank.menu.sub.RankEditPropertiesMenu
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.buttons.SkullButton
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

/**
 * Class created on 7/11/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class PermissionEditorMenu(val player: Player, val rank: Rank) : PaginatedMenu(36, player){
    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        var i = 0
        val map = mutableMapOf<Int, Button>()

        for (permission in rank.permissions) {
            map[i++] = PermissionButton(rank, permission)
        }

        return map
    }

    override fun getTitle(player: Player): String {
        return Chat.format("&7[Editor] &ePermissions")
    }

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        return mutableMapOf(
            1 to Button.placeholder(),
            2 to Button.placeholder(),
            4 to Button.placeholder(),
            6 to Button.placeholder(),
            7 to Button.placeholder(),
            9 to Button.placeholder(),
            17 to Button.placeholder(),
            18 to Button.placeholder(),
            26 to Button.placeholder(),
            27 to Button.placeholder(),
            35 to Button.placeholder(),
            36 to Button.placeholder(),
            37 to Button.placeholder(),
            38 to Button.placeholder(),
            39 to Button.placeholder(),
            40 to Button.placeholder(),
            41 to Button.placeholder(),
            42 to Button.placeholder(),
            43 to Button.placeholder(),
            44 to Button.placeholder(),
            3 to SimpleActionButton(Material.PAPER, mutableListOf(), "&eNavigate Back", 0).setBody {
                    player, i, clickType ->  RankEditPropertiesMenu(player, rank).openMenu()
            },
            5 to SimpleActionButton(Material.BOOK, mutableListOf(), "&eAdd Permission", 0).setBody {
                    player, i, clickType ->
                InputPrompt()
                    .withText(Chat.format("&aType a permission to add"))
                    .acceptInput {
                        rank.permissions.add(it)
                        RankService.save(rank)
                        AsynchronousRedisSender.send(RefreshRankPacket())
                        player.sendMessage(Chat.format("&aAdded a new permission to " + rank.color + rank.displayName))
                        PermissionEditorMenu(player, rank).updateMenu()
                    }.start(player)
            },
        )
    }

    override fun getButtonPositions(): List<Int> {
        return listOf(
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34
        )
    }

    override fun getButtonsPerPage(): Int {
        return 21
    }

    class PermissionButton(val rank: Rank, val perm: String) : Button() {
        override fun getMaterial(player: Player): Material {
            return Material.EMERALD
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = mutableListOf<String>()
            desc.add(Chat.format("&c&lRight-Click &cto delete this permission"))

            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format("&e$perm")
        }

        override fun getData(player: Player): Short {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            if (type == ClickType.RIGHT) {
                rank.permissions.remove(perm)
                RankService.save(rank)
                AsynchronousRedisSender.send(RefreshRankPacket())
                player.sendMessage(Chat.format("&cRemoved a permission from " + rank.color + rank.displayName))
                PermissionEditorMenu(player, rank).updateMenu()
            }
        }
    }
}