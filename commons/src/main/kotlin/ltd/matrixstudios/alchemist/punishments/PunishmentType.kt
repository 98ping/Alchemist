package ltd.matrixstudios.alchemist.punishments

enum class PunishmentType(
    var id: String,
    var color: String,
    var added: String,
    var removed: String,
    var niceName: String
) {

    BLACKLIST("blacklist", "&4", "blacklisted", "unblacklisted", "Blacklist"),
    BAN("ban", "&c", "banned", "unbanned", "Ban"),
    MUTE("mute", "&6", "muted", "unmuted", "Mute"),
    WARN("warn", "&e", "warned", "unwarned", "Warn")
}