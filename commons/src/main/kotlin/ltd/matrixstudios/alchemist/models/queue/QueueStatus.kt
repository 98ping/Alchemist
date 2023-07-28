package ltd.matrixstudios.alchemist.models.queue

/**
 * Class created on 7/12/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
enum class QueueStatus(
    val displayName: String
) {

    CLOSED("Closed"), PAUSED("Paused"), OPEN("Open")
}