package ltd.matrixstudios.discord


import ltd.matrixstudios.discord.links.DownloadAlchemistCommand
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.utils.cache.CacheFlag

class AlchemistDiscordBot
{

    companion object
    {
        lateinit var instance: AlchemistDiscordBot
        lateinit var jda: JDA

        @JvmStatic
        fun main(args: Array<String>) {
            instance = AlchemistDiscordBot()

            instance.start("token")
        }
    }

    fun start(token: String)
    {
        val builder = JDABuilder.createDefault(token)
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
        builder.setBulkDeleteSplittingEnabled(false)
        builder.setActivity(Activity.watching("Alchemist Error Logs"))
        jda = builder.build()

        jda.addEventListener(DownloadAlchemistCommand)
        jda.updateCommands().addCommands(
            Commands.slash("download-alchemist", "Find out where you can download Alchemist!"),
        ).queue()
    }
}