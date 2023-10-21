package ltd.matrixstudios.alchemist.commands.coins.listener

import ltd.matrixstudios.alchemist.commands.coins.CoinShopManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

/**
 * Class created on 8/23/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class CoinShopLoadTransactionsListener : Listener
{

    @EventHandler
    fun loadTransaction(event: PlayerJoinEvent)
    {
        CoinShopManager.lookupTransactions(event.player.uniqueId).thenAccept {
            CoinShopManager.transactionMap[event.player.uniqueId] = it
        }
    }
}