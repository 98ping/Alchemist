package ltd.matrixstudios.alchemist.aikar.context

import co.aikar.commands.BukkitCommandExecutionContext
import co.aikar.commands.InvalidCommandArgument
import co.aikar.commands.contexts.ContextResolver
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import java.util.UUID

/**
 * Class created on 6/13/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class UUIDContextResolver : ContextResolver<UUID, BukkitCommandExecutionContext> {

    override fun getContext(c: BukkitCommandExecutionContext?): UUID? {
        val firstArg = c!!.popFirstArg() ?: return null

        val uuid = UUID.fromString(firstArg)

        if (uuid != null) return uuid

        throw InvalidCommandArgument("Invalid uuid!")
    }
}