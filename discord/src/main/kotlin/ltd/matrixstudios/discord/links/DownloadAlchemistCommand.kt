package ltd.matrixstudios.discord.links

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter


object DownloadAlchemistCommand : ListenerAdapter()
{
    @Override
    fun onDownloadSlashCommand(event: SlashCommandInteractionEvent)
    {
        val commandName = event.name

        if (commandName.equals("download", ignoreCase = true))
        {
            val embed = EmbedBuilder()
            embed.setTitle("Download Links", null)
            embed.setDescription("Alchemist can be found at places such as **BuiltByBit**, and **GitHub**!\\n\\nGithub: https://github.com/98ping/Alchemist\\nBuiltByBit: https://builtbybit.com/resources/alchemist-rank-core-all-version-free.24657/")
            embed.setColor(0xea00ff)

            event.replyEmbeds(
                embed.build()
            ).setEphemeral(true).queue()
        }
    }
}