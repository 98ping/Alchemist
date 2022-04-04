package ltd.matrixstudios.alchemist.punishments.actor;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\b&\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0015"}, d2 = {"Lltd/matrixstudios/alchemist/punishments/actor/Actor;", "", "executor", "Lltd/matrixstudios/alchemist/punishments/actor/executor/Executor;", "actorType", "Lltd/matrixstudios/alchemist/punishments/actor/ActorType;", "name", "", "(Lltd/matrixstudios/alchemist/punishments/actor/executor/Executor;Lltd/matrixstudios/alchemist/punishments/actor/ActorType;Ljava/lang/String;)V", "getActorType", "()Lltd/matrixstudios/alchemist/punishments/actor/ActorType;", "setActorType", "(Lltd/matrixstudios/alchemist/punishments/actor/ActorType;)V", "getExecutor", "()Lltd/matrixstudios/alchemist/punishments/actor/executor/Executor;", "setExecutor", "(Lltd/matrixstudios/alchemist/punishments/actor/executor/Executor;)V", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "commons"})
public abstract class Actor {
    @org.jetbrains.annotations.NotNull()
    private ltd.matrixstudios.alchemist.punishments.actor.executor.Executor executor;
    @org.jetbrains.annotations.NotNull()
    private ltd.matrixstudios.alchemist.punishments.actor.ActorType actorType;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String name;
    
    public Actor(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.punishments.actor.executor.Executor executor, @org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.punishments.actor.ActorType actorType, @org.jetbrains.annotations.NotNull()
    java.lang.String name) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final ltd.matrixstudios.alchemist.punishments.actor.executor.Executor getExecutor() {
        return null;
    }
    
    public final void setExecutor(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.punishments.actor.executor.Executor p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final ltd.matrixstudios.alchemist.punishments.actor.ActorType getActorType() {
        return null;
    }
    
    public final void setActorType(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.punishments.actor.ActorType p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    public final void setName(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
}