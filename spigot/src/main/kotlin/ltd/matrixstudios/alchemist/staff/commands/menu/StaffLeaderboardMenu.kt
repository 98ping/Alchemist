package ltd.matrixstudios.alchemist.staff.commands.menu

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

/**
 * Class created on 9/16/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class StaffLeaderboardMenu(val player: Player, val preLoadedButtons: MutableMap<Int, Button>) : Menu(player)
{

    init
    {
        staticSize = 27
        placeholder = true
    }

    override fun getButtons(player: Player): MutableMap<Int, Button>
    {
        return preLoadedButtons
    }

    override fun getTitle(player: Player): String
    {
        return "Viewing Staff Leaderboards"
    }

    class LeaderboardPunishmentButton(val type: PunishmentType, val users: MutableList<GameProfile>) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.WOOL
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            val desc = mutableListOf<String>()
            var i = 1
            desc.add(Chat.format("&8&m---------------------------"))
            for (profile in users)
            {
                desc.add(
                    Chat.format(
                        getColoredSlot(i) + " &7- ${profile.getRankDisplay()} &8(${
                            profile.getExecutedCountByType(
                                type
                            )
                        })"
                    )
                )
                i++
            }
            desc.add(Chat.format("&8&m---------------------------"))

            return desc
        }

        fun getColoredSlot(index: Int): String
        {
            if (index == 1) return "&6#1"
            if (index == 2) return "&7#2"
            if (index == 3) return "&c#3"

            return "&a#${index}"
        }

        override fun getDisplayName(player: Player): String
        {
            return Chat.format(type.color + type.niceName + " Leaderboard &7(Top 10)")
        }

        override fun getData(player: Player): Short
        {
            return AlchemistAPI.getWoolColor(type.color).woolData.toShort()
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
        }

    }
}