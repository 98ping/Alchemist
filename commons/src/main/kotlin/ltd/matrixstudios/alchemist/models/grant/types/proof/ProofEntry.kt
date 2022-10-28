package ltd.matrixstudios.alchemist.models.grant.types.proof

import ltd.matrixstudios.alchemist.models.profile.GameProfile

data class ProofEntry(
    val link: String,
    val type: ProofType,
    val addedAt: Long,
    val whoAdded: GameProfile,
    val shouldBeConfidential: Boolean
) {

    enum class ProofType(
        val displayName: String,
    )
    {
        VIDEO("&6Videos"),
        IMAGES("&6Images"),
        STAFF_SUPERVISION("&6Staff Allowance")
    }
}