package ltd.matrixstudios.alchemist.convert.luckperms

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * Class created on 6/13/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class LuckPermsConverterCommand : BaseCommand() {

    @CommandAlias("convertluckperms")
    @CommandPermission("alchemist.owner")
    fun convert(sender: Player) {
        if (Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) {
            LuckpermsRankConverter.convert(sender)
        } else {
            sender.sendMessage(Chat.format("&cYou must be running &aLuck&2Perms &cto execute this!"))
        }
    }
}