package ltd.matrixstudios.alchemist.essentials.messages

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.profiles.getRankDisplay
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player
import java.util.UUID
import java.util.concurrent.CompletableFuture

object MessageHandler {
    val replyMap: MutableMap<UUID, UUID> = mutableMapOf()

    private val MESSAGE_FORMAT_FROM: String = AlchemistSpigotPlugin.instance.config.getString("message.message_format_from")
    private val MESSAGE_FORMAT_TO: String = AlchemistSpigotPlugin.instance.config.getString("message.message_format_to")

    fun message(to: Player, from: Player, message: String) {
        to.sendMessage(Chat.format(
            MESSAGE_FORMAT_FROM
                .replace("<from>", from.getRankDisplay())
                .replace("<message>", message)
        ))

        from.sendMessage(Chat.format(
            MESSAGE_FORMAT_TO
                .replace("<from>", from.getRankDisplay())
                .replace("<message>", message)
        ))

        replyMap[to.uniqueId] = from.uniqueId
        replyMap[from.uniqueId] = to.uniqueId
    }

    fun getPlayersIgnored(player: Player) : MutableList<UUID>
    {
        RedisPacketManager.pool.resource.use { jedis ->
            val list = jedis.hgetAll("Alchemist:messageSettings:ignoreList:${player.uniqueId}")

            return list.filter { it.value.toBoolean() }.map { UUID.fromString(it.key) }.toMutableList()
        }
    }

    fun hasPlayerIgnored(player: Player, ignored: UUID): Boolean {
        RedisPacketManager.pool.resource.use {
            return it.hget(
                "Alchemist:messageSettings:ignoreList:${player.uniqueId}",
                ignored.toString()
            ).toBoolean()
        }
    }

    fun addIgnoredPlayer(
        player: Player,
        ignored: UUID
    ) {
        RedisPacketManager.pool.resource.use {
            it.hset("Alchemist:messageSettings:ignoreList:${player.uniqueId}", ignored.toString(), "true")
        }
    }

    fun removeIgnoredPlayer(
        player: Player,
        ignored: UUID
    ) {
        RedisPacketManager.pool.resource.use {
            it.hset("Alchemist:messageSettings:ignoreList:${player.uniqueId}", ignored.toString(), "false")
        }
    }
}