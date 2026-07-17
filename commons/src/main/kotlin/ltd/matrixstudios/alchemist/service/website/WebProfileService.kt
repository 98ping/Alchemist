package ltd.matrixstudios.alchemist.service.website

import ltd.matrixstudios.alchemist.mongo.MongoStore
import ltd.matrixstudios.alchemist.mongo.MongoManager
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.website.AlchemistUser
import ltd.matrixstudios.alchemist.service.GeneralizedService
import org.bson.Document
import java.util.*
object WebProfileService : GeneralizedService
{
    var handler = MongoStore<UUID, AlchemistUser>("alchemistuser", AlchemistUser::class.java)
    val collection = MongoManager.getCollection("alchemistuser")

    fun byId(uuid: UUID): AlchemistUser? {
        val document = collection.find(Document("minecraft_uuid", uuid.toString())).first()
            ?: return null

        return Alchemist.gson.fromJson(document.toJson(), AlchemistUser::class.java)
    }

    fun save(user: AlchemistUser) {
        handler.storeAsync(user.random_id, user)
    }
}
