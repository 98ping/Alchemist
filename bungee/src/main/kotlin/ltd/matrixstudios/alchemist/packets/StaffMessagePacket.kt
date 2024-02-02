package ltd.matrixstudios.alchemist.packets

import ltd.matrixstudios.alchemist.AlchemistBungee
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.chat.BaseComponentSerializer

class StaffMessagePacket(val message: String) : RedisPacket("staff-message-bungee") {

    override fun action() {
        AlchemistBungee.instance.proxy.players.filter {
            ProfileGameService.byId(it.uniqueId)?.getHighestGlobalRank()!!.staff
        }.forEach {
            it.sendMessage(TextComponent(ChatColor.translateAlternateColorCodes('&', message)))
        }
    }
}