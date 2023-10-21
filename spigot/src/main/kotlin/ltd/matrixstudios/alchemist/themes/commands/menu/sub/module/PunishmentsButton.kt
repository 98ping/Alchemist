package ltd.matrixstudios.alchemist.themes.commands.menu.sub.module

import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor
import ltd.matrixstudios.alchemist.themes.Theme
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class PunishmentsButton(val theme: Theme, val player: Player) : Button()
{

    var fakeGrant = Punishment(
        PunishmentType.BAN.name,
        "7UiO",
        mutableListOf(),
        player.uniqueId,
        player.uniqueId,
        "Test Punishment",
        Long.MAX_VALUE,
        DefaultActor(Executor.CONSOLE, ActorType.GAME)
    )

    override fun getMaterial(player: Player): Material
    {
        return Material.WOOL
    }

    override fun getDescription(player: Player): MutableList<String>
    {
        return theme.getHistoryLore(player, fakeGrant)
    }

    override fun getDisplayName(player: Player): String
    {
        return theme.getHistoryDisplayName(player, fakeGrant)
    }

    override fun getData(player: Player): Short
    {
        return theme.getHistoryData(player, fakeGrant)
    }

    override fun onClick(player: Player, slot: Int, type: ClickType)
    {
        return
    }
}