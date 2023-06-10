package ltd.matrixstudios.alchemist.webhook

import club.minnced.discord.webhook.WebhookClient
import club.minnced.discord.webhook.WebhookClientBuilder
import club.minnced.discord.webhook.send.WebhookEmbed
import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import club.minnced.discord.webhook.send.WebhookMessageBuilder


object WebhookService {

    lateinit var notificationClient: WebhookClient

    val PUNISHMENT_ICON: String = "https://static.wikia.nocookie.net/minecraft/images/8/8d/BarrierNew.png"

    fun createNotificationClient(uri: String) {
        val builder = WebhookClientBuilder(uri)

        builder.setThreadFactory { job ->
            val thread = Thread(job)
            thread.name = "Alchemist - Notification Webhook"
            thread.isDaemon = true
            thread
        }
        builder.setWait(true)
        this.notificationClient = builder.build()
    }

    fun sendNotificationEmbed(info: WebhookInformation) {
        val builder = WebhookMessageBuilder()
        builder.setUsername("Alchemist Notifications") // use this username

        builder.addEmbeds(info.getEmbed())
        notificationClient.send(builder.build())
    }
}