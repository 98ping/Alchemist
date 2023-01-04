package ltd.matrixstudios.alchemist.staff.settings.toggle.types

import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.staff.settings.toggle.menu.SettingsMenu
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class ToggleModModeOnJoinSetting(val profile: GameProfile) : Button() {

    override fun getMaterial(player: Player): Material {
        return Material.WOOD_AXE
    }

    override fun getDescription(player: Player): MutableList<String>? {
        val desc = mutableListOf<String>()
        desc.add(" ")
        val hasMetadata = profile.hasMetadata("toggleMM")
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
        return Chat.format("&eToggle Mod Mode On Join")
    }

    override fun getData(player: Player): Short {
        return 0
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
        val hasMetadata = profile.hasMetadata("toggleMM")

        if (hasMetadata)
        {
            profile.metadata.remove("toggleMM")
            player.sendMessage(Chat.format("&eYou have toggled your mod mode on join &aon"))
            ProfileGameService.save(profile)
        } else {
            profile.metadata.addProperty("toggleMM", true)
            player.sendMessage(Chat.format("&eYou have toggled your mod mode on join &coff"))
            ProfileGameService.save(profile)
        }

        SettingsMenu(player).openMenu()
    }
}