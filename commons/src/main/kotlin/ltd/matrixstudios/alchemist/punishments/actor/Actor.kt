package ltd.matrixstudios.alchemist.punishments.actor

import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor

abstract class Actor(
    var executor: Executor,
    var actorType: ActorType,
    var name: String
) {
}