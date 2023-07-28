package ltd.matrixstudios.alchemist.webhook

import club.minnced.discord.webhook.WebhookClient
import club.minnced.discord.webhook.WebhookClientBuilder
import club.minnced.discord.webhook.send.WebhookEmbed
import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import club.minnced.discord.webhook.send.WebhookMessageBuilder


object WebhookService {

    lateinit var punishmentClient: WebhookClient
    lateinit var rankGrantClient: WebhookClient

    val PUNISHMENT_ICON: String = "https://static.wikia.nocookie.net/minecraft/images/8/8d/BarrierNew.png"

    fun createPunishmentClient(uri: String) {
        val builder = WebhookClientBuilder(uri)

        builder.setThreadFactory { job ->
            val thread = Thread(job)
            thread.name = "Alchemist - Punishment Payload"
            thread.isDaemon = true
            thread
        }

        builder.setWait(true)
        this.punishmentClient = builder.build()
    }

    fun createRankGrantClient(uri: String) {
        val builder = WebhookClientBuilder(uri)

        builder.setThreadFactory { job ->
            val thread = Thread(job)
            thread.name = "Alchemist - RankGrant Payload"
            thread.isDaemon = true
            thread
        }

        builder.setWait(true)
        this.rankGrantClient = builder.build()
    }


    fun sendInformation(info: WebhookInformation, client: WebhookClient) {
        val builder = WebhookMessageBuilder()
        builder.setUsername("Alchemist Notifications") // use this username

        builder.addEmbeds(info.getEmbed())
        client.send(builder.build())
    }
}