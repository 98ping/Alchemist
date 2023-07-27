package ltd.matrixstudios.alchemist.profiles.commands.player

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.WebUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class FreerankCommand : BaseCommand() {

    @CommandAlias("freerank")
    fun freeRank(player: Player) {
        val gameProfile = ProfileGameService.byId(player.uniqueId)

        if (gameProfile == null) {
            player.sendMessage(Chat.format("&cYou do not have a profile!"))
            return
        }

        if (gameProfile.metadata.has("redeemedFreeRank")) {
            player.sendMessage(Chat.format("&cYou already claimed your free rank!"))
            return
        }

        WebUtil.playerHasLiked(player.uniqueId).thenApply {
            if (it == false) {
                for (line in AlchemistSpigotPlugin.instance.config.getStringList("freeRank.message")) {
                    player.sendMessage(Chat.format(line))
                }

                return@thenApply
            } else {
                player.sendMessage(Chat.format("&aRedeemed your free rank!"))

                AlchemistSpigotPlugin.instance.server.scheduler.runTask(AlchemistSpigotPlugin.instance) {
                    Bukkit.dispatchCommand(
                        Bukkit.getConsoleSender(),
                        AlchemistSpigotPlugin.instance.config.getString("freeRank.command")
                            .replace("<target>", player.name)
                    )
                }

                gameProfile.metadata.addProperty("redeemedFreeRank", true)

                ProfileGameService.saveSync(gameProfile)
            }
        }
    }
}