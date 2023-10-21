package ltd.matrixstudios.alchemist.commands.coins

import com.mongodb.client.model.UpdateOptions
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.commands.coins.category.CoinShopCategory
import ltd.matrixstudios.alchemist.commands.coins.item.CoinShopItem
import ltd.matrixstudios.alchemist.commands.coins.transactions.Transaction
import ltd.matrixstudios.alchemist.util.Chat
import org.bson.Document
import java.util.*
import java.util.concurrent.CompletableFuture

object CoinShopManager
{
    val transactions = Alchemist.MongoConnectionPool.getCollection("coinShopTransactions")
    val items = Alchemist.MongoConnectionPool.getCollection("coinShopItems")
    val categories = Alchemist.MongoConnectionPool.getCollection("coinShopCategories")

    val itemMap: MutableMap<String, CoinShopItem> = mutableMapOf()
    val categoryMap: MutableMap<String, CoinShopCategory> = mutableMapOf()
    val transactionMap: MutableMap<UUID, MutableList<Transaction>> = mutableMapOf()

    fun loadCoinShopAssets()
    {
        val start = System.currentTimeMillis()
        val cursor = items.find().cursor()

        while (cursor.hasNext())
        {
            val item = cursor.next()
            val gson = Alchemist.gson.fromJson(item.toJson(), CoinShopItem::class.java)

            itemMap[gson.id] = gson
        }

        Chat.sendConsoleMessage(
            Chat.format(
                "&e[Coin Items] &fLoaded all coin shop item entries in " + System.currentTimeMillis()
                    .minus(start) + "ms"
            )
        )

        val start2 = System.currentTimeMillis()
        val categoryCursor = categories.find().cursor()

        while (categoryCursor.hasNext())
        {
            val item = categoryCursor.next()
            val gson = Alchemist.gson.fromJson(item.toJson(), CoinShopCategory::class.java)

            categoryMap[gson.id] = gson
        }

        Chat.sendConsoleMessage(
            Chat.format(
                "&e[Coin Category] &fLoaded all coin shop item categories in " + System.currentTimeMillis()
                    .minus(start2) + "ms"
            )
        )

    }

    fun addTransaction(transaction: Transaction): CompletableFuture<Transaction>
    {
        return CompletableFuture.supplyAsync {
            transactions.updateOne(
                Document("_id", transaction.id.toString()),
                Document("\$set", Document.parse(Alchemist.gson.toJson(transaction))),
                UpdateOptions().upsert(true)
            )
            val target = transaction.user

            if (!transactionMap.containsKey(target))
            {
                transactionMap[target] = Collections.singletonList(transaction)
            } else
            {
                val currentList = transactionMap[target]!!
                currentList.add(transaction)

                transactionMap[target] = currentList
            }

            transaction
        }
    }


    fun saveItem(item: CoinShopItem): CompletableFuture<CoinShopItem>
    {
        return CompletableFuture.supplyAsync {
            items.updateOne(
                Document("_id", item.id),
                Document("\$set", Document.parse(Alchemist.gson.toJson(item))),
                UpdateOptions().upsert(true)
            )
            itemMap[item.id] = item

            item
        }
    }

    fun deleteItem(item: CoinShopItem)
    {
        CompletableFuture.runAsync {
            items.deleteOne(Document("_id", item.id))
            itemMap.remove(item.id)
        }
    }

    fun findCategory(id: String): CoinShopCategory? = categoryMap.getOrDefault(id.lowercase(Locale.getDefault()), null)

    fun deleteCategory(item: CoinShopCategory)
    {
        CompletableFuture.runAsync {
            categories.deleteOne(Document("_id", item.id))
            categoryMap.remove(item.id)
        }
    }

    fun saveCategory(item: CoinShopCategory): CompletableFuture<CoinShopCategory>
    {
        return CompletableFuture.supplyAsync {
            categories.updateOne(
                Document("_id", item.id),
                Document("\$set", Document.parse(Alchemist.gson.toJson(item))),
                UpdateOptions().upsert(true)
            )
            categoryMap[item.id] = item

            item
        }
    }


    fun lookupTransactions(user: UUID): CompletableFuture<MutableList<Transaction>>
    {
        return CompletableFuture.supplyAsync {
            val cursor = transactions.find(Document("user", user.toString())).cursor()
            val items = mutableListOf<Transaction>()

            while (cursor.hasNext())
            {
                val item = cursor.next()
                items.add(Alchemist.gson.fromJson(item.toJson(), Transaction::class.java))
            }

            items
        }
    }

    fun findAllTransactions(uuid: UUID): MutableList<Transaction>
    {
        return transactionMap.getOrDefault(uuid, mutableListOf())
    }

    fun getTotalPriceOfTransactions(list: MutableList<Transaction>): Double
    {
        var price = 0.0

        for (t in list)
        {
            price += t.coinsSpent
        }

        return price
    }
}