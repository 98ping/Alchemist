package ltd.matrixstudios.alchemist.commands.coins.category

data class CoinShopCategory(
    val id: String,
    var displayName: String,
    var desc: List<String>,
    var displayItem: String,
    var activeOn: List<String>,
    var data: Short,
    var menuSlot: Int,
    var parentCategory: String?
)