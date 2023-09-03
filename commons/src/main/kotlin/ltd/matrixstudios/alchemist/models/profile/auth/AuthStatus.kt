package ltd.matrixstudios.alchemist.models.profile.auth

data class AuthStatus(
    var lastAuthenticated: Long = 0L,
    var hasSetup2fa: Boolean = false,
    var authBypassed: Boolean = false,
    var secret: String,
    var allowedIps: MutableList<String> = mutableListOf()
)