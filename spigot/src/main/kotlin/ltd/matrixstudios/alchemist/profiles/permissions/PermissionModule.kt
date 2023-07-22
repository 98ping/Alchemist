package ltd.matrixstudios.alchemist.profiles.permissions

import co.aikar.commands.BaseCommand
import ltd.matrixstudios.alchemist.module.PluginModule
import ltd.matrixstudios.alchemist.profiles.permissions.command.PermissionEditCommands
import ltd.matrixstudios.alchemist.util.Chat

/**
 * Class created on 7/21/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object PermissionModule : PluginModule {
    override fun onLoad() {
        val permissionStart = System.currentTimeMillis()
        AccessiblePermissionHandler.load()

        Chat.sendConsoleMessage(
            "&9[Permissions] &fAll permissions loaded in &9" + System.currentTimeMillis().minus(permissionStart) + "ms"
        )
    }

    override fun getCommands(): MutableList<BaseCommand> {
        val list = mutableListOf<BaseCommand>()
        list.add(PermissionEditCommands())

        return list
    }

    override fun getModularConfigOption(): Boolean {
        return true
    }
}