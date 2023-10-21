package ltd.matrixstudios.alchemist.convert.luckperms

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.convert.IRankConverter
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.Chat
import net.luckperms.api.LuckPerms
import net.luckperms.api.model.group.Group
import net.luckperms.api.query.QueryOptions
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

/**
 * Class created on 6/13/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object LuckpermsRankConverter : IRankConverter
{
    override fun convert(player: Player)
    {
        val provider = Bukkit.getServicesManager().getRegistration(LuckPerms::class.java).provider

        if (provider != null)
        {

            val weightedGroups =
                provider.groupManager.loadedGroups.sortedByDescending { group -> group.weight.orElse(0) }

            player.sendMessage(Chat.format("&aDiscovered ${weightedGroups.size} groups"))

            val rankToGroup = hashMapOf<Rank, Group>()

            // create ranks from groups
            for ((orderIndex, group) in weightedGroups.withIndex())
            {
                val rank = Rank(
                    group.name,
                    group.name,
                    group.displayName ?: group.name,
                    orderIndex, ArrayList<String>().also { arrayList -> arrayList.addAll(group.nodes.map { it.key }) },
                    arrayListOf(),
                    group.cachedData.getMetaData(QueryOptions.defaultContextualOptions()).prefix ?: "",
                    getLastColors(group.cachedData.getMetaData(QueryOptions.defaultContextualOptions()).prefix ?: "&f")
                )

                rankToGroup[rank] = group
            }

            // setup rank inheritance
            for ((rank, group) in rankToGroup)
            {
                val inherits = arrayListOf<String>()

                for ((otherRank, otherGroup) in rankToGroup)
                {
                    if (rank != otherRank)
                    {
                        if (group.getInheritedGroups(QueryOptions.defaultContextualOptions()).contains(otherGroup))
                        {
                            inherits.add(otherRank.id)
                        }
                    }
                }

                rank.parents = inherits
            }

            // save newly created ranks
            for (rank in rankToGroup.keys)
            {
                RankService.save(rank)
            }


            player.sendMessage(Chat.format("&aSaved ${rankToGroup.size} ranks"))

            // apply ranks to users from their groups
            provider.userManager.uniqueUsers.thenAcceptAsync { userSet ->
                userSet.forEach { uuid ->
                    provider.userManager.loadUser(uuid).thenAcceptAsync { user ->
                        val rank = RankService.byId(user.primaryGroup)

                        // check if a rank by an id of the user's primary group exists
                        if (rank != null)
                        {

                            object : BukkitRunnable()
                            {
                                override fun run()
                                {
                                    Bukkit.dispatchCommand(
                                        Bukkit.getConsoleSender(),
                                        "nmgrant ${user.uniqueId} ${user.primaryGroup} perm Rank Conversion (LuckPerms)"
                                    )
                                }
                            }.runTask(AlchemistSpigotPlugin.instance)
                        }
                    }
                }
            }

            player.sendMessage(Chat.format("&e&lCAUTION! &cYou may need to set a default rank because Alchemist cannot directly identify"))
            player.sendMessage(Chat.format("&cwhich rank should be default. Do this by executing /rank module <rank> default <boolean>"))
        }
    }

    fun getLastColors(input: String): String
    {
        var result = ""
        val length = input.length
        for (index in length - 1 downTo -1 + 1)
        {
            val section = input[index]
            if (section.code == 167 && index < length - 1)
            {
                val c = input[index + 1]
                val color = ChatColor.getByChar(c)
                if (color != null)
                {
                    result = color.toString() + result
                    if (color.isColor || color == ChatColor.RESET)
                    {
                        break
                    }
                }
            }
        }
        return result
    }

}