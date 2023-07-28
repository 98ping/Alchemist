package ltd.matrixstudios.alchemist.staff.settings.toggle.types


import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.staff.settings.toggle.menu.SettingsMenu
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import okhttp3.internal.addHeaderLenient
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class AllMessagesAreStaffSetting(val profile: GameProfile) : Button() {
    override fun getMaterial(player: Player): Material {
        return Material.PAPER
    }

    override fun getDescription(player: Player): MutableList<String>? {
        val desc = mutableListOf<String>()
        desc.add(" ")
        desc.add(Chat.format("&7Toggle this setting to make it so"))
        desc.add(Chat.format("&7every new message sent by you automaticially"))
        desc.add(Chat.format("&7ends up in &bStaff Chat"))
        desc.add("")
        val hasMetadata = profile.hasMetadata("allMSGSC")
        if (hasMetadata)
        {
            desc.add(Chat.format("&7► &eCurrently &aon"))
        } else {
            desc.add(Chat.format("&7► &eCurrently &coff"))
        }
        desc.add(" ")
        desc.add(Chat.format("&7Click to edit this value!"))
        desc.add(" ")
        return desc
    }

    override fun getDisplayName(player: Player): String? {
        return Chat.format("&eToggle All Messages Go To Staff Chat")
    }

    override fun getData(player: Player): Short {
        return 0
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
        val hasMetadata = profile.hasMetadata("allMSGSC")

        if (hasMetadata) {
            profile.metadata.remove("allMSGSC")
            player.sendMessage(Chat.format("&eAll message will &cnot &ego into staff chat!"))
            ProfileGameService.save(profile)
        } else {
            profile.metadata.addProperty("allMSGSC", true)
            player.sendMessage(Chat.format("&eAll messages &awill &ego into staff chat!"))
            ProfileGameService.save(profile)
        }

        SettingsMenu(player).openMenu()
    }
}