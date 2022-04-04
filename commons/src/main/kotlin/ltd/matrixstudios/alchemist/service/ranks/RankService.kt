package ltd.matrixstudios.alchemist.service.ranks

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.ranks.Rank

object RankService {

    fun getValues() : List<Rank> {
        return Alchemist.dataflow.createQuery(Rank::class.java).toList()
    }

    fun save(rank: Rank) {
        Alchemist.dataflow.save(rank.id, rank, Rank::class.java)
    }

    fun byId(id: String) : Rank? {
        return getValues().firstOrNull { it.id == id }
    }
}