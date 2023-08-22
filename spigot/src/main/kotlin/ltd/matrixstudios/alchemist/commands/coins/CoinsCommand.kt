package ltd.matrixstudios.alchemist.commands.coins

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.commands.coins.editor.CoinShopEditorMenu
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player

@CommandAlias("coins")
class CoinsCommand : BaseCommand() {

    @Default
    fun coinsCommand(player: Player) {
        val profile = AlchemistAPI.syncFindProfile(player.uniqueId)

        if (profile == null) {
            player.sendMessage(Chat.format("&cYou must have a profile to use this command!"))
            return
        }

        player.sendMessage(Chat.format("&7[&eCoins&7] &eYou have a total of &6" + profile.coins + " &ecoins"))
    }

    @Subcommand("editor")
    @CommandPermission("alchemist.coins.owner")
    fun coinsEditor(player: Player)
    {
        CoinShopEditorMenu(player).openMenu()
    }

    @Subcommand("set")
    @CommandPermission("alchemist.coins.admin")
    fun coinsSetCommand(player: Player, @Name("target") target: GameProfile, @Name("amount") amount: Int) {
        target.coins = amount
        ProfileGameService.save(target)

        player.sendMessage(Chat.format("&7[&eCoins&7] &eYou have set " + AlchemistAPI.getRankDisplay(target.uuid) + "'s &ecoins to &6" + amount))
    }

    @Subcommand("give")
    @CommandPermission("alchemist.coins.admin")
    fun coinsGiveCommand(player: Player, @Name("target") target: GameProfile, @Name("amount") amount: Int) {
        target.coins = amount
        ProfileGameService.save(target)

        player.sendMessage(Chat.format("&7[&eCoins&7] &eYou have given &6" + amount + " &ecoins to the player " + AlchemistAPI.getRankDisplay(target.uuid)))
    }
}