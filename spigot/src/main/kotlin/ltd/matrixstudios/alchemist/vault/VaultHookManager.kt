package ltd.matrixstudios.alchemist.vault

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import net.milkbowl.vault.chat.Chat
import net.milkbowl.vault.permission.Permission
import org.bukkit.plugin.RegisteredServiceProvider

object VaultHookManager
{
    private var using = false
    private var perms: Permission? = null
    var chat: Chat? = null

    fun loadVault()
    {
        if (AlchemistSpigotPlugin.instance.server.pluginManager.getPlugin("Vault") != null)
        {
            using = true
            ltd.matrixstudios.alchemist.util.Chat.sendConsoleMessage("&6[Vault] &fPermission Hook: " + setupPermissions())
            ltd.matrixstudios.alchemist.util.Chat.sendConsoleMessage("&6[Vault] &fChat Hook: " + setupChat())
        }
    }

    private fun setupChat(): Boolean {
        try {
            val rsp: RegisteredServiceProvider<net.milkbowl.vault.chat.Chat>? = AlchemistSpigotPlugin.instance.server.servicesManager.getRegistration(
                net.milkbowl.vault.chat.Chat::class.java
            )
            if (rsp != null) {
                chat = rsp.provider
            }
        } catch (ex: NoClassDefFoundError) {
            return false
        }
        return chat != null
    }

    private fun setupPermissions(): Boolean {
        try {
            val rsp: RegisteredServiceProvider<net.milkbowl.vault.permission.Permission>? = AlchemistSpigotPlugin.instance.server.servicesManager.getRegistration(
                net.milkbowl.vault.permission.Permission::class.java
            )
            if (rsp != null) {
                perms = rsp.provider
            }
        } catch (ex: NoClassDefFoundError) {
            return false
        }
        return perms != null
    }
}