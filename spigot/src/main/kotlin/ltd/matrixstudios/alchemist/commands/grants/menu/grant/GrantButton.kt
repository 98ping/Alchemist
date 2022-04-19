package ltd.matrixstudios.alchemist.commands.grants.menu.grant

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.conversations.*
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType


class GrantButton(var rank: Rank, var gameProfile: GameProfile) : Button() {


    override fun getMaterial(player: Player): Material {
        return Material.WOOL
    }

    override fun getDescription(player: Player): MutableList<String>? {
        val desc = arrayListOf<String>()

        desc.add(Chat.format("&7&m--------------"))
        desc.add(Chat.format("&fRank: &r${rank.color}${rank.displayName}"))
        desc.add(Chat.format("&7* &fColor: ${rank.color}This"))
        desc.add(Chat.format("&7* &fPrefix: ${rank.prefix}"))
        desc.add(Chat.format("&7* &fWeight: ${rank.weight}"))
        desc.add(Chat.format("&7&m--------------"))


        return desc
    }

    override fun getDisplayName(player: Player): String? {
        return Chat.format("${rank.color}${rank.displayName}")
    }

    override fun getData(player: Player): Short {
        return AlchemistAPI.getWoolColor(rank.color).woolData.toShort()
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
        reasonConvo(player)
    }

   fun reasonConvo(player: Player) {
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
                            val reason = input

                           Bukkit.getScheduler().runTaskLater(AlchemistSpigotPlugin.instance, {
                               durationConversation(player, reason)
                           }, 5L)
                            return Prompt.END_OF_CONVERSATION
                        }
                    }
                }).withEscapeSequence("/no").withLocalEcho(false).withTimeout(10)
                .thatExcludesNonPlayersWithMessage("Go away evil console!")
        val con: Conversation = factory.buildConversation(player)
        player.beginConversation(con)
    }

    fun durationConversation(player: Player, reason: String) {
        player.closeInventory()
        val factory =
            ConversationFactory(AlchemistSpigotPlugin.instance).withModality(true).withPrefix(NullConversationPrefix())
                .withFirstPrompt(object : StringPrompt() {
                    override fun getPromptText(context: ConversationContext): String {
                        return Chat.format("&ePlease type a duration for this grant, (\"perm\" for permanent), or type &ccancel &eto cancel.")
                    }

                    override fun acceptInput(context: ConversationContext, input: String): Prompt? {
                        return if (input.equals("cancel", ignoreCase = true)) {
                            context.forWhom.sendRawMessage(Chat.format("&cGrant process aborted."))
                            END_OF_CONVERSATION
                        } else {
                            val duration = TimeUtil.parseTime(input)
                            if (duration <= 0L) {
                                player.sendMessage(Chat.format("&cInvalid time, grant process aborted."))
                                return END_OF_CONVERSATION
                            }
                            Bukkit.getScheduler().runTaskLater(AlchemistSpigotPlugin.instance, {
                                val rankGrant = RankGrant(rank.id, gameProfile.uuid, player.uniqueId, reason, duration * 1000L, DefaultActor(Executor.PLAYER, ActorType.GAME))

                                RankGrantService.save(rankGrant)
                                player.sendMessage(
                                    Chat.format(
                                        "&aGranted &f" + gameProfile.username + " &athe " + rank.color + rank.displayName + " &arank"
                                    )
                                )
                            }, 1L)
                            END_OF_CONVERSATION
                        }
                    }
                }).withEscapeSequence("/no").withLocalEcho(false).withTimeout(10)
                .thatExcludesNonPlayersWithMessage("Go away evil console!")
        val con: Conversation = factory.buildConversation(player)
        player.beginConversation(con)
    }

}