package ltd.matrixstudios.alchemist.commands.player

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import com.google.gson.JsonObject
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import org.bson.Document
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import java.util.*

class SudoCommand : BaseCommand() {

    @CommandAlias("sudoall")
    @CommandPermission("alchemist.sudoall")
    fun sudoAll(sender: CommandSender, @Name("message")message: String) {
        Bukkit.getOnlinePlayers().forEach {
            it.chat(message)
        }

        sender.sendMessage(Chat.format("&aSudo'ed every person online to say &f$message"))
    }

    @CommandAlias("createprofiles")
    @CommandPermission("alchemist.owner")
    fun createProfiles(sender: CommandSender)
    {
        for (int in 0 until 100000)
        {
            val profile = GameProfile(UUID.randomUUID(), "funny_player_$int", "funny_player_$int", JsonObject(), "", arrayListOf(), arrayListOf(), null, System.currentTimeMillis())

            ProfileGameService.save(profile)
        }

        sender.sendMessage(Chat.format("&aCreated profiles!"))
    }
    
}