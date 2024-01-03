package ltd.matrixstudios.alchemist.models.profile.permissions

import ltd.matrixstudios.alchemist.Alchemist

data class ApplicablePermission(
    var global: Boolean,
    var scopes: MutableList<String>,
    var duration: Long,
    var node: String,
    var addedAt: Long = System.currentTimeMillis()
) {
    fun isActive(bungee: Boolean) : Boolean
    {
        if (duration != Long.MAX_VALUE && (System.currentTimeMillis() - (addedAt + duration) > 0))
        {
            return false
        }

        if (bungee) return true

        if (!global)
        {
             return scopes.any { it.equals(Alchemist.globalServer.id, ignoreCase = true) }
        }

        return true
    }
}