package ltd.matrixstudios.alchemist.service.ranks

import com.google.gson.reflect.TypeToken
import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.flatfile.FlatfileUtilities
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.grant.types.scope.GrantScope
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor
import ltd.matrixstudios.alchemist.service.GeneralizedService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import java.io.File
import java.lang.reflect.Type
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap


object RankService : GeneralizedService
{

    var handler = Alchemist.dataHandler.createStoreType<String, Rank>(Alchemist.getDataStoreMethod())
    var ranks = ConcurrentHashMap<String, Rank>()

    // flatfile info
    lateinit var jsonFile: File
    private val type: Type = object : TypeToken<MutableList<Rank>>()
    {}.type

    var FALLBACK_RANK =
        Rank("unknown", "Unknown", "Unknown", 1, arrayListOf(), arrayListOf(), "&f", "&f") //lunar.gg feature

    var FALLBACK_GRANT = RankGrant(
        FALLBACK_RANK.id,
        UUID.randomUUID(),
        UUID.randomUUID(),
        "Fallback Grant",
        Long.MAX_VALUE,
        DefaultActor(Executor.CONSOLE, ActorType.GAME),
        GrantScope("Fallback Grant", mutableListOf(), true)
    )

    fun loadRanks()
    {
        if (!Alchemist.usingMongo)
        {
            jsonFile = File(Alchemist.FlatFileConnectionPool.directory, "ranks.json")
        }

        //since there are only a limited amount of ranks we can just load on startup
        getValues().thenAccept {
            for (rank in it)
            {
                ranks[rank.id] = rank
            }

            if (byId("default") == null && findFirstAvailableDefaultRank() == null)
            {
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
                        default = true
                    )
                )
            }
        }
    }

    fun scanRank(rank: Rank): CompletableFuture<Collection<GameProfile>>
    {
        return CompletableFuture.supplyAsync {
            val profiles = ProfileGameService.handler.retrieveAll()
            val entries = mutableListOf<GameProfile>()

            for (prof in profiles)
            {
                if (prof.getCurrentRank().id == rank.id)
                {
                    entries.add(prof)
                }
            }

            return@supplyAsync entries
        }
    }

    fun getValues(): CompletableFuture<Collection<Rank>>
    {
        return if (Alchemist.usingMongo)
        {
            handler.retrieveAllAsync()
        } else
        {
            CompletableFuture.completedFuture(
                FlatfileUtilities.getAllEntries(jsonFile, type, mutableListOf())
            )
        }
    }

    fun save(rank: Rank): CompletableFuture<Void>
    {
        if (Alchemist.usingMongo)
        {
            return CompletableFuture.runAsync {
                handler.store(rank.id, rank)
            }.thenAccept {
                ranks[rank.id] = rank
            }
        } else
        {
            ranks[rank.id] = rank
            FlatfileUtilities.writeToFile(jsonFile, ranks.values, type)

            return CompletableFuture.completedFuture(null)
        }
    }

    fun delete(rank: Rank): CompletableFuture<Void>
    {
        ranks.remove(rank.id)

        return CompletableFuture.runAsync {
            handler.delete(rank.id)
        }
    }

    fun getAllRanksInOrder(): Collection<Rank>
    {
        return ranks.values.sortedByDescending { it.weight }
    }

    fun getRanksInOrder(): Collection<Rank>
    {
        val final = mutableListOf<Rank>()

        for (rank in ranks.values.sortedByDescending { it.weight })
        {
            if (rank.getRankScope().global || rank.getRankScope().appliesOn(Alchemist.globalServer))
            {
                final.add(rank)
            }
        }

        return final
    }


    fun findFirstAvailableDefaultRank(): Rank?
    {
        return ranks.values.firstOrNull { it.default }
    }

    fun byId(id: String): Rank?
    {
        if (ranks.containsKey(id))
        {
            return ranks[id]
        }

        return null
    }

    fun byIdAnyCase(id: String): Rank?
    {
        for (rank in ranks.values)
        {
            if (rank.id.equals(id, ignoreCase = true)) return rank
        }

        return null
    }
}