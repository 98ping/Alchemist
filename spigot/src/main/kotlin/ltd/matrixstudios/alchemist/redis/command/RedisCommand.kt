package ltd.matrixstudios.alchemist.redis.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.LocalPacketPubSub
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.command.CommandSender

object RedisCommand : BaseCommand()
{

    @CommandAlias("redis")
    @CommandPermission("alchemist.owner")
    fun redis(player: CommandSender)
    {
        player.sendMessage(Chat.format("&7&m--------------------------"))
        player.sendMessage(Chat.format("&4&lRedis &7❘ &fInformation"))
        player.sendMessage(Chat.format("&7&m--------------------------"))
        val isConnected = !RedisPacketManager.pool.isClosed
        player.sendMessage(Chat.format("&cIs Connected&7: &f${if (isConnected) "&aYes :3" else "&cNo :("}"))
        player.sendMessage(Chat.format("&cListening On&7: &fAlchemist||Packets||%packet%"))
        val totalPackets = AsynchronousRedisSender.totalPacketCount
        val receivedPackets = LocalPacketPubSub.received
        player.sendMessage(Chat.format("&cTotal Packets Sent&7: &f${totalPackets}"))
        player.sendMessage(Chat.format("&cTotal Packets Received&7: &f${totalPackets}"))
        player.sendMessage(Chat.format("&cConnected for&7: &f${TimeUtil.formatDuration(
            System.currentTimeMillis().minus(AlchemistSpigotPlugin.instance.launchedAt)
        )}"))
        player.sendMessage(Chat.format("&cPort&7: &f${Alchemist.redisConnectionPort}"))
        player.sendMessage(Chat.format("&7&m--------------------------"))
    }
}