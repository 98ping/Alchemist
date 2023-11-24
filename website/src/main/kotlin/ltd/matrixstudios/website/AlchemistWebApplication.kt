package ltd.matrixstudios.website

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class AlchemistWebApplication {

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			runApplication<AlchemistWebApplication>(*args)
		}
	}
}