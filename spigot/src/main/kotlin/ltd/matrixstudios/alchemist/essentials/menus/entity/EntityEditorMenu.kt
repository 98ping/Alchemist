package ltd.matrixstudios.alchemist.essentials.menus.entity

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue

class EntityEditorMenu(val player: Player, val entity: Entity) : Menu(player)
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
            Material.NAME_TAG, mutableListOf(
            Chat.format("&7Change the custom name"),
            Chat.format("&7of this entity!")
        ), Chat.format("&cChange Custom Name"), 0).setBody { player, i, clickType ->
            InputPrompt()
                .withText(Chat.format("&eType in a new &ccustom name &eto set this entity to"))
                .acceptInput { s ->
                    entity.customName = Chat.format(s)
                    player.sendMessage(Chat.format("&eUpdated the &b${entity.type.name} &eentity's custom name to &r${s}&e."))
                }.start(player)
        }

        buttons[11] = SimpleActionButton(
            Material.PAPER, mutableListOf(
                Chat.format("&7Change visibility of this"),
                Chat.format("&7entity's custom name!")
            ), Chat.format("&aChange Name Visibility"), 0).setBody { player, i, clickType ->
            val visible = entity.isCustomNameVisible

            entity.isCustomNameVisible = !visible
            player.sendMessage(Chat.format("&eYou have set this entity's &aname visibility &eto ${if (entity.isCustomNameVisible) "&atrue" else "&cfalse"}&e."))
        }

        buttons[16] = SimpleActionButton(
            Material.FEATHER, mutableListOf(
                Chat.format("&7Click to remove this"),
                Chat.format("&7entity from it's current world!")
            ), Chat.format("&cRemove Entity"), 0).setBody { player, i, clickType ->
            if (entity.type == EntityType.PLAYER)
            {
                player.sendMessage(Chat.format("&cYou cannot remove a player!"))
                return@setBody
            }

            entity.remove()
            player.sendMessage(Chat.format("&eYou have forcefully &ckilled &ethis entity!"))
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Edit This Entity"
    }
}