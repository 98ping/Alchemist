package ltd.matrixstudios.alchemist.punishments

import java.awt.Color

enum class PunishmentType(
    var id: String,
    var color: String,
    var added: String,
    var removed: String,
    var niceName: String,
    var hex: Int
) {

    BLACKLIST("blacklist", "&4", "blacklisted", "unblacklisted", "Blacklist", Color(136, 8, 8).rgb),
    BAN("ban", "&c", "banned", "unbanned", "Ban", Color(238, 75, 43).rgb),
    MUTE("mute", "&6", "muted", "unmuted", "Mute", Color(204, 85, 0).rgb),
    GHOST_MUTE("ghost_mute", "&a", "ghost muted", "un-ghost muted", "Ghost Mute", Color.GREEN.rgb),
    WARN("warn", "&e", "warned", "unwarned", "Warn", Color(255, 195, 0).rgb),
    KICK("kick", "&a", "kicked", "unkicked", "Kick", Color.GREEN.rgb)
}