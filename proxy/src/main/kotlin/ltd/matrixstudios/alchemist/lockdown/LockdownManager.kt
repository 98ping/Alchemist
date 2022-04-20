package ltd.matrixstudios.alchemist.lockdown

import ltd.matrixstudios.alchemist.AlchemistBungee
import net.md_5.bungee.api.connection.ProxiedPlayer

object LockdownManager {

    fun serverIsOnLockdown() : Boolean {
        return AlchemistBungee.instance.configuration.getBoolean("lockdown")
    }

    fun hasClearance(player: ProxiedPlayer) : Boolean {
        return player.hasPermission("alchemist.clearance")
    }
}