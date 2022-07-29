package ltd.matrixstudios.alchemist.util.menu

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.CompletableFuture

abstract class Menu(
    private val size: Int,
    private val player: Player
) {

    abstract fun getButtons(player: Player) : MutableMap<Int, Button>
    abstract fun getTitle(player: Player) : String

    fun getAllButtons() : MutableMap<Int, Button> {
        return getButtons(player)
    }


    //dont need to use update menu because it is just placing items in menu. If it gets to it ill do it
    fun openMenu() {
        val inventory = Bukkit.createInventory(null, size, getTitle(player))

        MenuController.addToMenuMap(player, this)

        CompletableFuture.runAsync {
            for (entry in getAllButtons())
            {
                inventory.setItem(entry.key, entry.value.constructItemStack(player))
            }
        }


        player.openInventory(inventory)

        object : BukkitRunnable() {

            override fun run() {
                if (!player.isOnline)
                {
                    cancel()
                }

                if (MenuController.menuMap.containsKey(player.uniqueId))
                {
                    val inventory = player.openInventory.topInventory

                    inventory.clear()

                    for (item in getAllButtons())
                    {
                        inventory.setItem(item.key, item.value.constructItemStack(player))
                    }
                } else {
                    cancel()
                }

            }

        }.runTaskTimer(AlchemistSpigotPlugin.instance, 20L, 10L)

    }





}