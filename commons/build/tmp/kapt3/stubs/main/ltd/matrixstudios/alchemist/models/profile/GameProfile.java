package ltd.matrixstudios.alchemist.models.profile;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u001e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u007f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0016\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u00050\nj\b\u0012\u0004\u0012\u00020\u0005`\u000b\u0012\u0016\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u00030\nj\b\u0012\u0004\u0012\u00020\u0003`\u000b\u0012\u0016\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u00030\nj\b\u0012\u0004\u0012\u00020\u0003`\u000b\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\u0002\u0010\u0011J\u000e\u0010.\u001a\u00020/2\u0006\u00100\u001a\u000201J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\u0005H\u00c6\u0003J\t\u00104\u001a\u00020\u0005H\u00c6\u0003J\t\u00105\u001a\u00020\bH\u00c6\u0003J\u0019\u00106\u001a\u0012\u0012\u0004\u0012\u00020\u00050\nj\b\u0012\u0004\u0012\u00020\u0005`\u000bH\u00c6\u0003J\u0019\u00107\u001a\u0012\u0012\u0004\u0012\u00020\u00030\nj\b\u0012\u0004\u0012\u00020\u0003`\u000bH\u00c6\u0003J\u0019\u00108\u001a\u0012\u0012\u0004\u0012\u00020\u00030\nj\b\u0012\u0004\u0012\u00020\u0003`\u000bH\u00c6\u0003J\u000b\u00109\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010:\u001a\u00020\u0010H\u00c6\u0003J\u0095\u0001\u0010;\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\u0018\b\u0002\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u00050\nj\b\u0012\u0004\u0012\u00020\u0005`\u000b2\u0018\b\u0002\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u00030\nj\b\u0012\u0004\u0012\u00020\u0003`\u000b2\u0018\b\u0002\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u00030\nj\b\u0012\u0004\u0012\u00020\u0003`\u000b2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u00c6\u0001J\u0013\u0010<\u001a\u00020/2\b\u0010=\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\b\u0010\u0012\u001a\u0004\u0018\u000101J\u0014\u0010>\u001a\b\u0012\u0004\u0012\u00020@0?2\u0006\u0010A\u001a\u00020BJ\b\u0010C\u001a\u0004\u0018\u00010DJ*\u0010E\u001a&\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0006\u0012\u0004\u0018\u00010/0Fj\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0006\u0012\u0004\u0018\u00010/`GJ\f\u0010H\u001a\b\u0012\u0004\u0012\u00020@0?J\u0006\u0010I\u001a\u00020/J\u000e\u0010J\u001a\u00020/2\u0006\u0010A\u001a\u00020BJ\t\u0010K\u001a\u00020LH\u00d6\u0001J\u0006\u0010M\u001a\u00020/J\u0014\u0010N\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00000P0OJ\t\u0010Q\u001a\u00020\u0005H\u00d6\u0001R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R*\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u00030\nj\b\u0012\u0004\u0012\u00020\u0003`\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R*\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u00030\nj\b\u0012\u0004\u0012\u00020\u0003`\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0017\"\u0004\b\u001b\u0010\u0019R\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0013\"\u0004\b!\u0010\u0015R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R*\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u00050\nj\b\u0012\u0004\u0012\u00020\u0005`\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0017\"\u0004\b\'\u0010\u0019R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0013\"\u0004\b)\u0010\u0015R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-\u00a8\u0006R"}, d2 = {"Lltd/matrixstudios/alchemist/models/profile/GameProfile;", "", "uuid", "Ljava/util/UUID;", "username", "", "lowercasedUsername", "metadata", "Lcom/google/gson/JsonObject;", "usedIps", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "friends", "friendInvites", "activePrefix", "lastSeenAt", "", "(Ljava/util/UUID;Ljava/lang/String;Ljava/lang/String;Lcom/google/gson/JsonObject;Ljava/util/ArrayList;Ljava/util/ArrayList;Ljava/util/ArrayList;Ljava/lang/String;J)V", "getActivePrefix", "()Ljava/lang/String;", "setActivePrefix", "(Ljava/lang/String;)V", "getFriendInvites", "()Ljava/util/ArrayList;", "setFriendInvites", "(Ljava/util/ArrayList;)V", "getFriends", "setFriends", "getLastSeenAt", "()J", "setLastSeenAt", "(J)V", "getLowercasedUsername", "setLowercasedUsername", "getMetadata", "()Lcom/google/gson/JsonObject;", "setMetadata", "(Lcom/google/gson/JsonObject;)V", "getUsedIps", "setUsedIps", "getUsername", "setUsername", "getUuid", "()Ljava/util/UUID;", "setUuid", "(Ljava/util/UUID;)V", "canUse", "", "tag", "Lltd/matrixstudios/alchemist/models/tags/Tag;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "getActivePunishments", "", "Lltd/matrixstudios/alchemist/models/grant/types/Punishment;", "type", "Lltd/matrixstudios/alchemist/punishments/PunishmentType;", "getCurrentRank", "Lltd/matrixstudios/alchemist/models/ranks/Rank;", "getPermissions", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "getPunishments", "hasActivePrefix", "hasActivePunishment", "hashCode", "", "isOnline", "supplyFriendsAsProfiles", "Ljava/util/concurrent/CompletableFuture;", "", "toString", "commons"})
public final class GameProfile {
    @org.jetbrains.annotations.NotNull()
    private java.util.UUID uuid;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String username;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String lowercasedUsername;
    @org.jetbrains.annotations.NotNull()
    private com.google.gson.JsonObject metadata;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<java.lang.String> usedIps;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<java.util.UUID> friends;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<java.util.UUID> friendInvites;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String activePrefix;
    private long lastSeenAt;
    
    @org.jetbrains.annotations.NotNull()
    public final ltd.matrixstudios.alchemist.models.profile.GameProfile copy(@org.jetbrains.annotations.NotNull()
    java.util.UUID uuid, @org.jetbrains.annotations.NotNull()
    java.lang.String username, @org.jetbrains.annotations.NotNull()
    java.lang.String lowercasedUsername, @org.jetbrains.annotations.NotNull()
    com.google.gson.JsonObject metadata, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<java.lang.String> usedIps, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<java.util.UUID> friends, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<java.util.UUID> friendInvites, @org.jetbrains.annotations.Nullable()
    java.lang.String activePrefix, long lastSeenAt) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public GameProfile(@org.jetbrains.annotations.NotNull()
    java.util.UUID uuid, @org.jetbrains.annotations.NotNull()
    java.lang.String username, @org.jetbrains.annotations.NotNull()
    java.lang.String lowercasedUsername, @org.jetbrains.annotations.NotNull()
    com.google.gson.JsonObject metadata, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<java.lang.String> usedIps, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<java.util.UUID> friends, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<java.util.UUID> friendInvites, @org.jetbrains.annotations.Nullable()
    java.lang.String activePrefix, long lastSeenAt) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.UUID component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.UUID getUuid() {
        return null;
    }
    
    public final void setUuid(@org.jetbrains.annotations.NotNull()
    java.util.UUID p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUsername() {
        return null;
    }
    
    public final void setUsername(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLowercasedUsername() {
        return null;
    }
    
    public final void setLowercasedUsername(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.gson.JsonObject component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.gson.JsonObject getMetadata() {
        return null;
    }
    
    public final void setMetadata(@org.jetbrains.annotations.NotNull()
    com.google.gson.JsonObject p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<java.lang.String> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<java.lang.String> getUsedIps() {
        return null;
    }
    
    public final void setUsedIps(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<java.util.UUID> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<java.util.UUID> getFriends() {
        return null;
    }
    
    public final void setFriends(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<java.util.UUID> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<java.util.UUID> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<java.util.UUID> getFriendInvites() {
        return null;
    }
    
    public final void setFriendInvites(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<java.util.UUID> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getActivePrefix() {
        return null;
    }
    
    public final void setActivePrefix(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    public final long component9() {
        return 0L;
    }
    
    public final long getLastSeenAt() {
        return 0L;
    }
    
    public final void setLastSeenAt(long p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Collection<ltd.matrixstudios.alchemist.models.grant.types.Punishment> getPunishments() {
        return null;
    }
    
    public final boolean hasActivePrefix() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final ltd.matrixstudios.alchemist.models.tags.Tag getActivePrefix() {
        return null;
    }
    
    public final boolean canUse(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.tags.Tag tag) {
        return false;
    }
    
    public final boolean isOnline() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.CompletableFuture<java.util.List<ltd.matrixstudios.alchemist.models.profile.GameProfile>> supplyFriendsAsProfiles() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Collection<ltd.matrixstudios.alchemist.models.grant.types.Punishment> getActivePunishments(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.punishments.PunishmentType type) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.HashMap<java.lang.String, java.lang.Boolean> getPermissions() {
        return null;
    }
    
    public final boolean hasActivePunishment(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.punishments.PunishmentType type) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final ltd.matrixstudios.alchemist.models.ranks.Rank getCurrentRank() {
        return null;
    }
}