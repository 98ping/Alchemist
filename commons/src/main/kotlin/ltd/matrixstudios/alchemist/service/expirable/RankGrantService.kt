package ltd.matrixstudios.alchemist.service.expirable

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import java.util.*

object RankGrantService : ExpiringService<RankGrant>() {

    override fun getValues(): List<RankGrant> {
        return Alchemist.dataflow.createQuery(RankGrant::class.java).toList()
    }

    override fun save(element: RankGrant) {
        Alchemist.dataflow.save(element.uuid.toString(), element, RankGrant::class.java)
    }

    override fun clearOutModels() {
        getValues().forEach {
            if (!it.expirable.isActive() && it.removedBy == null) {
                it.removedBy = UUID.fromString("00000000-0000-0000-0000-000000000000")
                it.removedReason = "Expired"

                save(it)
            }
        }
    }

}