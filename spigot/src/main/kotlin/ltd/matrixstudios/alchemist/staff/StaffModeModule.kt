package ltd.matrixstudios.alchemist.staff

import co.aikar.commands.BaseCommand
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.module.PluginModule
import ltd.matrixstudios.alchemist.staff.commands.InventoryViewCommand
import ltd.matrixstudios.alchemist.staff.mode.action.StaffModeActionBarHandler
import ltd.matrixstudios.alchemist.staff.mode.commands.FreezeCommand
import ltd.matrixstudios.alchemist.staff.mode.commands.StaffCommands
import ltd.matrixstudios.alchemist.staff.mode.commands.VanishCommands
import ltd.matrixstudios.alchemist.staff.settings.edit.EditModModeCommand

/**
 * Class created on 7/21/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object StaffModeModule : PluginModule
{
    override fun onLoad()
    {
        StaffModeActionBarHandler().runTaskTimer(AlchemistSpigotPlugin.instance, 20L, 20L)
    }

    override fun getCommands(): MutableList<BaseCommand>
    {
        val list = mutableListOf<BaseCommand>()

        list.add(FreezeCommand())
        list.add(StaffCommands())
        list.add(VanishCommands())

        list.add(EditModModeCommand())

        if (AlchemistSpigotPlugin.instance.config.getBoolean("staffmode.invseeCommand"))
        {
            list.add(InventoryViewCommand())
        }

        return list

    }

    override fun getModularConfigOption(): Boolean
    {
        return AlchemistSpigotPlugin.instance.config.getBoolean("modules.staffmode")
    }
}