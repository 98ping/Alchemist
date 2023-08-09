package ltd.matrixstudios.alchemist.essentials

import co.aikar.commands.BaseCommand
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.essentials.commands.*
import ltd.matrixstudios.alchemist.essentials.messages.MessageCommands
import ltd.matrixstudios.alchemist.module.PluginModule

object EssentialsModule : PluginModule {
    override fun onLoad() { }

    override fun getCommands(): MutableList<BaseCommand> {
        val commands = mutableListOf<BaseCommand>()

        commands.add(GamemodeCommands())
        commands.add(WorldCommands())
        commands.add(RegenerativeCommands())
        commands.add(ClearInventoryCommand())
        commands.add(CraftCommand())

        commands.add(KillCommand())
        commands.add(RenameCommand())

        commands.add(TeleportationCommands())

        commands.add(MessageCommands())

        return commands
    }

    override fun getModularConfigOption(): Boolean {
        return AlchemistSpigotPlugin.instance.config.getBoolean("modules.essentials")
    }
}