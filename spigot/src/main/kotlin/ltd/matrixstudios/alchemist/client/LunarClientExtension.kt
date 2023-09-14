package ltd.matrixstudios.alchemist.client

import co.aikar.commands.BaseCommand
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.client.commands.LunarClientCommands
import ltd.matrixstudios.alchemist.client.feature.NameTagFeature
import ltd.matrixstudios.alchemist.client.feature.TeamViewFeature
import ltd.matrixstudios.alchemist.module.PluginModule
import org.bukkit.Bukkit

/**
 * Class created on 9/13/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object LunarClientExtension : PluginModule {
    override fun onLoad() {
        NameTagFeature.startNametagUpdateTask()
    }

    override fun getCommands(): MutableList<BaseCommand> {
        return mutableListOf(
            LunarClientCommands
        )
    }

    override fun getModularConfigOption(): Boolean {
        return Bukkit.getPluginManager().isPluginEnabled("LunarClient-API")
    }
}