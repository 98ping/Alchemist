package ltd.matrixstudios.alchemist.aikar.flags

import co.aikar.commands.BukkitCommandExecutionContext
import co.aikar.commands.contexts.ContextResolver

class FlagTypeResolver : ContextResolver<MakeshiftFlag, BukkitCommandExecutionContext>
{
    override fun getContext(c: BukkitCommandExecutionContext?): MakeshiftFlag?
    {
        val firstArg = c!!.popFirstArg() ?: return null

        return MakeshiftFlag(firstArg)
    }
}