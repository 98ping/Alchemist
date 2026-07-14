package ltd.matrixstudios.alchemist.integration.clerk

import org.bukkit.Bukkit

object ClerkHook {

    private const val PLUGIN_NAME = "Clerk"
    private const val SERVICE_CLASS = "co.matrixstudios.clerk.FakePlayerService"

    fun isPresent(): Boolean {
        return Bukkit.getPluginManager().getPlugin(PLUGIN_NAME)?.isEnabled == true
    }

    fun fakeCount(): Int {
        return try {
            if (!isPresent()) return 0

            val service = resolveService() ?: return 0
            val fakes = service.javaClass.getMethod("fakePlayers").invoke(service) as? List<*> ?: return 0

            fakes.size
        } catch (ignored: Throwable) {
            0
        }
    }

    private fun resolveService(): Any? {
        val manager = Bukkit.getServicesManager()

        @Suppress("UNCHECKED_CAST")
        val serviceClass = manager.knownServices
            .firstOrNull { it.name == SERVICE_CLASS } as? Class<Any> ?: return null

        return manager.load(serviceClass)
    }
}
