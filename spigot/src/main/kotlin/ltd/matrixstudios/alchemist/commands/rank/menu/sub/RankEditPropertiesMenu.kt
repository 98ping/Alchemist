package ltd.matrixstudios.alchemist.commands.rank.menu.sub

import ltd.matrixstudios.alchemist.commands.rank.menu.sub.permission.PermissionEditorMenu
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.cache.refresh.RefreshRankPacket
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player

class RankEditPropertiesMenu(val player: Player, val rank: Rank) : Menu(player)
{

    init
    {
        placeholder = true
        staticSize = 27
    }

    override fun getButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        buttons[10] = SimpleActionButton(
            Material.LADDER,
            mutableListOf(
                " ",
                Chat.format("&7Change the priority of the rank."),
                Chat.format("&7This change will affect display order and"),
                Chat.format("&7server punishment/grant handling"),
                " ",
                Chat.format("&6&l｜ &fCurrently: &e" + rank.weight),
                " ",
                Chat.format("&aClick to change rank weight!")
            ),
            "&eChange Priority", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new priority for this rank!"))
                .acceptInput {
                    var newPriority = 0

                    try
                    {
                        newPriority = Integer.parseInt(it)
                    } catch (e: java.lang.NumberFormatException)
                    {
                        player.sendMessage(Chat.format("&cThis is not a number!"))
                        return@acceptInput
                    }

                    rank.weight = newPriority
                    RankService.save(rank)
                    AsynchronousRedisSender.send(RefreshRankPacket())
                    player.sendMessage(Chat.format("&aUpdated the priority of " + rank.color + rank.displayName))
                    RankEditPropertiesMenu(player, rank).openMenu()
                }.start(player)
        }

        buttons[11] = SimpleActionButton(
            Material.NETHER_STAR,
            mutableListOf(
                " ",
                Chat.format("&7Change staff status of this rank."),
                Chat.format("&7Ranks with staff status are handled"),
                Chat.format("&7differently than other ranks and given"),
                Chat.format("&7more permission."),
                " ",
                Chat.format("&6&l｜ &fCurrently: &e" + if (rank.staff) "&aTrue" else "&cFalse"),
                " ",
                Chat.format("&aClick to change rank staff status!")
            ),
            "&eChange Staff Status", 0
        ).setBody { player, slot, clicktype ->
            val otherBool = !rank.staff
            rank.staff = otherBool
            RankService.save(rank)
            AsynchronousRedisSender.send(RefreshRankPacket())
            player.sendMessage(Chat.format("&eUpdate the staff status of &6${rank.color}${rank.displayName} &eto " + if (otherBool) "&aTrue" else "&cFalse"))
            RankEditPropertiesMenu(player, rank).openMenu()
        }

        buttons[12] = SimpleActionButton(
            Material.BOOK,
            mutableListOf(
                " ",
                Chat.format("&7Change the prefix of this rank."),
                Chat.format("&7This prefix will show in public chat"),
                Chat.format("&7as well as some display parts of the server"),
                " ",
                Chat.format("&6&l｜ &fCurrently: &r" + rank.prefix),
                " ",
                Chat.format("&aClick to change rank prefix!")
            ),
            "&eChange Prefix", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new prefix for this rank! (Color Codes Supported)"))
                .acceptInput {
                    rank.prefix = it
                    RankService.save(rank)
                    AsynchronousRedisSender.send(RefreshRankPacket())
                    player.sendMessage(Chat.format("&aUpdated the prefix of " + rank.color + rank.displayName))
                    RankEditPropertiesMenu(player, rank).openMenu()
                }.start(player)
        }

        buttons[13] = SimpleActionButton(
            Material.EXP_BOTTLE,
            mutableListOf(
                " ",
                Chat.format("&7Change the display color of this rank."),
                Chat.format("&7This color will show in /list"),
                Chat.format("&7and menu aspects."),
                " ",
                Chat.format("&6&l｜ &fCurrently: &e" + rank.color + "This"),
                " ",
                Chat.format("&aClick to change rank color!")
            ),
            "&eChange Color", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new color for this rank!"))
                .acceptInput {
                    rank.color = it
                    RankService.save(rank)
                    AsynchronousRedisSender.send(RefreshRankPacket())
                    player.sendMessage(Chat.format("&aUpdated the color of " + rank.color + rank.displayName))
                    RankEditPropertiesMenu(player, rank).openMenu()
                }.start(player)
        }

        buttons[14] = SimpleActionButton(
            Material.NAME_TAG,
            mutableListOf(
                " ",
                Chat.format("&7Change the display name of this rank."),
                Chat.format("&7Changing the display name causes"),
                Chat.format("&7parts of the plugin use the display name"),
                Chat.format("&7instead of just the regular rank id"),
                " ",
                Chat.format("&6&l｜ &fCurrently: &e" + rank.displayName),
                " ",
                Chat.format("&aClick to change rank display name!")
            ),
            "&eChange Display Name", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new display name for this rank!"))
                .acceptInput {
                    rank.displayName = it
                    RankService.save(rank)
                    AsynchronousRedisSender.send(RefreshRankPacket())
                    player.sendMessage(Chat.format("&aUpdated the display name of " + rank.color + rank.displayName))
                    RankEditPropertiesMenu(player, rank).openMenu()
                }.start(player)
        }

        buttons[15] = SimpleActionButton(
            Material.DIAMOND,
            mutableListOf(
                " ",
                Chat.format("&7Change the wool color of this rank."),
                Chat.format("&7This is used for custom hex codes"),
                Chat.format("&7and showing their respective colors"),
                Chat.format("&7in rank-based menus."),
                " ",
                Chat.format("&cNormal color codes do not need this addition!"),
                Chat.format("&6&l｜ &fCurrently: &f" + rank.color + "This"),
                " ",
                Chat.format("&aClick to change rank wool color!")
            ),
            "&eChange Wool Color", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new wool color for this rank!"))
                .acceptInput {
                    rank.woolColor = it
                    RankService.save(rank)
                    AsynchronousRedisSender.send(RefreshRankPacket())
                    player.sendMessage(Chat.format("&aUpdated the wool color of " + rank.color + rank.displayName))
                    RankEditPropertiesMenu(player, rank).openMenu()
                }.start(player)
        }

        buttons[16] = SimpleActionButton(
            Material.EMERALD,
            mutableListOf(
                " ",
                Chat.format("&7Change the permissions of this"),
                Chat.format("&7rank. Permissions are used to give"),
                Chat.format("&7this rank command and features for anyone"),
                Chat.format("&7with this rank granted."),
                " ",
                Chat.format("&6&l｜ &fCurrently: &f" + rank.permissions.size + " Node${if (rank.permissions.size == 1) "" else "s"}"),
                " ",
                Chat.format("&aClick to change rank permissions!")
            ),
            "&eChange Permission", 0
        ).setBody { player, slot, clicktype ->
            PermissionEditorMenu(player, rank).updateMenu()
        }

        return buttons
    }


    override fun getTitle(player: Player): String
    {
        return Chat.format("&7[Editor] ${rank.color + rank.displayName}")
    }
}