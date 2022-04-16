package ltd.matrixstudios.alchemist.commands.punishments.remove;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u000b\u001a\u00020\fH\u0007\u00a8\u0006\r"}, d2 = {"Lltd/matrixstudios/alchemist/commands/punishments/remove/UnmuteCommand;", "Lco/aikar/commands/BaseCommand;", "()V", "ban", "", "sender", "Lorg/bukkit/command/CommandSender;", "gameProfile", "Lltd/matrixstudios/alchemist/models/profile/GameProfile;", "silent", "", "reason", "", "spigot"})
public final class UnmuteCommand extends co.aikar.commands.BaseCommand {
    
    public UnmuteCommand() {
        super();
    }
    
    @co.aikar.commands.annotation.CommandPermission(value = "alchemist.punishments.unmute")
    @co.aikar.commands.annotation.CommandAlias(value = "unmute|unm")
    public final void ban(@org.jetbrains.annotations.NotNull()
    org.bukkit.command.CommandSender sender, @org.jetbrains.annotations.NotNull()
    @co.aikar.commands.annotation.Name(value = "target")
    ltd.matrixstudios.alchemist.models.profile.GameProfile gameProfile, @co.aikar.commands.annotation.Name(value = "-s")
    @co.aikar.commands.annotation.Optional()
    boolean silent, @org.jetbrains.annotations.NotNull()
    @co.aikar.commands.annotation.Name(value = "reason")
    java.lang.String reason) {
    }
}