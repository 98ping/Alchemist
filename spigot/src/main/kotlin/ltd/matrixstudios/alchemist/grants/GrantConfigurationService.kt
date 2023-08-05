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

    var grantDurationModels: MutableMap<String, GrantDurationModel> = mutableMapOf()
    val grantDurationType: Type = object : TypeToken<MutableList<GrantDurationModel>>() {}.type

    fun loadAllModels() {
        RedisPacketManager.pool.resource.use {
            if (!it.exists("Alchemist:Grants:DurationModels")) {
                grantDurationModels = getDefaultGrantDurationModels()
                it.set("Alchemist:Grants:DurationModels", Alchemist.gson.toJson(grantDurationModels))
            } else {
                val items = it.get("Alchemist:Grants:DurationModels")
                val deserialize = Alchemist.gson.fromJson<MutableList<GrantDurationModel>>(items, grantDurationType)

                for (dur in deserialize) {
                    grantDurationModels[dur.id] = dur
                }
            }
        }
    }

    fun saveAll() {
        RedisPacketManager.pool.resource.use {
            it.set("Alchemist:Grants:DurationModels", Alchemist.gson.toJson(this.grantDurationModels.values))
        }
    }

    fun saveModel(model: GrantDurationModel) {
        grantDurationModels[model.id] = model
        RedisPacketManager.pool.resource.use {
            it.set("Alchemist:Grants:DurationModels", Alchemist.gson.toJson(this.grantDurationModels.values))
        }
    }

    fun getDefaultGrantDurationModels() : MutableMap<String, GrantDurationModel> {
        return mutableMapOf(
            "1h" to GrantDurationModel("1h","WOOL", 13, 10, "1h", "&21 Hour"),
            "1d" to GrantDurationModel("1d","WOOL", 5, 11, "1d", "&a1 Day"),
            "1w" to GrantDurationModel("1w", "WOOL", 4, 12, "1w", "&e1 Week"),
            "1m" to GrantDurationModel("1m", "WOOL", 1, 13, "1m", "&61 Month"),
            "1y" to GrantDurationModel("1y", "WOOL", 14, 14, "1y", "&c1 Year"),
            "permanent" to GrantDurationModel("permanent", "WOOL", 14, 15, "Permanent", "&4Permanent"),
            "custom" to GrantDurationModel("custom", "WOOL", 8, 16, "custom", "&7Custom")
        )
    }
}