package ltd.matrixstudios.alchemist.models.grant.types.proof

import ltd.matrixstudios.alchemist.models.profile.GameProfile
import java.util.UUID

data class ProofEntry(
    val link: String,
    val type: ProofType,
    val addedAt: Long,
    val whoAdded: UUID,
    val shouldBeConfidential: Boolean,
    var reviewer: UUID? = null,
    var reviewStatus: ReviewStatus? = null,
    var reviewedAt: Long? = null
) {

    enum class ProofType(
        val displayName: String,
    )
    {
        VIDEO("&6Videos"),
        IMAGES("&6Images"),
        STAFF_SUPERVISION("&6Classified")
    }

    enum class ReviewStatus(
        val displayName: String
    )
    {
        ACCEPTED("&aAccepted"), REPUNISHED("&6Change Punishment"), REJECTED("&cRejected")
    }
}