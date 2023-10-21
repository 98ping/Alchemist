package ltd.matrixstudios.alchemist.staff.requests.report

import java.util.*

data class ReportModel(
    val id: UUID,
    val reason: String,
    val issuer: UUID,
    val issuedTo: UUID,
    val server: String,
    val issuedAt: Long
)