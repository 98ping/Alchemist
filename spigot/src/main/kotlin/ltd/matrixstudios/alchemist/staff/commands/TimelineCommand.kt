package ltd.matrixstudios.alchemist.staff.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player
import java.text.SimpleDateFormat
import java.util.*

class TimelineCommand : BaseCommand()
{

    @CommandAlias("timeline|stafftimeline")
    @CommandPermission("alchemist.staff")
    fun timeline(player: Player)
    {
        val profile = AlchemistAPI.syncFindProfile(player.uniqueId) ?: return
        val grants = RankGrantService.getFromCache(profile.uuid).filter { it.getGrantable().staff }
            .sortedByDescending { System.currentTimeMillis().minus(it.expirable.addedAt) }
        val alreadyShownRanks = mutableListOf<Rank>()
        val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
        var barMessage = "&7█"

        player.sendMessage(Chat.format("&7&m------------------------"))
        player.sendMessage(Chat.format("&6&lStaff Timeline"))
        player.sendMessage(" ")
        if (grants.isEmpty())
        {
            player.sendMessage(Chat.format("&cNo previous staff ranks!"))
        }

        for (grant in grants)
        {
            val rank = grant.getGrantable()

            if (alreadyShownRanks.contains(rank))
            {
                continue
            }

            barMessage += rank.color + "█"
            player.sendMessage(
                Chat.format(
                    rank.prefix + rank.color + player.name + "&7: &fPromoted " + dateFormat.format(
                        grant.expirable.addedAt
                    )
                )
            )
            alreadyShownRanks.add(rank)
        }
        player.sendMessage(" ")
        player.sendMessage(Chat.format("&6&lBlock Timeline"))
        player.sendMessage(" ")
        player.sendMessage(Chat.format(barMessage))
        player.sendMessage(" ")
        player.sendMessage(Chat.format("&7&m------------------------"))


    }
}