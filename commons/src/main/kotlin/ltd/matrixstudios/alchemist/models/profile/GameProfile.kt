package ltd.matrixstudios.alchemist.models.profile

import com.google.gson.JsonObject
import ltd.matrixstudios.mongo.annotation.Collection
import java.util.*

@Collection("gameprofiles")
data class GameProfile(
    var uuid: UUID,
    var username: String,
    var metadata: JsonObject,
    var usedIps: List<String>,
) {
}