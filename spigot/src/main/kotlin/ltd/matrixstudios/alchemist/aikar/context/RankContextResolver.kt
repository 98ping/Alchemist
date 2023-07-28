package ltd.matrixstudios.alchemist.aikar.context

import co.aikar.commands.BukkitCommandExecutionContext
import co.aikar.commands.InvalidCommandArgument
import co.aikar.commands.contexts.ContextResolver
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.service.ranks.RankService

class RankContextResolver : ContextResolver<Rank, BukkitCommandExecutionContext> {

    override fun getContext(c: BukkitCommandExecutionContext?): Rank? {
        val firstArg = c!!.popFirstArg() ?: return null

        val rank = RankService.byId(firstArg.toLowerCase())
            ?: throw InvalidCommandArgument("No rank by this name found")

        return rank
    }
}