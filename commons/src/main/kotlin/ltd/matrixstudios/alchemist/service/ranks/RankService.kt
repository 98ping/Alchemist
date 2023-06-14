package ltd.matrixstudios.alchemist.service.ranks

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.service.GeneralizedService
import java.util.stream.Collectors
import kotlin.collections.ArrayList

object RankService : GeneralizedService {

    var handler = Alchemist.dataHandler.createStoreType<String, Rank>(DataStoreType.MONGO)

    var ranks = mutableMapOf<String, Rank>()

    fun loadRanks() {
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

        //since there are only a limited amount of ranks we can just load on startup
        for (rank in getValues())
        {
            ranks[rank.id] = rank
        }
    }

    fun getValues() : Collection<Rank> {
        return handler.retrieveAll()
    }

    fun save(rank: Rank) {
        ranks[rank.id] = rank

        handler.storeAsync(rank.id, rank)
    }

    fun getRanksInOrder() : Collection<Rank> {
        return ranks.values.stream().sorted { o1, o2 ->  o2.weight - o1.weight }.collect(Collectors.toList())
    }


    fun findFirstAvailableDefaultRank() : Rank? {
        return getValues().firstOrNull { it.default }
    }

    fun byId(id: String) : Rank? {
        if (ranks.containsKey(id))
        {
            return ranks[id]
        }

        return getValues().firstOrNull { it.name.equals(id, ignoreCase = true) }
    }
}