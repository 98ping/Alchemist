package ltd.matrixstudios.alchemist.broadcasts

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.broadcasts.model.BroadcastMessage
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

/**
 * Class created on 6/17/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object BroadcastService {

    val messages = mutableListOf<BroadcastMessage>()
    var interval = 60

    lateinit var runnable: BukkitRunnable
    var lastMessage: BroadcastMessage? = null

    fun loadMessages() {
        val section = "autobroadcast.messages"

        for (message in AlchemistSpigotPlugin.instance.config.getConfigurationSection(section).getKeys(true))
        {
            val message1 = BroadcastMessage(
                message,
                AlchemistSpigotPlugin.instance.config.getStringList("$section.$message")
            )

            messages.add(message1)
        }

        interval = AlchemistSpigotPlugin.instance.config.getInt("autobroadcast.interval")

        runnable = object : BukkitRunnable() {
            override fun run() {

                if (messages.size == 1)
                {
                    for (line in messages[0].lines) {
                        Bukkit.broadcastMessage(Chat.format(line))
                    }

                    lastMessage = messages[0]

                    return
                }

                if (lastMessage == null)
                {
                    val selected = messages.random()

                    for (line in selected.lines) {
                        Bukkit.broadcastMessage(Chat.format(line))
                    }
                } else {
                    val excluding = messages.dropWhile { it.id == lastMessage!!.id }
                    val random = excluding.random()

                    for (line in random.lines) {
                        Bukkit.broadcastMessage(Chat.format(line))
                    }

                    lastMessage = random
                }
            }
        }.apply { this.runTaskTimer(AlchemistSpigotPlugin.instance, interval * 20L, interval * 20L) }
    }
}