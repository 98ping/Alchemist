package ltd.matrixstudios.alchemist.profiles

import co.aikar.commands.BaseCommand
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.aikar.ACFCommandController
import ltd.matrixstudios.alchemist.module.PluginModule
import ltd.matrixstudios.alchemist.profiles.commands.player.*
import ltd.matrixstudios.alchemist.profiles.commands.sibling.SiblingCommands
import ltd.matrixstudios.alchemist.util.Chat

/**
 * Class created on 7/21/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object ProfileModule : PluginModule {

    override fun onLoad() {
        val start = System.currentTimeMillis()
        BukkitProfileAdaptation.loadAllEvents()

        Chat.sendConsoleMessage("&b[Profiles] &fAll profile events loaded in &b" + System.currentTimeMillis().minus(start) + "ms")
    }

    override fun getCommands(): MutableList<BaseCommand> {
       val list = mutableListOf<BaseCommand>()

        if (AlchemistSpigotPlugin.instance.config.getBoolean("freeRank.enabled")) {
            list.add(FreerankCommand())
        }

        list.add(ListCommand())
        list.add(LookupCommand())
        list.add(PlayerAdminCommand())
        list.add(SudoCommand())
        list.add(WipeGrantsCommand)
        list.add(WipeProfileCommand())
        list.add(SiblingCommands())

        return list
    }

    override fun getModularConfigOption(): Boolean {
        return true
    }
}