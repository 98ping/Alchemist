package ltd.matrixstudios.alchemist.commands.grants.menu.grants

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.permissions.packet.PermissionUpdatePacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Bukkit
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.conversations.*
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.*

class GrantsButton(var rankGrant: RankGrant) : Button() {


    override fun getMaterial(player: Player): Material {
        return Material.WOOL
    }

    override fun getDescription(player: Player): MutableList<String>? {
        val desc = arrayListOf<String>()

        desc.add(Chat.format("&6&m--------------------"))
        desc.add(Chat.format("&eTarget: &r" + AlchemistAPI.getRankDisplay(rankGrant.executor)))
        desc.add(Chat.format("&eRank: &r" + rankGrant.getGrantable()!!.color + rankGrant.getGrantable()!!.displayName))
        desc.add(Chat.format("&eDuration: &f" + TimeUtil.formatDuration(rankGrant.expirable.duration)))
        if (rankGrant.expirable.duration != Long.MAX_VALUE && rankGrant.expirable.isActive())
        {
            desc.add(Chat.format("&eRemaining: &f" + TimeUtil.formatDuration((rankGrant.expirable.addedAt + rankGrant.expirable.duration) - System.currentTimeMillis())))
        }
        desc.add(Chat.format("&6&m--------------------"))
        desc.add(Chat.format("&eActor:"))
        desc.add(Chat.format("&7- &eType: &c" + rankGrant.internalActor.actorType.name))
        desc.add(Chat.format("&7- &eExecuted From: &c" + rankGrant.internalActor.executor.name))
        desc.add(Chat.format("&6&m--------------------"))
        desc.add(Chat.format("&eIssued By: &f" + AlchemistAPI.getRankDisplay(rankGrant.executor)))
        desc.add(Chat.format("&eIssued Reason: &f" + rankGrant.reason))
        desc.add(Chat.format("&6&m--------------------"))
        if (!rankGrant.expirable.isActive())
        {
            desc.add(Chat.format("&eRemoved By: &f" + AlchemistAPI.getRankDisplay(rankGrant.removedBy!!)))
            desc.add(Chat.format("&eRemoved Reason: &f" + rankGrant.removedReason!!))
            desc.add(Chat.format("&6&m--------------------"))
        }


        return desc
    }

    override fun getDisplayName(player: Player): String? {
        return Chat.format((if (rankGrant.expirable.isActive()) "&a&l(Active) " else "&c&l(Inactive) ") + Date(rankGrant.expirable.addedAt))
    }

    override fun getData(player: Player): Short {
        return (if (rankGrant.expirable.isActive()) DyeColor.GREEN.woolData.toShort() else DyeColor.RED.woolData.toShort())
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
        player.closeInventory()
        val factory =
            ConversationFactory(AlchemistSpigotPlugin.instance).withModality(true).withPrefix(NullConversationPrefix())
                .withFirstPrompt(object : StringPrompt() {
                    override fun getPromptText(context: ConversationContext): String {
                        return Chat.format("&ePlease type a reason for this grant to be removed, or type &ccancel &eto cancel.")
                    }

                    override fun acceptInput(context: ConversationContext, input: String): Prompt? {
                        if (input.equals("cancel", ignoreCase = true)) {
                            context.forWhom.sendRawMessage(Chat.format("&cGrant process aborted."))
                            return Prompt.END_OF_CONVERSATION
                        } else {
                            Bukkit.getScheduler().runTaskLater(AlchemistSpigotPlugin.instance, {
                                rankGrant.expirable.removedAt = System.currentTimeMillis()
                                rankGrant.removedReason = input
                                rankGrant.removedBy = player.uniqueId
                                rankGrant.expirable.expired = true

                                RankGrantService.save(rankGrant)

                                AsynchronousRedisSender.send(PermissionUpdatePacket(rankGrant.target))
                                player.sendMessage(Chat.format("&aRemoved the grant!"))
                            }, 5L)
                            return Prompt.END_OF_CONVERSATION
                        }
                    }
                }).withEscapeSequence("/no").withLocalEcho(false).withTimeout(10)
                .thatExcludesNonPlayersWithMessage("Go away evil console!")
        val con: Conversation = factory.buildConversation(player)
        player.beginConversation(con)
    }
}