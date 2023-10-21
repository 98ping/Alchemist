package ltd.matrixstudios.alchemist.aikar.context

import co.aikar.commands.BukkitCommandExecutionContext
import co.aikar.commands.contexts.ContextResolver
import ltd.matrixstudios.alchemist.profiles.AsyncGameProfile
import java.util.*

/**
 * Class created on 7/26/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class AsyncProfileResolver : ContextResolver<AsyncGameProfile, BukkitCommandExecutionContext>
{

    override fun getContext(c: BukkitCommandExecutionContext): AsyncGameProfile?
    {
        val firstArg = c.popFirstArg() ?: return null

        return AsyncGameProfile.name(firstArg.lowercase(Locale.getDefault()))
    }
}