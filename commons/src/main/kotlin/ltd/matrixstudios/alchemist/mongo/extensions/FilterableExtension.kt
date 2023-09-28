package ltd.matrixstudios.alchemist.mongo.extensions

import com.mongodb.client.model.Filters
import org.bson.conversions.Bson
import kotlin.reflect.KProperty

/**
 * Class created on 9/27/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
infix fun <V> KProperty<V?>.eq(value: V?): Bson = Filters.eq(this.name, value)