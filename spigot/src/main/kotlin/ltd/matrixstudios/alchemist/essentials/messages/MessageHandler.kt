package ltd.matrixstudios.alchemist.essentials.messages

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.profiles.getRankDisplay
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.UUID

object MessageHandler {
    val replyMap: MutableMap<UUID, UUID> = mutableMapOf()

    private val MESSAGE_FORMAT_FROM: String = AlchemistSpigotPlugin.instance.config.getString("message.message_format_from")
    private val MESSAGE_FORMAT_TO: String = AlchemistSpigotPlugin.instance.config.getString("message.message_format_to")

    private val STAFF_MESSAGE_FORMAT_FROM: String = AlchemistSpigotPlugin.instance.config.getString("message.staff_message_format_from")
    private val STAFF_MESSAGE_FORMAT_TO: String = AlchemistSpigotPlugin.instance.config.getString("message.staff_message_format_to")
    private val SOUND: String = AlchemistSpigotPlugin.instance.config.getString("message.sound")

    fun message(to: Player, from: CommandSender, message: String) {
        //allow people to not be able to ignore console
        if (from is Player) {
            if (!hasMessagesOn(to.uniqueId)) {
                from.sendMessage(Chat.format("&cThis player does not have their direct messages open!"))
                return
            }
        }

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

        //can't reply to console either
        if (from is Player) {
            replyMap[to.uniqueId] = from.uniqueId
            replyMap[from.uniqueId] = to.uniqueId
        }

        //some sounds work differently on newer versions
        val mcSound = Sound.values().firstOrNull { it.name.equals(SOUND.toUpperCase(), ignoreCase = true)}

        if (mcSound != null)
        {
            if (hasSoundsOn(to.uniqueId))
            {
                to.playSound(to.location, mcSound, 1.0f, 1.0f)
            }

            //cant play sounds to console
            if (from is Player) {
                if (hasSoundsOn(from.uniqueId)) {
                    from.playSound(from.location, mcSound, 1.0f, 1.0f)
                }
            }
        }
    }

    fun staffMessage(to: Player, from: Player, message: String) {
        to.sendMessage(Chat.format(
            STAFF_MESSAGE_FORMAT_FROM
                .replace("<from>", from.getRankDisplay())
                .replace("<message>", message)
        ))

        from.sendMessage(Chat.format(
            STAFF_MESSAGE_FORMAT_TO
                .replace("<from>", from.getRankDisplay())
                .replace("<message>", message)
        ))

        replyMap[to.uniqueId] = from.uniqueId
        replyMap[from.uniqueId] = to.uniqueId

        //some sounds work differently on newer versions
        val mcSound = Sound.values().firstOrNull { it.name.equals(SOUND.toUpperCase(), ignoreCase = true)}

        if (mcSound != null)
        {
            if (hasSoundsOn(to.uniqueId))
            {
                to.playSound(to.location, mcSound, 1.0f, 1.0f)
            }

            if (hasSoundsOn(from.uniqueId))
            {
                from.playSound(from.location, mcSound, 1.0f, 1.0f)
            }
        }
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

    fun hasSoundsOn(player: UUID) : Boolean
    {
        RedisPacketManager.pool.resource.use {
            if (it.hexists("Alchemist:messageSettings:soundsDisabled:", player.toString()))
            {
                return false
            }

            return true
        }
    }

    fun toggleSounds(value: Boolean, player: UUID)
    {
        RedisPacketManager.pool.resource.use {
            if (!value) {
                it.hset("Alchemist:messageSettings:soundsDisabled:", player.toString(), true.toString())
            } else {
                it.hdel("Alchemist:messageSettings:soundsDisabled:", player.toString())
            }
        }
    }

    fun hasMessagesOn(player: UUID) : Boolean
    {
        RedisPacketManager.pool.resource.use {
            if (it.hexists("Alchemist:messageSettings:messagesDisabled:", player.toString()))
            {
                return false
            }

            return true
        }
    }

    fun toggleMessages(value: Boolean, player: UUID)
    {
        RedisPacketManager.pool.resource.use {
            if (!value) {
                it.hset("Alchemist:messageSettings:messagesDisabled:", player.toString(), true.toString())
            } else {
                it.hdel("Alchemist:messageSettings:messagesDisabled:", player.toString())
            }
        }
    }
}