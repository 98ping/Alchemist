package ltd.matrixstudios.alchemist.punishments.actor

import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor

class DefaultActor(
    executor: Executor,
    name: String,
    actorType: ActorType
) : Actor(executor, actorType, name) {
}