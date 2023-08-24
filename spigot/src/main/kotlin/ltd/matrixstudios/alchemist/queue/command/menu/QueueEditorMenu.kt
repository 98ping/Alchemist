package ltd.matrixstudios.alchemist.queue.command.menu

/**
 * Class created on 7/12/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
import ltd.matrixstudios.alchemist.models.queue.QueueModel
import ltd.matrixstudios.alchemist.models.queue.QueueStatus
import ltd.matrixstudios.alchemist.queue.packet.QueueUpdatePacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.queue.QueueService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player

class QueueEditorMenu(var player: Player): PaginatedMenu(36, player) {

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        var index = 0
        for (queue in QueueService.cache.values) {
            buttons[index++] = QueueButton(queue)
        }


        return buttons
    }

    override fun getButtonPositions(): List<Int> {
        return listOf(
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34)
    }

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        return mutableMapOf(
            1 to Button.placeholder(),
            2 to Button.placeholder(),
            5 to Button.placeholder(),
            3 to Button.placeholder(),
            4 to SimpleActionButton(Material.BOOKSHELF, mutableListOf(
                " ",
                Chat.format("&7Click here to create a new"),
                Chat.format("&7queue and add it to the local"),
                Chat.format("&7cache"),
                " "
            ), "&eCreate New Queue", 0).setBody { player, i, clickType ->
                InputPrompt()
                    .withText(Chat.format("&eType in the name of the queue you want to create"))
                    .acceptInput { string ->
                        QueueService.byId(string.toLowerCase()).thenAccept {
                            if (it != null)
                            {
                                player.sendMessage(Chat.format("&cThis queue already exists!"))
                                return@thenAccept
                            }

                            val queue = QueueModel(string.toLowerCase(), string, 1, QueueStatus.CLOSED, 1000, string, -1L, "DIAMOND")
                            QueueService.saveQueue(queue)
                            AsynchronousRedisSender.send(QueueUpdatePacket())
                            player.sendMessage(Chat.format("&aYou have created a new queue with the name &f$string"))
                        }
                    }.start(player)
            },
            6 to Button.placeholder(),
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
    
    override fun getButtonsPerPage(): Int {
        return 21
    }

    override fun getTitle(player: Player): String {
        return Chat.format("&7[Editor] &eQueues")
    }
}