package ltd.matrixstudios.alchemist.disguise.commands


import co.aikar.commands.BaseCommand
import co.aikar.commands.ConditionFailedException
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import ltd.matrixstudios.alchemist.disguise.commands.menu.skin.DisguiseSelectNameMenu
import ltd.matrixstudios.alchemist.models.profile.disguise.SkinDisguiseAttribute
import ltd.matrixstudios.alchemist.profiles.getProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import net.pinger.disguise.DisguiseAPI
import net.pinger.disguise.exception.UserNotFoundException
import net.pinger.disguise.skin.Skin
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object DisguiseCommand : BaseCommand()
{

    @CommandAlias("reveal|realname|disguiseinfo")
    @CommandPermission("alchemist.disguise.reveal")
    fun reveal(sender: CommandSender, @Name("target") target: OnlinePlayer)
    {
        sender.sendMessage(" ")
        sender.sendMessage(Chat.format("&ePlayers that disguised as &6${target.player.displayName}&e:"))
        val profile = target.player.getProfile()

        if (profile == null)
        {
            sender.sendMessage(Chat.format("&e• &cNone (Profile Not Found)"))
            sender.sendMessage(" ")
            return
        }

        if (profile.username == target.player.name)
        {
            sender.sendMessage(Chat.format("&e• &cNot Disguised"))
            sender.sendMessage(" ")
            return
        }

        sender.sendMessage(Chat.format("&e• &6${profile.username} &a(CURRENT)"))
        sender.sendMessage(" ")
    }

    @CommandAlias("undisguise|unnick")
    @CommandPermission("alchemist.disguise")
    fun unDisguise(player: Player)
    {
        val profile = player.getProfile() ?: return

        if (profile.skinDisguiseAttribute != null)
        {
            profile.skinDisguiseAttribute = null
            DisguiseAPI.getDefaultProvider().resetPlayer(player)

            val location = player.location

            // refresh player skin
            player.teleport(Location(Bukkit.getWorld("SkinUpdateWorld"), 0.0, 100.0, 0.0))
            player.teleport(location)

            player.sendMessage(Chat.format("&aSuccess! You have reset your name and skin."))
            ProfileGameService.save(profile)
        } else
        {
            throw ConditionFailedException(
                "You are not currently disguised!"
            )
        }
    }

    @CommandAlias("disguise|nick")
    @CommandPermission("alchemist.disguise")
    fun onDisguise(player: Player)
    {
        DisguiseSelectNameMenu(player).openMenu()
    }

    @CommandAlias("manualdisguise|manualnick")
    @CommandPermission("alchemist.disguise.manual")
    fun onManualDisguise(player: Player, @Name("name") name: String)
    {
        if (!player.hasPermission("alchemist.disguise.custom.unrestricted"))
        {

            if (name.length < 3)
            {
                throw ConditionFailedException(
                    "This disguise is too short!"
                )
            }

            if (name.length >= 16)
            {
                throw ConditionFailedException(
                    "This disguise is too long!"
                )
            }
        }

        player.getProfile().apply {
            if (this != null)
            {
                val skin: Skin?
                try
                {
                    skin = DisguiseAPI.getSkinManager().getFromMojang(name)
                } catch (e: UserNotFoundException)
                {
                    throw ConditionFailedException(
                        "This user does not have a Minecraft account!"
                    )
                }

                this.skinDisguiseAttribute = SkinDisguiseAttribute(name, System.currentTimeMillis(), name, skin.value, skin.signature)

                player.displayName = this.skinDisguiseAttribute!!.customName
                player.playerListName = player.displayName
                player.customName = player.displayName

                DisguiseAPI.getDefaultProvider().updatePlayer(player, skin, name)
                val location = player.location

                // refresh player skin
                player.teleport(Location(Bukkit.getWorld("SkinUpdateWorld"), 0.0, 100.0, 0.0))
                player.teleport(location)

                ProfileGameService.save(this)
                player.sendMessage(Chat.format("&aSuccess! You now look like &f${name}&a."))
                player.sendMessage(Chat.format("&c${name} is an existing Minecraft player, so if they log in for the first time as you're disguised, you will be kicked!"))
            }
        }

    }
}