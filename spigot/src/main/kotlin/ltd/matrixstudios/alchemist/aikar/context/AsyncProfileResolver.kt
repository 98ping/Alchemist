package ltd.matrixstudios.alchemist.aikar.context

import co.aikar.commands.BukkitCommandExecutionContext
import co.aikar.commands.InvalidCommandArgument
import co.aikar.commands.contexts.ContextResolver
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.profiles.AsyncGameProfile

/**
 * Class created on 7/26/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class AsyncProfileResolver : ContextResolver<AsyncGameProfile, BukkitCommandExecutionContext> {

    override fun getContext(c: BukkitCommandExecutionContext): AsyncGameProfile? {
        val firstArg = c.popFirstArg() ?: return null

        return AsyncGameProfile.name(firstArg)
    }
}