package ltd.matrixstudios.alchemist.staff.settings.toggle.types

import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.staff.settings.toggle.menu.SettingsMenu
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class ToggleStaffChatSetting(val profile: GameProfile) : Button() {
    override fun getMaterial(player: Player): Material {
        return Material.SIGN
    }

    override fun getDescription(player: Player): MutableList<String>? {
        val desc = mutableListOf<String>()
        desc.add(" ")
        desc.add(Chat.format("&7Toggle this setting to make it so"))
        desc.add(Chat.format("&7you are unable to see staff chat"))
        desc.add(Chat.format("&7Great for &5Streamers &7and &cYou&fTubers"))
        desc.add("")
        val hasMetadata = profile.hasMetadata("toggleSC")
        if (hasMetadata)
        {
            desc.add(Chat.format("&7► &eCurrently &coff"))
        } else {
            desc.add(Chat.format("&7► &eCurrently &aon"))
        }
        desc.add(" ")
        desc.add(Chat.format("&7Click to edit this value!"))
        desc.add(" ")
        return desc
    }

    override fun getDisplayName(player: Player): String? {
        return Chat.format("&eToggle Staff Chat")
    }

    override fun getData(player: Player): Short {
        return 0
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
        val hasMetadata = profile.hasMetadata("toggleSC")

        if (hasMetadata)
        {
            profile.metadata.remove("toggleSC")
            player.sendMessage(Chat.format("&eYou have toggled your staff chat &aon"))
            ProfileGameService.save(profile)
        } else {
            profile.metadata.addProperty("toggleSC", true)
            player.sendMessage(Chat.format("&eYou have toggled your staff chat &coff"))
            ProfileGameService.save(profile)
        }

        SettingsMenu(player).openMenu()
    }
}