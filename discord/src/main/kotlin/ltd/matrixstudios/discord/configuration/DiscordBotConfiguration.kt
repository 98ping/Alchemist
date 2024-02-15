package ltd.matrixstudios.discord.configuration

import ltd.matrixstudios.amber.configurations.annotate.EntryName
import ltd.matrixstudios.amber.configurations.annotate.Intrinsic
import ltd.matrixstudios.amber.configurations.annotate.primitives.DefaultString

interface DiscordBotConfiguration
{
    @Intrinsic
    @EntryName("getSyncChannelId")
    @DefaultString("CHANNEL-ID")
    fun getSyncChannelId(): String

    @Intrinsic
    @EntryName("mongo-uri")
    @DefaultString("mongodb://localhost:27017")
    fun getMongoURI(): String
}