package ltd.matrixstudios.alchemist.webhook.types.punishments

import club.minnced.discord.webhook.send.WebhookEmbed
import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.webhook.WebhookInformation
import ltd.matrixstudios.alchemist.webhook.WebhookService
import org.bukkit.Color

class PunishmentNotification(val punishment: Punishment) : WebhookInformation() {

    override fun getEmbed(): WebhookEmbed {
        val embed = WebhookEmbedBuilder()
            .setColor(Color.RED.asRGB())
            .setTitle(WebhookEmbed.EmbedTitle("Alchemist Notification", WebhookService.PUNISHMENT_ICON))
            .addField(
                WebhookEmbed.EmbedField(
                    true,
                    "Target: ",
                    AlchemistAPI.syncFindProfile(punishment.target)?.username ?: "Unknown"
                )
            )
            .addField(
                WebhookEmbed.EmbedField(
                    true,
                    "Executor: ",
                    AlchemistAPI.syncFindProfile(punishment.executor)?.username ?: "Console"
                )
            )
            .addField(WebhookEmbed.EmbedField(true, "Reason: ", punishment.reason))
            .addField(WebhookEmbed.EmbedField(true, "Type: ", punishment.punishmentType))
            .addField(WebhookEmbed.EmbedField(true, "Short Identifier: ", punishment.easyFindId))
            .build()


        return embed
    }

    override fun send() {
        if (AlchemistSpigotPlugin.instance.config.getBoolean("discord.enabled")) {
            WebhookService.sendNotificationEmbed(this)
        }
    }
}