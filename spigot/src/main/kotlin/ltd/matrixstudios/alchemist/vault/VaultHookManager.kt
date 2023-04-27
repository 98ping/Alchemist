package ltd.matrixstudios.alchemist.vault

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import net.milkbowl.vault.chat.Chat
import net.milkbowl.vault.permission.Permission
import org.bukkit.plugin.RegisteredServiceProvider

object VaultHookManager
{
    private var using = false
    var perms: Permission? = null
    var chat: Chat? = null

    fun loadVault()
    {
        if (AlchemistSpigotPlugin.instance.server.pluginManager.getPlugin("Vault") != null)
        {
            using = true
            setupPermissions()
            setupChat()
        }
    }

    private fun setupChat(): Boolean {
        val rsp: RegisteredServiceProvider<Chat> = AlchemistSpigotPlugin.instance.server.servicesManager.getRegistration(
            Chat::class.java
        )
        chat = rsp.provider
        return chat != null
    }

    private fun setupPermissions(): Boolean {
        val rsp: RegisteredServiceProvider<Permission> = AlchemistSpigotPlugin.instance.server.servicesManager.getRegistration(
            Permission::class.java
        )
        perms = rsp.provider
        return perms != null
    }
}