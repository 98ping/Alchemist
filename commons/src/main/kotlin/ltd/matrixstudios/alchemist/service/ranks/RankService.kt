package ltd.matrixstudios.alchemist.service.ranks

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.ranks.Rank
import java.util.*
import kotlin.collections.ArrayList

object RankService  {

    var handler = Alchemist.dataHandler.createStoreType<String, Rank>(DataStoreType.MONGO)

    fun createDefaultRankIfDoesntExist() {
        if (byId("default") == null && findFirstAvailableDefaultRank() == null) {
            save(Rank(
                "default",
                "Default",
                "Default",
                1,
                ArrayList(),
                ArrayList(),
                "",
                "&7",
                staff = false,
                default = true)
            )
        }
    }

    fun getValues() : Collection<Rank> {
        return handler.retrieveAll()
    }

    fun save(rank: Rank) {
        handler.store(rank.id, rank)
    }

    fun findFirstAvailableDefaultRank() : Rank? {
        return getValues().firstOrNull { it.default }
    }

    fun byId(id: String) : Rank? {
        return getValues().firstOrNull { it.name.equals(id, ignoreCase = true) }
    }
}