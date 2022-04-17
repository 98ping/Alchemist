package ltd.matrixstudios.alchemist.tasks

import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import org.bukkit.scheduler.BukkitRunnable

object ClearOutExpirablesTask : BukkitRunnable() {

    override fun run() {
        RankGrantService.clearOutModels()

        PunishmentService.clearOutModels()
    }
}