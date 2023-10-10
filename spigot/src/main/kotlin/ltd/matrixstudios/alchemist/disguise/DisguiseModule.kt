package ltd.matrixstudios.alchemist.disguise

import co.aikar.commands.BaseCommand
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.disguise.commands.DisguiseCacheCommands
import ltd.matrixstudios.alchemist.disguise.commands.DisguiseCommand
import ltd.matrixstudios.alchemist.module.PluginModule
import org.bukkit.Bukkit

object DisguiseModule : PluginModule
{
    override fun onLoad()
    {
        DisguiseService.loadAllSkins()
    }

    override fun getCommands(): MutableList<BaseCommand>
    {
        return mutableListOf(
            DisguiseCommand,
            DisguiseCacheCommands
        )
    }

    override fun getModularConfigOption(): Boolean
    {
        return Bukkit.getPluginManager().isPluginEnabled(
            "DisguiseAPI"
        ) && AlchemistSpigotPlugin.instance.config.getBoolean(
            "modules.disguise"
        )
    }
}