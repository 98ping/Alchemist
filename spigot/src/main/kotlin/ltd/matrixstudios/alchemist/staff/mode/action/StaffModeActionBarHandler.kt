package ltd.matrixstudios.alchemist.staff.mode.action

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.chat.ChatService
import ltd.matrixstudios.alchemist.profiles.getProfile
import ltd.matrixstudios.alchemist.staff.mode.StaffSuiteManager
import ltd.matrixstudios.alchemist.util.Chat
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class StaffModeActionBarHandler : BukkitRunnable()
{

    override fun run()
    {
        for (player in Bukkit.getOnlinePlayers())
        {
            if (StaffSuiteManager.isModMode(player))
            {
                AlchemistSpigotPlugin.instance.audience.player(player).sendActionBar(
                    Component.text(
                        Chat.format(
                            "&eChat&7: &r${getChatString()} &7｜ &eVanish&7: &r${
                                getVanishString(
                                    player
                                )
                            } &7｜ &eStaff Chat&7: &r${getStaffChat(player)}"
                        )
                    )
                )
            }
        }
    }


    fun getChatString(): String
    {
        if (ChatService.muted) return "&cMuted"
        if (ChatService.slowed) return "&6Slowed"

        return "&fNormal"
    }

    fun getVanishString(player: Player): String
    {
        if (player.hasMetadata("vanish")) return "&aInvisible"

        return "&cVisible"
    }

    fun getStaffChat(player: Player): String
    {
        val profile = player.getProfile() ?: return "&cDisabled"

        if (player.hasPermission("alchemist.staff") && profile.hasMetadata("allMSGSC"))
        {
            return "&aEnabled"
        }

        return "&cDisabled"
    }
}