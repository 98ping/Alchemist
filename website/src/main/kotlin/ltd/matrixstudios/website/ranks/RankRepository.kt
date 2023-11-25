package ltd.matrixstudios.website.ranks

import ltd.matrixstudios.alchemist.models.ranks.Rank
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Class created on 11/24/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
@Repository
interface RankRepository : MongoRepository<Rank, String> {
    override fun findAll(): List<Rank>
}