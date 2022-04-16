package ltd.matrixstudios.alchemist.permissions

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.util.Chat.format
import org.bukkit.entity.Player
import org.bukkit.permissions.PermissionAttachment
import sun.java2d.cmm.Profile
import java.lang.reflect.Field
import java.util.*


object AccessiblePermissionHandler {

    private val permissionAttachmentMap: MutableMap<UUID, PermissionAttachment> = HashMap()

    lateinit var permissionField: Field

    fun load() {
        try {
            permissionField = PermissionAttachment::class.java.getDeclaredField("permissions")
            permissionField.isAccessible = true
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
    }

    fun remove(player: Player) {
        permissionAttachmentMap.remove(player.uniqueId)
    }

    fun update(player: Player, perms: Map<String?, Boolean?>?) {
        permissionAttachmentMap.putIfAbsent(player.uniqueId, player.addAttachment(AlchemistSpigotPlugin.instance))
        try {
            val attachment = permissionAttachmentMap[player.uniqueId]
            permissionField.set(attachment, perms)
            player.recalculatePermissions()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        val profile: GameProfile = AlchemistAPI.quickFindProfile(player.uniqueId) ?: return
        player.displayName = format(profile.getCurrentRank()?.color.toString() + player.name)
    }
}