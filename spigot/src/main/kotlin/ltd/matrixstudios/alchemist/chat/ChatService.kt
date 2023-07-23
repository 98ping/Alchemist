package ltd.matrixstudios.alchemist.chat

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import org.bukkit.entity.Player
import java.util.UUID
import java.util.concurrent.TimeUnit

object ChatService {

    val SLOW_MESSAGE = AlchemistSpigotPlugin.instance.config.getString("chat.slowChatMessage")
    val MUTE_MESSAGE = AlchemistSpigotPlugin.instance.config.getString("chat.muteChatMessage")

    val cooldownMap = hashMapOf<UUID, Long>()

    var slowed = false
    var slowDuration = 3

    var muted = false

    fun addCooldown(player: Player) {
        cooldownMap[player.uniqueId] = System.currentTimeMillis()
    }

    fun isOnCooldown(player: Player) : Boolean {
        val contains = cooldownMap.containsKey(player.uniqueId)

        return if (contains && System.currentTimeMillis().minus(cooldownMap[player.uniqueId]!!) < TimeUnit.SECONDS.toMillis((slowDuration + 1).toLong())) {
            true
        } else {
            cooldownMap.remove(player.uniqueId)

            false
        }
    }

    fun getCooldownRemaining(player: Player) : Int {
        if (!cooldownMap.containsKey(player.uniqueId)) return 0

        val cooldown = cooldownMap[player.uniqueId]!!
        val removingAt = cooldown + TimeUnit.SECONDS.toMillis((slowDuration + 1).toLong())

        return (removingAt.minus(System.currentTimeMillis()) / 1000L).toInt()
    }

}