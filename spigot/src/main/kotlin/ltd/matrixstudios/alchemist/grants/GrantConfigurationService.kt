package ltd.matrixstudios.alchemist.grants

import com.google.gson.reflect.TypeToken
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.grants.models.GrantDurationModel
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import java.lang.reflect.Type

/**
 * Class created on 8/3/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object GrantConfigurationService {

    var grantDurationModels: MutableList<GrantDurationModel> = mutableListOf()
    val grantDurationType: Type = object : TypeToken<MutableList<GrantDurationModel>>() {}.type

    fun loadAllModels() {
        RedisPacketManager.pool.resource.use {
            if (!it.exists("Alchemist:Grants:DurationModels")) {
                grantDurationModels = getDefaultGrantDurationModels()
                it.set("Alchemist:Grants:DurationModels", Alchemist.gson.toJson(grantDurationModels))
            } else {
                val items = it.get("Alchemist:Grants:DurationModels")
                val deserialize = Alchemist.gson.fromJson<MutableList<GrantDurationModel>>(items, grantDurationType)

                grantDurationModels = deserialize
            }
        }
    }

    fun getDefaultGrantDurationModels() : MutableList<GrantDurationModel> {
        return mutableListOf(
            GrantDurationModel("WOOL", 13, 10, "1h", "&21 Hour"),
            GrantDurationModel("WOOL", 5, 11, "1d", "&a1 Day"),
            GrantDurationModel("WOOL", 4, 12, "1w", "&e1 Week"),
            GrantDurationModel("WOOL", 1, 13, "1m", "&61 Month"),
            GrantDurationModel("WOOL", 14, 14, "1y", "&c1 Year"),
            GrantDurationModel("WOOL", 14, 15, "Permanent", "&4Permanent"),
            GrantDurationModel("WOOL", 8, 16, "custom", "&7Custom")
        )
    }
}