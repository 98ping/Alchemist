package ltd.matrixstudios.alchemist.profiles

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import org.bukkit.command.CommandSender
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
) {

    fun use(
        sender: CommandSender,
        action: (GameProfile) -> Unit
    ): CompletableFuture<List<GameProfile>> {
        val future = future.whenComplete { t, v ->
            if (t.isEmpty())
            {
                sender.sendMessage(Chat.format("&cA profile with the name &e$name &cwas not found"))
                return@whenComplete
            }

            if (t.size > 1)
            {
                sender.sendMessage(Chat.format("&cThere were multiple results to your request!"))
                for (name in t) {
                    val c = Component.text(Chat.format("&7- &e${name.username}"))
                        .hoverEvent(HoverEvent.showText(Component.text(Chat.format("&eClick to copy!"))))
                        .clickEvent(ClickEvent.suggestCommand(name.uuid.toString()))

                    AlchemistSpigotPlugin.instance.audience.sender(sender).sendMessage(c.asComponent())
                }

                sender.sendMessage(" ")
                sender.sendMessage(Chat.format("&7&oClick any of them to copy their UUID your clipboard"))

                return@whenComplete
            }

            action.invoke(t.first())

        }

        return future


    }

    companion object {
        fun name(
            name: String
        ): AsyncGameProfile {
            return AsyncGameProfile(
                name,
                ProfileGameService.byUsernameWithList(name)
            )
        }
    }
}