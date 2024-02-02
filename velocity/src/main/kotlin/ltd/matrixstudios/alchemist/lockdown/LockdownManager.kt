package ltd.matrixstudios.alchemist.lockdown

import com.velocitypowered.api.proxy.Player
import ltd.matrixstudios.alchemist.AlchemistVelocity


object LockdownManager {
    fun serverIsOnLockdown() : Boolean {
        return AlchemistVelocity.instance.config.getBoolean("lockdown")
    }

    fun hasClearance(player: Player) : Boolean {
        return player.hasPermission("alchemist.clearance")
    }
}