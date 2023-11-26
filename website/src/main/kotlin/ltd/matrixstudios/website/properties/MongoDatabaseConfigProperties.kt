package ltd.matrixstudios.website.properties

import lombok.Getter
import lombok.Setter
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.data.mongodb")
object MongoDatabaseConfigProperties
{
    var uri: String? = null
    var database: String? = null
}

