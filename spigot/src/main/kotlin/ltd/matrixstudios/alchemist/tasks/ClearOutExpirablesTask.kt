package ltd.matrixstudios.alchemist.tasks

import com.mongodb.client.model.Accumulators
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.BsonField
import com.mongodb.client.model.Filters
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.profiles.permissions.packet.PermissionUpdatePacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import org.bson.BsonBoolean
import org.bson.Document
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import kotlin.math.max

object ClearOutExpirablesTask : BukkitRunnable()
{

    private val rankGrantConnection = RankGrantService.collection
    private val punishmentCollection = PunishmentService.collection

    override fun run()
    {
        val maxIntString = Integer.MAX_VALUE.toLong()

        rankGrantConnection
            .aggregate(
                listOf(
                    Aggregates.unwind("\$expirable"),
                    Aggregates.match(Filters.eq("expirable.expired", false))
                )
            ).forEach {
                val unwindedDocument = it.get("expirable", Document::class.java)
                val addedAt = unwindedDocument.getString("addedAt").toLong()
                val duration = unwindedDocument.getString("duration").toLong()

                if (duration != maxIntString)
                {
                    if (it["removedBy"] == null
                        &&
                        duration != -1L
                        &&
                        (addedAt + duration) - System.currentTimeMillis() <= 0
                    )
                    {
                        val objectifiedGrant = Alchemist.gson.fromJson(it.toJson(), RankGrant::class.java)

                        objectifiedGrant.removedBy = UUID.fromString("00000000-0000-0000-0000-000000000000")
                        objectifiedGrant.removedReason = "Expired"

                        RankGrantService.saveSync(objectifiedGrant).whenComplete { t, u ->
                            RankGrantService.recalculateUUID(t.target)
                            AsynchronousRedisSender.send(PermissionUpdatePacket(t.target))
                        }
                    }
                }
            }

        val punishments = PunishmentService.handler.retrieveAll()
        punishments.forEach {
            if (!it.expirable.isActive() && it.removedBy == null)
            {
                it.removedBy = UUID.fromString("00000000-0000-0000-0000-000000000000")
                it.removedReason = "Expired"
                PunishmentService.saveSync(it)
            }
        }
    }
}

