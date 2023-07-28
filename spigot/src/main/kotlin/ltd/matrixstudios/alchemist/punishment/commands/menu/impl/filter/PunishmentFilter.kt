package ltd.matrixstudios.alchemist.punishment.commands.menu.impl.filter

import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor
import java.util.*

enum class PunishmentFilter(
    val displayName: String,
    val lambda: (Collection<Punishment>) -> List<Punishment>
) {
    ALL("Every Punishment", { punishments ->  punishments.toList() }),
    ACTIVE("Active", { punishments -> punishments.filter { it.expirable.isActive() } }),
    REMOVED("Removed", { punishments -> punishments.filter { it.removedBy != null && it.removedBy != UUID.fromString("00000000-0000-0000-0000-000000000000") } }),
    EXPIRED("Expired", { punishments -> punishments.filter { it.removedBy != null && it.removedBy == UUID.fromString("00000000-0000-0000-0000-000000000000") } }),
    TEMPORARY("Temporary", { punishments ->  punishments.filter { it.expirable.duration != kotlin.Long.Companion.MAX_VALUE }}),
    GAME_GRANTED("Executed In-Game", { punishments -> punishments.filter { it.actor.executor == Executor.PLAYER } }),
    CONSOLE_GRANTED("Executed In Console", { punishments -> punishments.filter { it.actor.executor == Executor.CONSOLE } }),
    GAME_ACTOR("Game-Based Actor", { punishments -> punishments.filter { it.actor.actorType == ActorType.GAME } })
}