package ltd.matrixstudios.alchemist.commands.coins.cart.model

import ltd.matrixstudios.alchemist.commands.coins.item.CoinShopItem
import ltd.matrixstudios.alchemist.profiles.getProfile
import org.bukkit.entity.Player
import java.util.UUID

/**
 * Class created on 8/23/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
data class Cart(
    var player: UUID,
    var items: MutableList<CoinShopItem>
) {

    fun getCombinedPrice() : Double {
        var price = 0.0

        for (item in items)
        {
            val p = item.price
            val d = item.discount

            price += if (d != 0.0) {
                (p - d)
            } else {
                p
            }
        }

        return price
    }

    fun playerCanAfford(player: Player) : Boolean
    {
        val profile = player.getProfile() ?: return false

        val coins = profile.coins

        if (coins < getCombinedPrice())
        {
            return false
        }

        return true
    }
}