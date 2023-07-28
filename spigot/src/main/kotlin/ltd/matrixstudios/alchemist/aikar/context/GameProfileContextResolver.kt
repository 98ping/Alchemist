package ltd.matrixstudios.alchemist.aikar.context

import co.aikar.commands.BukkitCommandExecutionContext
import co.aikar.commands.InvalidCommandArgument
import co.aikar.commands.contexts.ContextResolver
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import java.util.*

class GameProfileContextResolver : ContextResolver<GameProfile, BukkitCommandExecutionContext> {

    override fun getContext(c: BukkitCommandExecutionContext): GameProfile? {
        val firstArg = c.popFirstArg() ?: return null

        //todo: async wrapper just mad lazy
        return ProfileGameService.byUsername(firstArg).join() ?: throw InvalidCommandArgument("No player by this name found")
    }
}