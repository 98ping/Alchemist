package ltd.matrixstudios.alchemist.models.vouchers

import java.util.UUID

data class VoucherGrant(
    var uniqueId: UUID,
    var template: VoucherTemplate,
    var mustRedeemByTime: Boolean,
    var redeemByDuration: Long,
    var completed: Boolean,
    var executedBy: UUID,
    var givenTo: UUID
)