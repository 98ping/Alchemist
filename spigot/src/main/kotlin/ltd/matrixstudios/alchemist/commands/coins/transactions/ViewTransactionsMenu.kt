package ltd.matrixstudios.alchemist.commands.coins.transactions

import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.type.BorderedPaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

/**
 * Class created on 8/23/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class ViewTransactionsMenu(val player: Player, val transactions: MutableList<Transaction>) :
    BorderedPaginatedMenu(player)
{
    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (transaction in transactions)
        {
            buttons[i++] = TransactionButton(transaction)
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Your Transactions"
    }

    class TransactionButton(val transaction: Transaction) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.BOOK
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            val desc = mutableListOf<String>()
            desc.add(Chat.format(" "))
            desc.add(Chat.format("&7Items Purchased: &f" + transaction.items.size))
            desc.add(Chat.format("&7Amount Spent: &f$" + transaction.coinsSpent))
            desc.add(Chat.format("&7Bought On: &f${transaction.on}"))
            desc.add(Chat.format(" "))
            desc.add(Chat.format("&7Detailed Breakdown:"))
            for (item in transaction.items)
            {
                desc.add(
                    Chat.format(
                        "&7- &r${item.displayName} &7(" + (if (item.discount != 0.0) "&c&m$${
                            Math.round(
                                item.price
                            )
                        }&r &7-> &a$${Math.round(item.price.minus(item.discount))}" else "&a${Math.round(item.price)}") + "&7)"
                    )
                )
            }
            desc.add(Chat.format(" "))
            return desc
        }

        override fun getDisplayName(player: Player): String
        {
            return Chat.format("&a${transaction.id}")
        }

        override fun getData(player: Player): Short
        {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
        }

    }
}