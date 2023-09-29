package ltd.matrixstudios.alchemist.mongo.extensions

import com.mongodb.client.model.Filters
import ltd.matrixstudios.alchemist.Alchemist
import org.bson.Document
import org.bson.conversions.Bson
import kotlin.reflect.KProperty

/**
 * Class created on 9/27/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
infix fun <V> KProperty<V?>.eq(value: V?): Bson = Filters.eq(this.name, value)
infix fun <V> Document.deserialize(clazz: Class<V>) : V? = Alchemist.gson.fromJson(this.toJson(), clazz)