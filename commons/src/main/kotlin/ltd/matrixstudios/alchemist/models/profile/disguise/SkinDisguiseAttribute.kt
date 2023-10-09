package ltd.matrixstudios.alchemist.models.profile.disguise

data class SkinDisguiseAttribute(
    var customName: String,
    var disguisedAt: Long = System.currentTimeMillis(),
    var skinName: String
)