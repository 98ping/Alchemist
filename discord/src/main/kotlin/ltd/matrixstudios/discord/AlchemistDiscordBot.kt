package ltd.matrixstudios.discord


import ltd.matrixstudios.discord.configuration.ConfigurationService
import ltd.matrixstudios.discord.links.DownloadAlchemistCommand
import ltd.matrixstudios.discord.sync.SyncCommand
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.utils.cache.CacheFlag

class AlchemistDiscordBot
{

    companion object
    {
        lateinit var instance: AlchemistDiscordBot
        lateinit var jda: JDA

        @JvmStatic
        fun main(args: Array<String>)
        {
            instance = AlchemistDiscordBot()

            instance.start(args[0])
        }
    }

    private val commands = listOf(
        DownloadAlchemistCommand,
        SyncCommand
    )

    fun start(token: String)
    {
        val builder = JDABuilder.createDefault(token)
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
        builder.setBulkDeleteSplittingEnabled(false)
        builder.setActivity(Activity.watching("Alchemist Error Logs"))
        builder.setAutoReconnect(true)
        jda = builder.build()

        ConfigurationService.load()

        jda.updateCommands().addCommands(
            Commands.slash("download-alchemist", "Find the links where you can download Alchemist."),
            Commands.slash("sync", "Sync your in-game rank with your Discord rank.")
                .addOption(
                    OptionType.STRING,
                    "Sync Code",
                    "The code that you receive in-game when you execute the sync command",
                    true
                )
        ).queue()

        commands.forEach {
            jda.addEventListener(it)
        }

        jda.awaitReady()
    }
}