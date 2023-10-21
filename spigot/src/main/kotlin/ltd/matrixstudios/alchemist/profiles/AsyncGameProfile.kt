package ltd.matrixstudios.alchemist.profiles

import co.aikar.commands.ConditionFailedException
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Class created on 7/26/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 * @credits GrowlyX
 */
data class AsyncGameProfile(
    val name: String,
    val future: CompletableFuture<List<GameProfile>>
)
{

    fun use(
        sender: CommandSender,
        action: (GameProfile) -> Unit
    ): CompletableFuture<Void>
    {
        val f = future.thenAccept { t ->
            if (t.isEmpty())
            {
                sender.sendMessage(Chat.format("&cA profile with the name &e$name &cwas not found"))
                return@thenAccept
            }

            //todo: get dash to fix code

            if (t.size > 1)
            {
                sender.sendMessage(Chat.format("&cThere were multiple results to your request!"))
                for (name in t)
                {
                    val c = Component.text(Chat.format("&7- &e${name.username}"))
                        .hoverEvent(HoverEvent.showText(Component.text(Chat.format("&eClick to copy!"))))
                        .clickEvent(ClickEvent.suggestCommand(name.uuid.toString()))

                    AlchemistSpigotPlugin.instance.audience.sender(sender).sendMessage(c.asComponent())
                }

                sender.sendMessage(" ")
                sender.sendMessage(Chat.format("&7&oClick any of them to copy their UUID your clipboard"))

                return@thenAccept
            }

            action.invoke(t.first())
        }

        return f
    }

    companion object
    {
        fun name(
            name: String
        ): AsyncGameProfile
        {
            val uuid: UUID? = findUUID(name)

            return if (uuid != null)
            {
                val profile = ProfileGameService.byId(uuid)
                    ?: throw ConditionFailedException("${ChatColor.RED}The uuid ${ChatColor.YELLOW}$uuid ${ChatColor.RED}does not have an active profile")


                AsyncGameProfile(
                    profile.username,
                    CompletableFuture.completedFuture(Collections.singletonList(profile))
                )
            } else
            {
                AsyncGameProfile(
                    name,
                    ProfileGameService.byUsernameWithList(name)
                )
            }
        }

        fun findUUID(string: String): UUID?
        {
            val uuid: UUID?

            try
            {
                uuid = UUID.fromString(string)
            } catch (_: IllegalArgumentException)
            {
                return null
            }

            return uuid
        }
    }
}