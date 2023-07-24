package ltd.matrixstudios.alchemist.service.ranks

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.service.GeneralizedService
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.ForkJoinPool


object RankService : GeneralizedService {

    var handler = Alchemist.dataHandler.createStoreType<String, Rank>(DataStoreType.MONGO)

    var ranks = ConcurrentHashMap<String, Rank>()

    var FALLBACK_RANK = Rank("unknown", "Unknown", "Unknown", 1, arrayListOf(), arrayListOf(), "&f", "&f") //lunar.gg feature

    fun loadRanks() {
        //since there are only a limited amount of ranks we can just load on startup
        getValues().thenAccept {
            for (rank in it) {
                ranks[rank.id] = rank
            }
        }

        if (byId("default") == null && findFirstAvailableDefaultRank() == null) {
            save(
                Rank(
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

    fun getValues() : CompletableFuture<Collection<Rank>> {
        return handler.retrieveAllAsync()
    }

    fun save(rank: Rank) {
        ranks[rank.id] = rank

        CompletableFuture.runAsync {
            handler.store(rank.id, rank)
        }
    }

    fun getRanksInOrder() : Collection<Rank> {
        return ranks.values.sortedByDescending { it.weight }
    }


    fun findFirstAvailableDefaultRank() : Rank? {
        val cachedDefault = ranks.values.firstOrNull { it.default }

        if (cachedDefault != null) return cachedDefault

        val future: CompletableFuture<Rank?> = getValues().thenApply {
            for (rank in it)
            {
                if (rank.default)
                {
                    return@thenApply rank
                }
            }

            return@thenApply null
        }

            return future.get()
    }

    fun byId(id: String) : Rank? {
        if (ranks.containsKey(id)) {
            return ranks[id]
        }

        return null
    }
}