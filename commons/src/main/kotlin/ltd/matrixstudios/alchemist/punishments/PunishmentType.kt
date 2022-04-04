package ltd.matrixstudios.alchemist.punishments

enum class PunishmentType(
    var id: String,
    var color: String,
    var added: String,
    var removed: String,
) {

    BLACKLIST("blacklist", "&4", "blacklisted", "unblacklisted"),
    BAN("ban", "&c", "banned", "unbanned"),
    MUTE("mute", "&6", "muted", "unmuted"),
    WARN("warn", "&e", "warned", "unwarned")
}