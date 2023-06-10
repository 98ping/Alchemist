package ltd.matrixstudios.alchemist.webhook.types.punishments

import club.minnced.discord.webhook.send.WebhookEmbed
import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.webhook.WebhookInformation
import ltd.matrixstudios.alchemist.webhook.WebhookService
import org.bukkit.Color

class PunishmentNotification(val punishment: Punishment) : WebhookInformation() {

    override fun getEmbed(): WebhookEmbed {
        val embed = WebhookEmbedBuilder()
            .setColor(punishment.getGrantable().hex)
            .setThumbnailUrl("https://minotar.net/avatar/${punishment.target}/75.png")
            .setTitle(WebhookEmbed.EmbedTitle("**Punishment Executed**", null))
            .setDescription(
                ((AlchemistAPI.syncFindProfile(punishment.target)?.username ?: "Unknown") + " has been " + punishment.getGrantable().added + " by " + (AlchemistAPI.syncFindProfile(punishment.executor)?.username ?: "Console"))
                + "\n\n" + "**Reason**: " + punishment.reason
                + "\n**Short Identifier**: " + punishment.easyFindId
                + "\n**Type**: " + punishment.getGrantable().niceName
                + "\n**Actor**: " + punishment.actor.actorType.name
                + "\n**Duration**: " + if (punishment.expirable.duration != Long.MAX_VALUE) TimeUtil.formatDuration(punishment.expirable.duration) else "Permanent"
            ).build()


        return embed
    }

    override fun send() {
        if (AlchemistSpigotPlugin.instance.config.getBoolean("discord.enabled")) {
            WebhookService.sendInformation(this, WebhookService.punishmentClient)
        }
    }
}