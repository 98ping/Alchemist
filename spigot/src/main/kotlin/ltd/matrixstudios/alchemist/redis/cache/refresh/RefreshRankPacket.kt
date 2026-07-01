package ltd.matrixstudios.alchemist.redis.cache.refresh

import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.profiles.permissions.packet.PermissionUpdateAllPacket
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.ranks.RankService
import java.util.concurrent.ConcurrentHashMap

class RefreshRankPacket : RedisPacket("refresh-rank")
{

    override fun action()
    {
        RankService.getValues().whenComplete { ranks, _ ->
            val rebuilt = ConcurrentHashMap<String, Rank>()

            for (rank in ranks)
            {
                rebuilt[rank.id] = rank
            }

            RankService.ranks = rebuilt

            PermissionUpdateAllPacket().action()
        }
    }
}
