package ltd.matrixstudios.alchemist.commands.coins.category

import ltd.matrixstudios.alchemist.commands.coins.CoinShopManager
import ltd.matrixstudios.alchemist.commands.coins.item.CoinShopItem

data class CoinShopCategory(
    val id: String,
    var displayName: String,
    var desc: List<String>,
    var displayItem: String,
    var activeOn: MutableList<String>,
    var data: Short,
    var menuSlot: Int,
    var parentCategory: String?
)
{

    fun getAllProducts(): MutableList<CoinShopItem>
    {
        val items = mutableListOf<CoinShopItem>()

        for (item in CoinShopManager.itemMap.values)
        {
            if (item.category.equals(id, ignoreCase = true))
            {
                items.add(item)
            }
        }

        return items
    }

    fun getCategoriesThatParentThisOne(): MutableList<CoinShopCategory>
    {
        val items = mutableListOf<CoinShopCategory>()
        for (category in CoinShopManager.categoryMap.values)
        {
            if (category.parentCategory != null)
            {
                val cat = category.parentCategory

                if (cat.equals(id, ignoreCase = true))
                {
                    items.add(category)
                }
            }
        }

        return items
    }
}