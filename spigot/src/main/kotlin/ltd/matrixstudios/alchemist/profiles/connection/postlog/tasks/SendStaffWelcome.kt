package ltd.matrixstudios.alchemist.profiles.connection.postlog.tasks

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.profiles.connection.postlog.BukkitPostLoginTask
import ltd.matrixstudios.alchemist.profiles.getProfile
import ltd.matrixstudios.alchemist.staff.mode.StaffSuiteManager
import ltd.matrixstudios.alchemist.staff.requests.handlers.RequestHandler
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
object SendStaffWelcome : BukkitPostLoginTask
{

    override fun run(player: Player)
    {
        Bukkit.getScheduler().runTaskLater(AlchemistSpigotPlugin.instance, {
            val config = AlchemistSpigotPlugin.instance.config
            val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())

            if (player.hasPermission("alchemist.staff"))
            {

                if (config.getBoolean("staffmode.sendWelcomeMessage"))
                {
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
                    AlchemistSpigotPlugin.instance.config.getBoolean("modules.staffmode")
                )
                {
                    player.sendMessage(Chat.format("&7&oYou have been put into ModMode automatically"))
                    StaffSuiteManager.setStaffMode(player)
                }

                val profile = player.getProfile()

                if (profile != null)
                {
                    if (config.getBoolean("staffmode.sendSettingSummaryOnJoin"))
                    {
                        player.sendMessage(" ")
                        player.sendMessage(Chat.format("&6&lYour Settings"))
                        player.sendMessage(Chat.format("&7➥ &eReports: &f${if (RequestHandler.hasReportsEnabled(player)) "&aOn" else "&cOff"}"))
                        player.sendMessage(Chat.format("&7➥ &eStaff Chat: &f${if (profile.hasMetadata("allMSGSC")) "&aTogged On" else "&cCommand Only"}"))
                        player.sendMessage(
                            Chat.format(
                                "&7➥ &eAuto ModMode: &f${
                                    if (StaffSuiteManager.isModModeOnJoin(
                                            player
                                        )
                                    ) "&aOn" else "&cOff"
                                }"
                            )
                        )
                        player.sendMessage(Chat.format("&7➥ &eAuth Setup: &f${if (profile.getAuthStatus().hasSetup2fa) "&aYes :)" else "&cNo"}"))
                        player.sendMessage(Chat.format("&eUse the &f/staffsettings &eto edit these properties"))
                        player.sendMessage(" ")
                    }
                }
            }
        }, 10L)
    }
}