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
import ltd.matrixstudios.alchemist.caches.redis.UpdateGrantCacheRequest
import ltd.matrixstudios.alchemist.packets.NetworkMessagePacket
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.packets.StaffAuditPacket
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.conversations.*
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class ReasonMenu(val player: Player, val rank: Rank, val target: GameProfile, val duration: Long) : Menu(player) {

    init {
        staticSize = 27
        placeholder = true
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        buttons[11] = ReasonButton("Promotion", 10, "&5", rank, target, player, duration)
        buttons[12] = ReasonButton("Won Event", 2, "&d", rank, target, player, duration)
        buttons[13] = ReasonButton("Purchased", 11, "&9", rank, target, player, duration)
        buttons[14] = ReasonButton("Staff Grant", 9, "&3", rank, target, player, duration)
        buttons[15] = ReasonButton("Custom", 8, "&7", rank, target, player, duration)

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Select a Reason"
    }


    class ReasonButton(val reason: String, val data: Short, val color: String, val rank: Rank, val target: GameProfile, val player: Player, val duration: Long) : Button() {
        override fun getMaterial(player: Player): Material {
            return Material.WOOL
        }

        override fun getDescription(player: Player): MutableList<String>? {
            return mutableListOf()
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format("$color$reason")
        }

        override fun getData(player: Player): Short {
            return data
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            if (reason.equals("Custom", ignoreCase = true)) {
                player.closeInventory()
                val factory =
                    ConversationFactory(AlchemistSpigotPlugin.instance).withModality(true).withPrefix(NullConversationPrefix())
                        .withFirstPrompt(object : StringPrompt() {
                            override fun getPromptText(context: ConversationContext): String {
                                return Chat.format("&ePlease type a reason for this grant, or type &ccancel &eto cancel.")
                            }

                            override fun acceptInput(context: ConversationContext, input: String): Prompt? {
                                if (input.equals("cancel", ignoreCase = true)) {
                                    context.forWhom.sendRawMessage(Chat.format("&cGrant process aborted."))
                                    return Prompt.END_OF_CONVERSATION
                                } else {
                                    val internalreason = input

                                    Bukkit.getScheduler().runTaskLater(AlchemistSpigotPlugin.instance, {
                                        val rankGrant = RankGrant(rank.id, target.uuid, player.uniqueId, internalreason, duration, DefaultActor(Executor.PLAYER, ActorType.GAME))

                                        RankGrantService.save(rankGrant)
                                        player.sendMessage(
                                            Chat.format(
                                                "&aGranted &f" + target.username + " &athe " + rank.color + rank.displayName + " &arank"
                                            )
                                        )

                                        AsynchronousRedisSender.send(PermissionUpdatePacket(target.uuid))

                                        AsynchronousRedisSender.send(StaffAuditPacket("&b[Audit] &b" + target.username + " &3was granted " + rank.color + rank.displayName + " &3for &b" + internalreason))
                                        AsynchronousRedisSender.send(NetworkMessagePacket(target.uuid, Chat.format(AlchemistSpigotPlugin.instance.config.getString("grant-message").replace("<rank>", rank.displayName))))
                                    }, 1L)
                                    return Prompt.END_OF_CONVERSATION
                                }
                            }
                        }).withEscapeSequence("/no").withLocalEcho(false).withTimeout(10)
                        .thatExcludesNonPlayersWithMessage("Go away evil console!")
                val con: Conversation = factory.buildConversation(player)
                player.beginConversation(con)
            } else {
                Bukkit.getScheduler().runTaskLater(AlchemistSpigotPlugin.instance, {
                    val rankGrant = RankGrant(rank.id, target.uuid, player.uniqueId, reason, duration, DefaultActor(Executor.PLAYER, ActorType.GAME))

                    RankGrantService.save(rankGrant)
                    player.sendMessage(
                        Chat.format(
                            "&aGranted &f" + target.username + " &athe " + rank.color + rank.displayName + " &arank"
                        )
                    )

                    AsynchronousRedisSender.send(PermissionUpdatePacket(target.uuid))
                    AsynchronousRedisSender.send(UpdateGrantCacheRequest(target.uuid))

                    AsynchronousRedisSender.send(StaffAuditPacket("&b[Audit] &b" + target.username + " &3was granted " + rank.color + rank.displayName + " &3for &b" + reason))
                }, 1L)
                player.closeInventory()
            }
        }

    }
}