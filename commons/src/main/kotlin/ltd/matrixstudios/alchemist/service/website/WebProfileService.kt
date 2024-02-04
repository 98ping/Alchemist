package ltd.matrixstudios.alchemist.service.website

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.website.AlchemistUser
import ltd.matrixstudios.alchemist.service.GeneralizedService
import org.bson.Document
import java.util.*
object WebProfileService : GeneralizedService
{
    var handler = Alchemist.dataHandler.createStoreType<UUID, AlchemistUser>(Alchemist.getDataStoreMethod())
    val collection = Alchemist.MongoConnectionPool.getCollection("alchemistuser")

    fun byId(uuid: UUID): AlchemistUser? {
        val document = collection.find(Document("minecraft_uuid", uuid.toString())).first()
            ?: return null

        return Alchemist.gson.fromJson(document.toJson(), AlchemistUser::class.java)
    }

    fun save(user: AlchemistUser) {
        handler.storeAsync(user.random_id, user)
    }
}
