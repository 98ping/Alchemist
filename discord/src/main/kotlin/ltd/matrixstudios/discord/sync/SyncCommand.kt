package ltd.matrixstudios.discord.sync

import ltd.matrixstudios.discord.configuration.ConfigurationService
import net.dv8tion.jda.api.EmbedBuilder
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
                val code = event.getOption("sync-code")?.asString
                    ?: return
                val username = event.getOption("username")?.asString
                    ?: return

                SyncService.getSyncCodeForUser(username).thenAccept {
                    if (it == null)
                    {
                        event.reply("This account does not have a sync code setup!").setEphemeral(true).queue()
                    } else
                    {
                        val profileCode = it

                        if (profileCode == code)
                        {
                            val embed = EmbedBuilder()

                            embed.setColor(0x09ff00)
                            embed.setFooter("Not your username? Type /desync to restart this process!")
                            embed.setDescription("Your account has been linked to the Discord server!\n\n**Code:** ${code}\n**Username**: $username")
                            embed.setTitle("You have been synced!")

                            event.replyEmbeds(
                                embed.build()
                            ).setEphemeral(true).queue()

                            println("[Sync] $username has been synced to the discord server")
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