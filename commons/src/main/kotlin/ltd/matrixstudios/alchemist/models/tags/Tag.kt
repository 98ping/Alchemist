package ltd.matrixstudios.alchemist.models.tags



data class Tag(
    var id: String,
    var menuName: String,
    var purchasable: Boolean,
    var prefix: String,
    var category: String? = null,
    var displayItem: String? = null
) {
}