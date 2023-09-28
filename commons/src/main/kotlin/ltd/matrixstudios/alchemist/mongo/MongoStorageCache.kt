package ltd.matrixstudios.alchemist.mongo

/**
 * Class created on 9/27/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object MongoStorageCache
{
    inline fun <K, reified V> create(collection: String) : MongoStorageController<K ,V> = MongoStorageController(collection, V::class.java)
}