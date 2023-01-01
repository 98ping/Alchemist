package ltd.matrixstudios.alchemist.commands.notes.menu.button

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.profile.notes.ProfileNote
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.*

class PlayerNotesButton(val note: ProfileNote, val targetProfile: GameProfile) : Button() {

    override fun getMaterial(player: Player): Material {
        return Material.SKULL_ITEM
    }

    override fun getDescription(player: Player): MutableList<String>? {
        val desc = mutableListOf<String>()

        desc.add(Chat.format("&7&m-------------------"))
        desc.add(Chat.format("&eAdded by: &c" + AlchemistAPI.getRankDisplay(note.author)))
        desc.add(Chat.format("&eNote: &c" + note.content))
        desc.add(Chat.format("&7&m-------------------"))
        desc.add(Chat.format("&eClick to remove this note"))

        return desc
    }

    override fun getDisplayName(player: Player): String? {
        return Chat.format("&e${Date(note.createdAt)}")
    }

    override fun getData(player: Player): Short {
        return 0
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
        targetProfile.notes.remove(note)
        ProfileGameService.save(targetProfile)

        player.sendMessage(Chat.format("&cRemoved note from ${targetProfile.username}."))
        player.closeInventory()
    }
}