package ltd.matrixstudios.alchemist.commands.coins

import com.mongodb.client.model.UpdateOptions
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.commands.coins.category.CoinShopCategory
import ltd.matrixstudios.alchemist.commands.coins.item.CoinShopItem
import ltd.matrixstudios.alchemist.util.Chat
import org.bson.Document
import java.util.concurrent.CompletableFuture

object CoinShopManager
{
    val transactions = Alchemist.MongoConnectionPool.getCollection("coinShopTransactions")
    val items = Alchemist.MongoConnectionPool.getCollection("coinShopItems")
    val categories = Alchemist.MongoConnectionPool.getCollection("coinShopCategories")

    val itemMap: MutableMap<String, CoinShopItem> = mutableMapOf()
    val categoryMap: MutableMap<String, CoinShopCategory> = mutableMapOf()

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

        Chat.sendConsoleMessage(Chat.format("&e[Coin Items] &fLoaded all coin shop item entries in " + System.currentTimeMillis().minus(start) + "ms"))

        val start2 = System.currentTimeMillis()
        val categoryCursor = categories.find().cursor()

        while (categoryCursor.hasNext())
        {
            val item = cursor.next()
            val gson = Alchemist.gson.fromJson(item.toJson(), CoinShopCategory::class.java)

            categoryMap[gson.id] = gson
        }

        Chat.sendConsoleMessage(Chat.format("&e[Coin Category] &fLoaded all coin shop item categories in " + System.currentTimeMillis().minus(start2) + "ms"))
    }


    fun saveItem(item: CoinShopItem) : CompletableFuture<CoinShopItem>
    {
        return CompletableFuture.supplyAsync {
            items.updateOne(Document("_id", item.id), Document("\$set", Document.parse(Alchemist.gson.toJson(item))), UpdateOptions().upsert(true))
            itemMap[item.id] = item

            item
        }
    }

    fun saveCategory(item: CoinShopCategory) : CompletableFuture<CoinShopCategory>
    {
        return CompletableFuture.supplyAsync {
            categories.updateOne(Document("_id", item.id), Document("\$set", Document.parse(Alchemist.gson.toJson(item))), UpdateOptions().upsert(true))
            categoryMap[item.id] = item

            item
        }
    }
}