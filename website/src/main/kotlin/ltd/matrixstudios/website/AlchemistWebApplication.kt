package ltd.matrixstudios.website

import io.github.nosequel.data.connection.mongo.URIMongoConnectionPool
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.website.properties.MongoDatabaseConfigProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan("ltd.matrixstudios.website")
open class AlchemistWebApplication
{
	companion object {
		lateinit var instance: AlchemistWebApplication

		@JvmStatic
		fun main(args: Array<String>) {
			runApplication<AlchemistWebApplication>(*args)

			instance = AlchemistWebApplication()
			instance.onEnable()
		}
	}


	fun onEnable()
	{
		val connectionPool = URIMongoConnectionPool().apply {
			this.databaseName = MongoDatabaseConfigProperties.database
			this.uri = MongoDatabaseConfigProperties.uri
		}

		Alchemist.start(
			true,
			connectionPool,
			false,
			"localhost",
			6379,
			null,
			null
		)

		println("Started alchemist alongside the spring application")
	}
}