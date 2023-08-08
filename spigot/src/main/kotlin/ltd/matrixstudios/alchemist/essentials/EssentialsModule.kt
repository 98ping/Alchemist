package ltd.matrixstudios.alchemist.essentials

import co.aikar.commands.BaseCommand
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.essentials.commands.ClearInventoryCommand
import ltd.matrixstudios.alchemist.essentials.commands.CraftCommand
import ltd.matrixstudios.alchemist.essentials.commands.GamemodeCommands
import ltd.matrixstudios.alchemist.essentials.commands.WorldCommands
import ltd.matrixstudios.alchemist.essentials.messages.MessageCommands
import ltd.matrixstudios.alchemist.module.PluginModule

object EssentialsModule : PluginModule {
    override fun onLoad() { }

    override fun getCommands(): MutableList<BaseCommand> {
        val commands = mutableListOf<BaseCommand>()

        commands.add(GamemodeCommands())
        commands.add(WorldCommands())
        commands.add(ClearInventoryCommand())
        commands.add(CraftCommand())

        commands.add(MessageCommands())

        return commands
    }

    override fun getModularConfigOption(): Boolean {
        return AlchemistSpigotPlugin.instance.config.getBoolean("modules.essentials")
    }
}