package ltd.matrixstudios.alchemist.commands.coins

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import ltd.matrixstudios.alchemist.commands.coins.shop.CoinShopMenu
import org.bukkit.entity.Player

class CoinShopCommand : BaseCommand()
{

    @CommandAlias("coinshop")
    fun coinShop(player: Player)
    {
        CoinShopMenu(player).openMenu()
    }
}