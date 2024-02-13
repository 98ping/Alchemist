package ltd.matrixstudios.discord.configuration

import ltd.matrixstudios.amber.AmberConfigurationService
import ltd.matrixstudios.discord.AlchemistDiscordBot
import java.io.File


object ConfigurationService
{
    lateinit var configuration: DiscordBotConfiguration

    fun load()
    {
        AmberConfigurationService.make(
            File(AlchemistDiscordBot::class.java
                .getProtectionDomain()
                .codeSource
                .location
                .toURI()
            ).parentFile.path,
            "ltd.matrixstudios.discord",
            false
        )

        configuration = AmberConfigurationService.from(DiscordBotConfiguration::class.java, "bot-config.yml")
    }
}