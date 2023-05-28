package ltd.matrixstudios.alchemist.models.connection

/**
 * Class created on 5/27/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
abstract class ConnectionMethod<T> {


    val allCallbacks: MutableList<(T) -> Unit> = mutableListOf()
    val allLazyCallbacks: MutableList<(T) -> Unit> = mutableListOf()

    fun registerNewCallback(call: (T) -> Unit) = allCallbacks.add(call)

    fun registerNewLazyCallback(call: (T) -> Unit) = allLazyCallbacks.add(call)

}