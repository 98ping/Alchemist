package ltd.matrixstudios.alchemist.staff

import co.aikar.commands.BaseCommand
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.module.PluginModule
import ltd.matrixstudios.alchemist.profiles.BukkitProfileAdaptation
import ltd.matrixstudios.alchemist.staff.commands.JumpToPlayerCommand
import ltd.matrixstudios.alchemist.staff.commands.OnlineStaffCommand
import ltd.matrixstudios.alchemist.staff.commands.StaffchatCommand
import ltd.matrixstudios.alchemist.staff.commands.TimelineCommand
import ltd.matrixstudios.alchemist.staff.mode.commands.FreezeCommand
import ltd.matrixstudios.alchemist.staff.mode.commands.StaffCommands
import ltd.matrixstudios.alchemist.staff.mode.commands.VanishCommands
import ltd.matrixstudios.alchemist.staff.requests.commands.ReportCommand
import ltd.matrixstudios.alchemist.staff.requests.commands.RequestCommand
import ltd.matrixstudios.alchemist.staff.settings.edit.EditModModeCommand
import ltd.matrixstudios.alchemist.staff.settings.toggle.SettingsCommand
import ltd.matrixstudios.alchemist.util.Chat
import org.checkerframework.common.util.report.qual.ReportCall

/**
 * Class created on 7/21/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object StaffModeModule : PluginModule {
    override fun onLoad() { }

    override fun getCommands(): MutableList<BaseCommand> {
        val list = mutableListOf<BaseCommand>()

        list.add(FreezeCommand())
        list.add(StaffCommands())
        list.add(VanishCommands())

        list.add(ReportCommand())
        list.add(RequestCommand())

        list.add(SettingsCommand())
        list.add(EditModModeCommand())

        return list

    }

    override fun getModularConfigOption(): Boolean {
        return AlchemistSpigotPlugin.instance.config.getBoolean("modules.staffmode")
    }
}