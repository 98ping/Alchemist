package ltd.matrixstudios.alchemist.punishment.limitation

import com.google.common.collect.HashBasedTable
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Class created on 6/17/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object PunishmentLimitationUnderstander
{

    private val punishmentTimes: HashBasedTable<UUID, Int, Long> = HashBasedTable.create()

    private lateinit var runnable: BukkitRunnable

    fun load()
    {
        runnable = object : BukkitRunnable()
        {
            override fun run()
            {
                for (entry in punishmentTimes.cellSet())
                {
                    val stamp = entry.value

                    if (System.currentTimeMillis().minus(stamp) >= TimeUnit.MINUTES.toMillis(5L))
                    {
                        punishmentTimes.remove(entry.rowKey, entry.columnKey)
                    }
                }
            }

        }.apply { this.runTaskTimer(AlchemistSpigotPlugin.instance, 80L, 80L) }
    }

    fun getTimes(player: UUID): Int
    {
        return punishmentTimes.row(player).keys.firstOrNull() ?: 0
    }

    fun canApplyPunishment(player: UUID): Boolean
    {
        if (!punishmentTimes.containsRow(player)) return true

        val amountAndStamp = punishmentTimes.row(player)

        val amount = amountAndStamp.keys.first() ?: 0
        val stamp = amountAndStamp.values.first() ?: System.currentTimeMillis()

        return !(amount >= 5 && System.currentTimeMillis().minus(stamp) < TimeUnit.MINUTES.toMillis(5L))
    }

    fun getDurationString(player: UUID): String
    {
        if (!punishmentTimes.containsRow(player)) return "0 seconds"

        val amountAndStamp = punishmentTimes.row(player)
        val stamp = amountAndStamp.values.first() ?: System.currentTimeMillis()

        return TimeUtil.formatDuration((stamp.plus(TimeUnit.MINUTES.toMillis(5L))).minus(System.currentTimeMillis()))
    }

    fun equipCooldown(player: UUID)
    {
        if (punishmentTimes.containsRow(player))
        {
            val amountAndStamp = punishmentTimes.row(player)

            val amount = amountAndStamp.keys.first() ?: 0
            val oldStamp = punishmentTimes.values().first() ?: System.currentTimeMillis()

            punishmentTimes.remove(player, amount)
            punishmentTimes.put(player, amount + 1, oldStamp)
        } else punishmentTimes.put(player, 1, System.currentTimeMillis())
    }
}