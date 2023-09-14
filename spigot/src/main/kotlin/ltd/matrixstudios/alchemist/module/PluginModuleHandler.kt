package ltd.matrixstudios.alchemist.module

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.aikar.ACFCommandController
import ltd.matrixstudios.alchemist.chat.ChatModule
import ltd.matrixstudios.alchemist.client.LunarClientExtension
import ltd.matrixstudios.alchemist.essentials.EssentialsModule
import ltd.matrixstudios.alchemist.profiles.ProfileModule
import ltd.matrixstudios.alchemist.profiles.permissions.PermissionModule
import ltd.matrixstudios.alchemist.punishment.PunishmentModule
import ltd.matrixstudios.alchemist.servers.ServerModule
import ltd.matrixstudios.alchemist.staff.StaffModeModule

/**
 * Class created on 7/21/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object PluginModuleHandler {

    private val modules = listOf(
        ProfileModule,
        StaffModeModule,
        PunishmentModule,
        PermissionModule,
        ServerModule,
        ChatModule,
        EssentialsModule,
        LunarClientExtension
    )

    fun loadModules() {
        for (module in modules) {
            if (!module.getModularConfigOption()) continue

            module.onLoad()

            for (command in module.getCommands()) {
                AlchemistSpigotPlugin.instance.commandManager.registerCommand(command)
            }
        }
    }
}