package ltd.matrixstudios.alchemist.commands.grants.menu.grants

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.commands.grants.menu.grant.scope.ScopeSelectionEditorMenu
import ltd.matrixstudios.alchemist.commands.grants.menu.grant.scope.ScopeSelectionMenu
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.permissions.packet.PermissionUpdatePacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.themes.ThemeLoader
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
        return ThemeLoader.defaultTheme.getGrantsLore(player, rankGrant)
    }

    override fun getDisplayName(player: Player): String? {
        return ThemeLoader.defaultTheme.getGrantsDisplayName(player, rankGrant)
    }

    override fun getData(player: Player): Short {
        return ThemeLoader.defaultTheme.getGrantsData(player, rankGrant)
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
        if (type == ClickType.RIGHT) {
            if (player.hasPermission("alchemist.grants.remove")) {
                if (rankGrant.expirable.isActive()) {
                    player.closeInventory()
                    val factory =
                        ConversationFactory(AlchemistSpigotPlugin.instance).withModality(true)
                            .withPrefix(NullConversationPrefix())
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
                } else {
                    player.sendMessage(Chat.format("&cYou cannot remove a grant that is already removed!"))
                }
            } else {
                player.sendMessage(Chat.format("&cYou lack the permissions to do this!"))
            }
        }

        if (type == ClickType.LEFT) {
            ScopeSelectionEditorMenu(player, rankGrant.getGrantable(), AlchemistAPI.syncFindProfile(rankGrant.target)!!, rankGrant.expirable.duration, rankGrant.reason, rankGrant.verifyGrantScope().servers, rankGrant, rankGrant.verifyGrantScope().global).updateMenu()
        }
    }
}