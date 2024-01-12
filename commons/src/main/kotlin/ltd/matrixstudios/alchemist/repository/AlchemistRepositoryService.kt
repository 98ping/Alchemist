package ltd.matrixstudios.alchemist.repository

import ltd.matrixstudios.alchemist.Alchemist
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.maven.artifact.versioning.DefaultArtifactVersion
import java.math.BigDecimal
import kotlin.math.max


/**
 * Class created on 1/11/2024

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object AlchemistRepositoryService
{
    var client: OkHttpClient = OkHttpClient()


    fun checkLatestJarFile(version: DefaultArtifactVersion) : Pair<ResponseStatus, AlchemistRepositoryOverview.AlchemistJarFile?>
    {
        val request: Request = Request.Builder()
            .url("https://maven.matrixstudios.ltd/api/maven/details/public/ltd/matrixstudios/alchemist")
            .build()

        client.newCall(request).execute().use { response ->
            val body = response.body ?: return Pair(ResponseStatus.CouldNotLoad, null)
            val json = Alchemist.gson.fromJson(
                body.string(),
                AlchemistRepositoryOverview::class.java
            ) ?: return Pair(ResponseStatus.CouldNotLoad, null)

            val latest = json.files.maxByOrNull {
                DefaultArtifactVersion(it.name)
            } ?: return Pair(ResponseStatus.CouldNotLoad, null)

            if (DefaultArtifactVersion(latest.name) > version)
            {
                return Pair(ResponseStatus.NewerVersion, latest)
            } else
            {
                return Pair(ResponseStatus.Latest, null)
            }
        }
    }

    enum class ResponseStatus
    {
        NewerVersion, CouldNotLoad, Latest
    }
}