package ltd.matrixstudios.alchemist.vault.permission

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.vault.chat.VaultChatExtension
import net.milkbowl.vault.chat.Chat
import net.milkbowl.vault.permission.Permission
import org.bukkit.plugin.ServicePriority

class VaultPermissionExtension : Permission() {

    private val alchemist: AlchemistSpigotPlugin = AlchemistSpigotPlugin.instance
    fun init()
    {
        this.plugin = alchemist

        plugin.server.servicesManager.register(
            Permission::class.java,
            this,
            this.plugin,
            ServicePriority.Highest
        )

        plugin.server.servicesManager.register(
            Chat::class.java,
            VaultChatExtension(this, this.plugin),
            this.plugin,
            ServicePriority.Highest
        )
    }


    override fun getName(): String {
        return "Alchemist"
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun hasSuperPermsCompat(): Boolean {
        return false
    }

    override fun playerHas(p0: String?, p1: String?, p2: String?): Boolean {
        val profile = ProfileGameService.byUsername(p1!!.toLowerCase()) ?: return false

        return profile.getPermissions().keys.contains(p2)
    }

    override fun playerAdd(p0: String?, p1: String?, p2: String?): Boolean {
        val profile = ProfileGameService.byUsername(p1!!.toLowerCase()) ?: return false
        profile.permissions.add(p2!!)
        ProfileGameService.save(profile)

        return true
    }

    override fun playerRemove(p0: String?, p1: String?, p2: String?): Boolean {
        val profile = ProfileGameService.byUsername(p1!!.toLowerCase()) ?: return false
        profile.permissions.remove(p2!!)
        ProfileGameService.save(profile)

        return true
    }

    override fun groupHas(p0: String?, p1: String?, p2: String?): Boolean {
        val rank = RankService.byId(p1!!.toLowerCase()) ?: return false

        return rank.permissions.contains(p2!!)
    }

    override fun groupAdd(p0: String?, p1: String?, p2: String?): Boolean {
        throw UnsupportedOperationException("Alchemist does not allow for non-core group auditing!")
    }

    override fun groupRemove(p0: String?, p1: String?, p2: String?): Boolean {
        throw UnsupportedOperationException("Alchemist does not allow for non-core group auditing!")
    }

    override fun playerInGroup(p0: String?, p1: String?, p2: String?): Boolean {
        val profile = ProfileGameService.byUsername(p1!!.toLowerCase()) ?: return false

        return RankGrantService.getFromCache(profile.uuid).firstOrNull { it.rank == p2!!} != null
    }

    override fun playerAddGroup(p0: String?, p1: String?, p2: String?): Boolean {
        throw UnsupportedOperationException("Alchemist does not allow for non-core group auditing!")
    }

    override fun playerRemoveGroup(p0: String?, p1: String?, p2: String?): Boolean {
        throw UnsupportedOperationException("Alchemist does not allow for non-core group auditing!")
    }

    override fun getPlayerGroups(p0: String?, p1: String?): Array<String> {
        val profile = ProfileGameService.byUsername(p1!!.toLowerCase()) ?: return arrayOf()

        return RankGrantService.getFromCache(profile.uuid).map { it.getGrantable()!!.displayName }.toTypedArray()
    }

    override fun getPrimaryGroup(p0: String?, p1: String?): String {
        val profile = ProfileGameService.byUsername(p1!!.toLowerCase()) ?: return "Unknown"

        return profile.getCurrentRank()!!.displayName
    }

    override fun getGroups(): Array<String> {
        return RankService.getRanksInOrder().map { it.displayName }.toTypedArray()
    }

    override fun hasGroupSupport(): Boolean {
        return true
    }
}