package ltd.matrixstudios.alchemist.commands.coins.item

/**
 * Class created on 7/4/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class CoinShopItem(
    val id: String,
    var displayName: String,
    var commands: MutableList<String>,
    var servers: MutableList<String>,
    var category: String,
    var displayMaterial: String,
    var requiredRank: String,
    var data: Short,
    var lore: MutableList<String>,
    var price: Double,
    var displayOrder: Int? = null,
    var discount: Double = 0.0 //simple amount so fixed price would be price - discount
)
{
    fun findOrder(): Int
    {
        if (displayOrder == null) return 0

        return displayOrder!!
    }
}