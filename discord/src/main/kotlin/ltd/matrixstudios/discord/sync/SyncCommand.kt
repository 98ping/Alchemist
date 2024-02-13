package ltd.matrixstudios.discord.sync

import ltd.matrixstudios.discord.configuration.ConfigurationService
import ltd.matrixstudios.discord.framework.DiscordCommand
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

@DiscordCommand(name = "sync", description = "Sync your in-game rank with your Discord rank!")
object SyncCommand : ListenerAdapter()
{
    @Override
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent)
    {
        val commandName = event.name

        if (commandName.equals("sync", ignoreCase = true))
        {
            val channel = event.channelId
            val syncChannel = ConfigurationService.configuration.getSyncChannelId()

            if (channel == syncChannel)
            {
                // should never null because we set it as required, but will check anyway
                val code = event.getOption("Sync Code")?.asString ?: return
            }
        }
    }
}