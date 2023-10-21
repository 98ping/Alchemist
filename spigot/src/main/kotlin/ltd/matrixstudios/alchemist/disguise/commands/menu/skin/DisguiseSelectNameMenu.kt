package ltd.matrixstudios.alchemist.disguise.commands.menu.skin

import ltd.matrixstudios.alchemist.disguise.DisguiseService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import net.pinger.disguise.DisguiseAPI
import net.pinger.disguise.exception.UserNotFoundException
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.concurrent.ThreadLocalRandom

class DisguiseSelectNameMenu(val player: Player) : Menu(player)
{
    init
    {
        staticSize = 9
        placeholder = true
    }

    override fun getButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        buttons[2] = SimpleActionButton(
            Material.NAME_TAG,
            mutableListOf(
                " ",
                Chat.format("&7Click to be randomly"),
                Chat.format("&7given a username from a list"),
                Chat.format("&7of uncommon names."),
                " ",
                Chat.format("&a&lLeft-Click &ato use a random name!"),
                " "
            ),
            Chat.format("&cUse Random Name"),
            0
        ).setBody { player, i, clickType ->
            DisguiseSelectSkinMenu(
                player,
                DisguiseService.commonNames.getOrNull(
                    ThreadLocalRandom.current().nextInt(DisguiseService.commonNames.size)
                ) ?: player.name
            ).updateMenu()
        }

        buttons[4] = SimpleActionButton(
            Material.SIGN,
            mutableListOf(
                " ",
                Chat.format("&7Click to type in a custom"),
                Chat.format("&7username that you will use for your"),
                Chat.format("&7disguise."),
                " ",
                Chat.format("&a&lLeft-Click &ato use a custom name!"),
                " "
            ),
            Chat.format("&9Use Custom Name"),
            0
        ).setBody { player, i, clickType ->
            InputPrompt()
                .withText(Chat.format("&aType in a &ecustom name &ato disguise yourself as!"))
                .acceptInput {
                    if (it.length < 3)
                    {
                        player.sendMessage(Chat.format("&cThis disguise is too short!"))
                        return@acceptInput
                    }

                    if (it.length >= 16)
                    {
                        player.sendMessage(Chat.format("&cThis disguise is too long!"))
                        return@acceptInput
                    }

                    try
                    {
                        DisguiseAPI.getSkinManager().getFromMojang(it)
                    } catch (e: UserNotFoundException)
                    {
                        player.sendMessage(Chat.format("&cThis player does not exist! Please check the spelling of the name."))
                    }

                    DisguiseSelectSkinMenu(
                        player, it
                    ).updateMenu()
                }.start(player)
        }

        buttons[6] = SimpleActionButton(
            Material.PAINTING,
            mutableListOf(
                " ",
                Chat.format("&7Click to be randomly"),
                Chat.format("&7given a popular username."),
                " ",
                Chat.format("&a&lLeft-Click &ato use a random name!"),
                " "
            ),
            Chat.format("&aUse Popular Name"),
            0
        ).setBody { player, i, clickType ->
            DisguiseSelectSkinMenu(
                player,
                DisguiseService.popularNames.getOrNull(
                    ThreadLocalRandom.current().nextInt(DisguiseService.popularNames.size)
                ) ?: player.name
            ).updateMenu()
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Select a Name!"
    }
}