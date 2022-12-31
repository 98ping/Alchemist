package ltd.matrixstudios.alchemist.commands.grants.menu.grant

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.permissions.packet.PermissionUpdatePacket
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.staff.packets.StaffAuditPacket
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.conversations.*
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class DurationMenu(val player: Player, val rank: Rank, val target: GameProfile) : Menu(player) {

    init {
        staticSize = 27
        placeholder = true
    }


    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        buttons[10] = DurationButton("1h", 0, "&f", rank, target)
        buttons[11] = DurationButton("12h", 13, "&2", rank, target)
        buttons[12] = DurationButton("1d", 5, "&a", rank, target)
        buttons[13] = DurationButton("1w", 4, "&e", rank, target)
        buttons[14] = DurationButton("1m", 1, "&6", rank, target)
        buttons[15] = DurationButton("1y", 14, "&c", rank, target)
        buttons[16] = DurationButton("Permanent", 14, "&4", rank, target)
        buttons[17] = DurationButton("Custom", 8, "&7", rank, target)

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Select a Duration"
    }


    class DurationButton(val time: String, val data: Short, val timeColor: String, val rank: Rank, val target: GameProfile) : Button()
    {
        override fun getMaterial(player: Player): Material {
            return Material.WOOL
        }

        override fun getDescription(player: Player): MutableList<String>? {
            return mutableListOf()
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format("$timeColor$time")
        }

        override fun getData(player: Player): Short {
            return data
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            if (time.equals("Custom", ignoreCase = true))
            {
                player.closeInventory()
                val factory =
                    ConversationFactory(AlchemistSpigotPlugin.instance).withModality(true).withPrefix(
                        NullConversationPrefix()
                    )
                        .withFirstPrompt(object : StringPrompt() {
                            override fun getPromptText(context: ConversationContext): String {
                                return Chat.format("&ePlease type a duration for this grant, (\"perm\" for permanent), or type &ccancel &eto cancel.")
                            }

                            override fun acceptInput(context: ConversationContext, input: String): Prompt? {
                                return if (input.equals("cancel", ignoreCase = true)) {
                                    context.forWhom.sendRawMessage(Chat.format("&cGrant process aborted."))
                                    END_OF_CONVERSATION
                                } else {
                                    var duration = 0L

                                    duration = if (input == "perm" || input.equals("Permanent", ignoreCase = true)) {
                                        Long.MAX_VALUE
                                    } else {
                                        TimeUtil.parseTime(input).toLong() * 1000L
                                    }

                                    if (duration <= 0) {
                                        player.sendMessage(Chat.format("&cInvalid time, grant process aborted."))
                                        return END_OF_CONVERSATION
                                    }

                                    ReasonMenu(player, rank, target, duration).updateMenu()

                                    END_OF_CONVERSATION
                                }
                            }
                        }).withEscapeSequence("/no").withLocalEcho(false).withTimeout(10)
                        .thatExcludesNonPlayersWithMessage("Go away evil console!")
                val con: Conversation = factory.buildConversation(player)
                player.beginConversation(con)
            } else if (time == "Permanent") {
                ReasonMenu(player, rank, target, Long.MAX_VALUE).updateMenu()
            } else {
                ReasonMenu(player, rank, target, TimeUtil.parseTime(time) * 1000L).updateMenu()
            }
        }

    }
}