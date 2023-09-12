package ltd.matrixstudios.alchemist.staff.requests.packets


import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.staff.requests.handlers.RequestHandler
import ltd.matrixstudios.alchemist.staff.requests.report.ReportModel
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit

class ReportPacket(val message: String, val reportModel: ReportModel) : RedisPacket("staff-message-report") {

    override fun action() {
        Bukkit.getOnlinePlayers().filter {
            it.hasPermission("alchemist.staff") && RequestHandler.hasReportsEnabled(it)
        }.forEach { it.sendMessage(Chat.format(message)) }

        RequestHandler.activeReports[reportModel.id] = reportModel
    }
}