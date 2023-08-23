package ltd.matrixstudios.alchemist.commands.coins.transactions

import ltd.matrixstudios.alchemist.commands.coins.item.CoinShopItem
import java.util.UUID

/**
 * Class created on 8/23/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
data class Transaction(
    var id: UUID,
    var user: UUID,
    var items: MutableList<CoinShopItem>,
    var on: String,
    var coinsSpent: Double
)