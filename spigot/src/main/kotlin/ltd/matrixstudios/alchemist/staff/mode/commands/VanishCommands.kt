package ltd.matrixstudios.alchemist.staff.mode.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.staff.mode.StaffSuiteManager
import ltd.matrixstudios.alchemist.staff.mode.StaffSuiteVisibilityHandler
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue

class VanishCommands : BaseCommand()
{

    @CommandAlias("vanish|v|byebye")
    @CommandPermission("alchemist.staffmode")
    fun vanish(player: Player)
    {
        if (player.hasMetadata("vanish"))
        {
            player.removeMetadata("vanish", AlchemistSpigotPlugin.instance)
            StaffSuiteVisibilityHandler.onDisableVisbility(player)
            player.sendMessage(Chat.format("&cYou have came out of vanish!"))
        } else
        {
            player.setMetadata("vanish", FixedMetadataValue(AlchemistSpigotPlugin.instance, true))
            StaffSuiteVisibilityHandler.onEnableVisibility(player)
            player.sendMessage(Chat.format("&aYou have entered vanish!"))
        }
    }

    @CommandAlias("?vis|qvis|amivanished|visible")
    @CommandPermission("alchemist.staffmode")
    fun qvis(player: Player)
    {
        val modded = StaffSuiteManager.isModMode(player)
        val vanish = player.hasMetadata("vanish")

        player.sendMessage(Chat.format("&6ModMode: &f" + if (modded) "&aYes" else "&cNo"))
        player.sendMessage(Chat.format("&6Vanished: &f" + if (vanish) "&aYes" else "&cNo"))
        player.sendMessage(Chat.format("&7&oBukkit respects and abides by these values"))
    }
}