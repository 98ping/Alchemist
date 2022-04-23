package ltd.matrixstudios.alchemist.models.tags

data class Tag(
    var id: String,
    var menuName: String,
    var purchasable: Boolean,
    var prefix: String
) {
}