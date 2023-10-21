package ltd.matrixstudios.alchemist.queue.command.menu.sub

import ltd.matrixstudios.alchemist.models.queue.QueueModel
import ltd.matrixstudios.alchemist.queue.packet.QueueUpdatePacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.queue.QueueService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player

class QueueEditAttributesMenu(val player: Player, val queue: QueueModel) : Menu(player)
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
            Material.NAME_TAG,
            mutableListOf(
                " ",
                Chat.format("&7Change the display name of this queue."),
                Chat.format("&7Changing the display name causes"),
                Chat.format("&7API calls and server messages"),
                Chat.format("&7to use this custom name."),
                " ",
                Chat.format("&eCurrently: &f" + queue.displayName),
                " "
            ),
            "&eChange Display Name", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new display name for this queue"))
                .acceptInput {
                    queue.displayName = it
                    QueueService.saveQueue(queue)
                    AsynchronousRedisSender.send(QueueUpdatePacket())
                    player.sendMessage(Chat.format("&aUpdated the display name of " + queue.displayName))
                    QueueEditAttributesMenu(player, queue).openMenu()
                }.start(player)
        }

        buttons[11] = SimpleActionButton(
            Material.EMERALD,
            mutableListOf(
                " ",
                Chat.format("&7Change the material of this queue."),
                Chat.format("&7This material will be used in menus"),
                Chat.format("&7relating to queues."),
                " ",
                Chat.format("&eCurrently: &f" + queue.material),
                " "
            ),
            "&eChange Material", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new material for this queue!"))
                .acceptInput {
                    queue.material = it.uppercase()
                    QueueService.saveQueue(queue)
                    AsynchronousRedisSender.send(QueueUpdatePacket())
                    player.sendMessage(Chat.format("&aUpdated the material of " + queue.displayName))
                    QueueEditAttributesMenu(player, queue).openMenu()
                }.start(player)
        }
        buttons[12] = SimpleActionButton(
            Material.ANVIL,
            mutableListOf(
                " ",
                Chat.format("&7Change the entry limit of the queue."),
                Chat.format("&7If this limit gets reached, queue entry will be denied."),
                " ",
                Chat.format("&eCurrently: &f" + queue.limit),
                " "
            ),
            "&eChange Entry Limit", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new entry limit for this rank!"))
                .acceptInput {
                    var limit = 1000

                    try
                    {
                        limit = Integer.parseInt(it)
                    } catch (e: NumberFormatException)
                    {
                        player.sendMessage(Chat.format("&cThis is not a valid integer!"))
                    }

                    queue.limit = limit
                    QueueService.saveQueue(queue)
                    AsynchronousRedisSender.send(QueueUpdatePacket())
                    player.sendMessage(Chat.format("&aUpdated the material of " + queue.displayName))
                    QueueEditAttributesMenu(player, queue).openMenu()
                }.start(player)
        }

        return buttons
    }


    override fun getTitle(player: Player): String
    {
        return Chat.format("&7[Editor] ${queue.displayName}")
    }
}