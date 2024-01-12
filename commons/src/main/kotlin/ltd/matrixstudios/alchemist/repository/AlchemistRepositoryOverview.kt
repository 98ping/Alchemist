package ltd.matrixstudios.alchemist.repository

/**
 * Class created on 1/11/2024

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
data class AlchemistRepositoryOverview(
    val name: String,
    val files: List<AlchemistJarFile>,
    val type: String
) {

    data class AlchemistJarFile(
        val name: String,
        val type: String,
        val contentType: String? = null,
        val contentLength: Int? = null
    )
}