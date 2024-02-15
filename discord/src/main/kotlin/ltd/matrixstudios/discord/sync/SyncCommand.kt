package ltd.matrixstudios.discord.sync

import ltd.matrixstudios.discord.configuration.ConfigurationService
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

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
                val code = event.getOption("Sync Code")?.asString
                    ?: return
                val username = event.getOption("Username")?.asString
                    ?: return

                SyncService.getSyncCodeForUser(username).thenAccept {
                    if (it == null)
                    {
                        event.reply("This account does not have a sync code setup!").queue()
                    } else
                    {
                        val profileCode = it

                        if (profileCode == code)
                        {

                        }
                    }
                }
            } else
            {
                event.reply("You must be in the appropriate channel to sync your account!").setEphemeral(true).queue()
            }
        }
    }
}