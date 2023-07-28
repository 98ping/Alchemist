package ltd.matrixstudios.alchemist.punishments.actor

import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor

data class DefaultActor(
    var executor: Executor,
    var actorType: ActorType
)