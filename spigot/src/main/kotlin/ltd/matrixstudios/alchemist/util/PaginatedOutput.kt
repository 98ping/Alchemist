package ltd.matrixstudios.alchemist.util


import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

abstract class PaginatedOutput<T> @JvmOverloads constructor(private val resultsPerPage: Int = 9) {

    init {
        assert(resultsPerPage > 0)
    }

    fun display(sender: CommandSender, results: Collection<T>, page: Int) {
        this.display(sender, ArrayList(results), page)
    }

    fun display(sender: CommandSender, results: List<T>, page: Int) {
        if (results.isEmpty()) {
            sender.sendMessage(ChatColor.RED.toString() + "No entries were found.")
            return
        }

        val maxPages = results.size / this.resultsPerPage + 1

        if (page <= 0 || page > maxPages) {
            sender.sendMessage(ChatColor.RED.toString() + "Page '" + page + "' not found. (" + ChatColor.YELLOW + "1 - " + maxPages + ChatColor.RED + ")")
            return
        }

        val msgs = this.getHeader(page, maxPages)

        for (message in msgs)
        {
            sender.sendMessage(message)
        }

        var i = this.resultsPerPage * (page - 1)
        while (i < this.resultsPerPage * page && i < results.size) {
            sender.sendMessage(this.format(results[i], i))
            ++i
        }
    }

    abstract fun getHeader(page: Int, maxPages: Int): List<String>

    abstract fun format(result: T, resultIndex: Int): String

}