package ltd.matrixstudios.alchemist.service.ranks

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.service.GeneralizedService
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors
import kotlin.collections.ArrayList

object RankService : GeneralizedService {

    var handler = Alchemist.dataHandler.createStoreType<String, Rank>(DataStoreType.MONGO)

    var ranks = mutableMapOf<String, Rank>()

    fun loadRanks() {
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

        //since there are only a limited amount of ranks we can just load on startup
        getValues().thenAccept {
            for (rank in it) {
                ranks[rank.id] = rank
            }
        }
    }

    fun getValues() : CompletableFuture<Collection<Rank>> {
        return handler.retrieveAllAsync()
    }

    fun save(rank: Rank) {
        ranks[rank.id] = rank

        handler.storeAsync(rank.id, rank)
    }

    fun getRanksInOrder() : Collection<Rank> {
        return ranks.values.stream().sorted { o1, o2 ->  o2.weight - o1.weight }.collect(Collectors.toList())
    }


    fun findFirstAvailableDefaultRank() : Rank? {
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
        val future: CompletableFuture<Rank?> = getValues().thenApply {
            for (rank in it)
            {
                if (rank.name.equals(id, ignoreCase = true))
                {
                    return@thenApply rank
                }
            }

            return@thenApply null
        }

        return future.get()
    }
}