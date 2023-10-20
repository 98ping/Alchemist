package ltd.matrixstudios.alchemist.disguise.commands.menu.skin

import ltd.matrixstudios.alchemist.disguise.DisguiseService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.buttons.SkullButton
import ltd.matrixstudios.alchemist.util.menu.type.BorderedPaginatedMenu
import ltd.matrixstudios.alchemist.util.skull.SkullButtonOnlyName
import net.pinger.disguise.DisguiseAPI
import net.pinger.disguise.exception.UserNotFoundException
import net.pinger.disguise.skin.Skin
import org.bukkit.Material
import org.bukkit.entity.Player

class DisguiseSelectSkinMenu(val player: Player, val name: String) : BorderedPaginatedMenu(player)
{
    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (skin in DisguiseService.commonSkins.entries)
        {
            buttons[i++] = SkullButton(
                skin.value.value,
                mutableListOf(),
                Chat.format("&9${skin.key}'s Skin")
            ).setBody { player, i, clickType ->
                player.closeInventory()
                DisguiseService.setupDisguise(player, name, skin.value)
            }
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Select a Skin..."
    }

    override fun getButtonPositions(): List<Int> {
        return listOf(
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34
        )
    }

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        return mutableMapOf(
            1 to Button.placeholder(),
            2 to SkullButtonOnlyName(
                player.name,
                Chat.format("&aUse Your Current Skin"),
                mutableListOf()
            ).setBody { player, i, clickType ->
                player.closeInventory()
                val skin = DisguiseAPI.getSkinManager().getFromMojang(player.name)

                if (skin == null || skin.value == null)
                {
                    player.sendMessage(Chat.format("&cUnable to contact MojangAPI"))
                    return@setBody
                }

                DisguiseService.setupDisguise(player, name, skin)
            },
            3 to Button.placeholder(),
            6 to SkullButtonOnlyName(
                name,
                Chat.format("&9Use ${name}'s Skin"),
                mutableListOf()
            ).setBody { player, i, clickType ->
                player.closeInventory()
                val skin = DisguiseAPI.getSkinManager().getFromMojang(player.name)

                if (skin == null || skin.value == null)
                {
                    player.sendMessage(Chat.format("&cUnable to contact MojangAPI"))
                    return@setBody
                }

                DisguiseService.setupDisguise(player, name, skin)
            },
            5 to Button.placeholder(),
            4 to SimpleActionButton(
                Material.NAME_TAG,
                mutableListOf(),
                Chat.format("&3Use Custom Skin"),
                0
            ).setBody { player, i, clickType ->
                InputPrompt()
                    .withText(Chat.format("&cType in a custom skin to disguies yourself as!"))
                    .acceptInput {
                        player.closeInventory()
                        val skin: Skin?
                        try
                        {
                            skin = DisguiseAPI.getSkinManager().getFromMojang(it)
                        } catch (e: UserNotFoundException)
                        {
                            player.sendMessage(Chat.format("&cThis player does not exist! Please check the spelling of the name."))
                            return@acceptInput
                        }

                        DisguiseService.setupDisguise(player, name, skin)
                    }.start(player)
            },
            7 to Button.placeholder(),
            17 to Button.placeholder(),
            18 to Button.placeholder(),
            26 to Button.placeholder(),
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
            9 to Button.placeholder(),
            27 to Button.placeholder(),
        )
    }
}