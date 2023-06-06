package ltd.matrixstudios.alchemist.models.profile.auth

import ltd.matrixstudios.alchemist.util.TOTPUtil

data class AuthFactors(
    var setup2fa: Boolean = false,
    var authBypassed: Boolean = false,
    var lastAuth: Long? = null,
    var secret: String
) {


    fun isAuthValid(secret: String?, code: Int): Boolean {
        if (secret == null) {
            return false
        }
        return if (secret == "") {
            false
        } else try {
            TOTPUtil.validateCurrentNumber(secret, code, 250)
        } catch (ignored: Exception) {
            false
        }
    }
}