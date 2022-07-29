package ltd.matrixstudios.alchemist.commands.party.menu.buttons

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.models.party.PartyElevation
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.impl.NetworkMessagePacket
import ltd.matrixstudios.alchemist.service.party.PartyService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Material
import org.bukkit.conversations.*
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class RemovePartyMemberButton : Button() {

    override fun getMaterial(player: Player): Material {
        return Material.REDSTONE_COMPARATOR
    }

    override fun getDescription(player: Player): MutableList<String>? {
        return mutableListOf()
    }

    override fun getDisplayName(player: Player): String? {
        return Chat.format("&cRemove a Party Member")
    }

    override fun getData(player: Player): Short {
        return 0
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
        player.closeInventory()
        val factory =
            ConversationFactory(AlchemistSpigotPlugin.instance).withModality(true).withPrefix(
                NullConversationPrefix()
            )
                .withFirstPrompt(object : StringPrompt() {
                    override fun getPromptText(context: ConversationContext): String {
                        return Chat.format("&ePlease type a player to remove, or type &ccancel &eto cancel.")
                    }

                    override fun acceptInput(context: ConversationContext, input: String): Prompt? {
                        return if (input.equals("cancel", ignoreCase = true)) {
                            context.forWhom.sendRawMessage(Chat.format("&cPlayer add process aborted."))
                            END_OF_CONVERSATION
                        } else {
                            if (PartyService.getParty(player.uniqueId) == null)
                            {
                                player.sendMessage(Chat.format("&cYou are not in a party!"))
                                return END_OF_CONVERSATION
                            }

                            val party = PartyService.getParty(player.uniqueId)!!

                            if (party.members.map { it.first }.contains(player.uniqueId)
                                && party.members.first { it.first.toString() == player.uniqueId.toString() }.second == PartyElevation.MEMBER) {
                                player.sendMessage(Chat.format("&cYou are not high enough rank to remove people from your party!"))
                                return END_OF_CONVERSATION
                            }

                            val target = input

                            val targetProfile = ProfileGameService.byUsername(input)

                            if (targetProfile == null) {
                                player.sendMessage(Chat.format("&cPlayer '$target' does not exist!"))
                                return END_OF_CONVERSATION
                            }

                            if (PartyService.getParty(targetProfile.uuid) == null) {
                                player.sendMessage(Chat.format("&cPlayer '$target' is not in a party!"))
                                return END_OF_CONVERSATION
                            }

                            if (party.members.firstOrNull { it.first.toString() == targetProfile.uuid.toString() } == null) {
                                player.sendMessage(Chat.format("&cPlayer '$target' is not in your party!"))
                                return END_OF_CONVERSATION
                            }

                            party.members.forEach {
                                AsynchronousRedisSender.send(NetworkMessagePacket(it.first, Chat.format("&8[&dParties&8] &b${targetProfile.username} &fhas been &ckicked &ffrom your party")))
                            }

                            party.removeMember(targetProfile.uuid)
                            PartyService.handler.storeAsync(party.id, party)

                            END_OF_CONVERSATION
                        }
                    }
                }).withEscapeSequence("/no").withLocalEcho(false).withTimeout(10)
                .thatExcludesNonPlayersWithMessage("Go away evil console!")
        val con: Conversation = factory.buildConversation(player)
        player.beginConversation(con)
    }
}