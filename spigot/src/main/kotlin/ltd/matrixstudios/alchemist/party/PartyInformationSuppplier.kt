package ltd.matrixstudios.alchemist.party

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.party.Party
import java.util.*

object PartyInformationSuppplier {

    fun getLeaderFancyName(uuid: UUID) : String
    {
        return AlchemistAPI.getRankDisplay(uuid)
    }

    fun formatMembersString(party: Party) : List<String>
    {
        return party.members.map { AlchemistAPI.getRankDisplay(it.first) + " &7(" + it.second.name + ")" }
    }
}