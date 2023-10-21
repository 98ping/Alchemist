package ltd.matrixstudios.alchemist.profiles

import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.profiles.connection.postlog.BukkitPostLoginConnection
import ltd.matrixstudios.alchemist.profiles.connection.prelog.BukkitPreLoginConnection
import ltd.matrixstudios.alchemist.profiles.permissions.packet.PermissionUpdatePacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.cache.mutate.UpdateGrantCacheRequest
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.SHA
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Class created on 5/27/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object BukkitProfileAdaptation
{

    val backingCacheIpStore = mutableMapOf<UUID, String?>()

    fun loadAllEvents()
    {

        for (task in BukkitPreLoginConnection.getAllTasks())
        {
            if (!task.shouldBeLazy())
            {
                BukkitPreLoginConnection.registerNewCallback {
                    task.run(it)
                }
            } else BukkitPreLoginConnection.registerNewLazyCallback { task.run(it) }
        }

        for (task in BukkitPostLoginConnection.getAllTasks())
        {
            BukkitPostLoginConnection.registerNewCallback {
                task.run(it)
            }
        }
    }

    fun initializeGrant(rankGrant: RankGrant, uuid: UUID)
    {
        RankGrantService.save(rankGrant).whenComplete { g, e ->
            val profile = ProfileGameService.byId(uuid) ?: return@whenComplete

            AsynchronousRedisSender.send(PermissionUpdatePacket(profile.uuid))
            AsynchronousRedisSender.send(UpdateGrantCacheRequest(profile.uuid))
        }
    }

    fun playerNeedsAuthenticating(
        profile: GameProfile, player: Player
    ): Boolean
    {
        val rank = profile.getCurrentRank()

        if (rank.staff)
        {
            val auth = profile.getAuthStatus()

            //player isnt bypassed and player doesnt have 2fa
            if (!auth.authBypassed && !auth.hasSetup2fa)
            {
                return true
            }

            if (auth.authBypassed) return false

            //player has 2fa but it's been 3 days since last verification
            if (auth.hasSetup2fa && System.currentTimeMillis()
                    .minus(auth.lastAuthenticated) >= TimeUnit.DAYS.toMillis(3L)
            )
            {
                return true
            }

            val hexIp = if (backingCacheIpStore.containsKey(player.uniqueId))
            {
                backingCacheIpStore[player.uniqueId]
            } else
            {
                val item = SHA.toHexString(player.address.hostString)
                backingCacheIpStore[player.uniqueId] = item

                item
            }

            //player has 2fa but ip's dont match
            if (auth.hasSetup2fa && !auth.allowedIps.contains(hexIp))
            {
                return true
            }
        }

        return false
    }
}