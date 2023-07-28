package ltd.matrixstudios.alchemist.profiles.connection.postlog.tasks

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.aikar.ACFCommandController
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.profiles.connection.postlog.BukkitPostLoginTask
import ltd.matrixstudios.alchemist.staff.mode.StaffSuiteManager
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.text.SimpleDateFormat
import java.util.*

/**
 * Class created on 7/20/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object SendStaffWelcome : BukkitPostLoginTask {

    override fun run(player: Player) {
        Bukkit.getScheduler().runTaskLater(AlchemistSpigotPlugin.instance, {
            val config = AlchemistSpigotPlugin.instance.config
            val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())

            if (player.hasPermission("alchemist.staff")) {

                if (config.getBoolean("staffmode.sendWelcomeMessage")) {
                    player.sendMessage(" ")
                    player.sendMessage(Chat.format("&eWelcome back, " + AlchemistAPI.getRankDisplay(player.uniqueId)))
                    player.sendMessage(Chat.format("&eIt is currently &d" + dateFormat.format(Date(System.currentTimeMillis()))))
                    player.sendMessage(Chat.format("&eEdit your mod mode with &a/editmodmode"))
                    player.sendMessage(" ")

                }

                if (AlchemistSpigotPlugin.instance.config.getBoolean("staffmode.autoEquipOnJoin")
                    &&
                    StaffSuiteManager.isModModeOnJoin(player)
                    &&
                    AlchemistSpigotPlugin.instance.config.getBoolean("modules.staffmode"))
                {
                    player.sendMessage(Chat.format("&7&oYou have been put into ModMode automatically"))
                    StaffSuiteManager.setStaffMode(player)
                }
            }
        }, 10L)
    }
}