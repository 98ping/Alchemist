package ltd.matrixstudios.alchemist.permission

import com.velocitypowered.api.permission.PermissionFunction
import com.velocitypowered.api.permission.PermissionProvider
import com.velocitypowered.api.permission.PermissionSubject
import com.velocitypowered.api.permission.Tristate
import com.velocitypowered.api.proxy.Player
import ltd.matrixstudios.alchemist.models.profile.GameProfile

class AlchemistPermissionProvider(private val player: Player, private val profile: GameProfile) : PermissionProvider, PermissionFunction {

    override fun createFunction(subject: PermissionSubject): PermissionFunction {
        check(subject == player) { "createFunction called with without using a Player" }
        return this
    }

    override fun getPermissionValue(permission: String): Tristate {
        return profile.getPermissions()[permission]?.let { Tristate.fromBoolean(it) } ?: Tristate.FALSE
    }
}