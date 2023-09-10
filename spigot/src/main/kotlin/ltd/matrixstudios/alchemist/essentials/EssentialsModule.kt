package ltd.matrixstudios.alchemist.essentials

import co.aikar.commands.BaseCommand
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.essentials.commands.*
import ltd.matrixstudios.alchemist.essentials.listener.EntityEditorListener
import ltd.matrixstudios.alchemist.essentials.menus.entity.EntityEditorMenu
import ltd.matrixstudios.alchemist.essentials.messages.MessageCommands
import ltd.matrixstudios.alchemist.module.PluginModule
import org.bukkit.Bukkit

object EssentialsModule : PluginModule {
    override fun onLoad() {
        Bukkit.getServer().pluginManager.registerEvents(EntityEditorListener(), AlchemistSpigotPlugin.instance)
    }

    override fun getCommands(): MutableList<BaseCommand> {
        val commands = mutableListOf<BaseCommand>()

        commands.add(GamemodeCommands())
        commands.add(WorldCommands())
        commands.add(RegenerativeCommands())
        commands.add(ClearInventoryCommand())
        commands.add(CraftCommand())

        commands.add(KillCommand())
        commands.add(RenameCommand())

        commands.add(InventoryCopyingCommands())
        commands.add(TeleportationCommands())

        commands.add(EntityCommands)

        commands.add(MessageCommands())

        return commands
    }

    override fun getModularConfigOption(): Boolean {
        return AlchemistSpigotPlugin.instance.config.getBoolean("modules.essentials")
    }
}