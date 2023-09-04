package ltd.matrixstudios.alchemist.profiles.commands.auth.menu

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.profiles.getProfile
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.items.ItemBuilder
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.PlaceholderButton
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.buttons.SkullButton
import ltd.matrixstudios.alchemist.util.skull.SkullUtil
import ltd.matrixstudios.alchemist.util.totp.TOTPUtil
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.Skull
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

class AuthSetupMenu(val player: Player) : Menu(player)
{

    init
    {
        staticSize = 45
        placeholder = true
    }
    override fun getButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        buttons[11] = StepButton(1, mutableListOf(
            " ",
            "&7The first step is to access your mobile device.",
            "&7Some of the most popular 2 Factor Authentication",
            "&7apps are Google Authenticator and AuthMe.",
            " ",
        ), Chat.format("&a&lAccess Your Mobile Device"), Material.PAPER)

        buttons[12] = StepButton(2, mutableListOf(
            " ",
            "&7The second step is to choose your designated",
            "&7app and let it install. Once the app finishes",
            "&7installing, there may be an account setup process",
            "&7that you need to complete.",
            " ",
        ), Chat.format("&a&lInstall Your App"), Material.PAPER)

        buttons[13] = StepButton(3, mutableListOf(
            " ",
            "&7The third step is to get to the home page",
            "&7of the authenticator app. This step usually happens",
            "&7after you setup your account.",
            " ",
        ), Chat.format("&a&lNavigate To Home Page"), Material.PAPER)

        buttons[14] = StepButton(4, mutableListOf(
            " ",
            "&7The fourth step is to add the code given into",
            "&7into the application. The code is given when",
            "&7clicking the green wool. On most apps, ",
            "&7there will be a + icon or an 'Add Authenticator'",
            "&7section which you can use to input the code.",
            " ",
        ), Chat.format("&a&lGet QR Code"), Material.PAPER)

        buttons[15] = StepButton(5, mutableListOf(
            " ",
            "&7Once inputted, you must use the code",
            "&7in the command /auth verify <code>",
            "&7to allow you to run commands as a staff member.",
            " ",
        ), Chat.format("&a&lAuthenticate In Game"), Material.PAPER)

        buttons[22 + 9] = YourProfileButton()
        buttons[22] = PlaceholderButton(Material.PAINTING, mutableListOf(
            Chat.format(" "),
            Chat.format("&7Two Factor Authentication, also commonly called"),
            Chat.format("&72fa, is a method that people use to protect"),
            Chat.format("&7their accounts on popular websites."),
            Chat.format("&7the practical use of this for minecraft servers"),
            Chat.format("&7is to protect staff members from being hacked."),
            " "
        ), Chat.format("&e&lWhat Is 2fa?"), 0)

        buttons[30] = SimpleActionButton(
            Material.WOOL, mutableListOf(),
            "&aI Accept This Information",
            5
        ).setBody { player, i, clickType ->
            val profile = player.getProfile() ?: return@setBody
            player.closeInventory()

            player.sendMessage(Chat.format("&eYour authentication link is:"))
            player.sendMessage("${ChatColor.GOLD}${TOTPUtil.qrImageUrl(profile.getAuthStatus().secret, player.name)}")

            val troublesComponent = Component.text(
                "Having problems? No worries! Hover over this text to view your secret key"
            ).color(NamedTextColor.GRAY).hoverEvent(HoverEvent.showText(Component.text(Chat.format("&7Your secret (input in the app): &f${profile.getAuthStatus().secret}"))))

            AlchemistSpigotPlugin.instance.audience.player(player).sendMessage(troublesComponent)
        }

        buttons[32] = SimpleActionButton(
            Material.WOOL, mutableListOf(),
            "&cI Decline This Information",
            14
        ).setBody { player, i, clickType ->
            player.closeInventory()
            player.sendMessage(Chat.format("&cYou have stopped the process!"))
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Setup your 2fa"
    }

    class YourProfileButton : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.DIRT
        }

        override fun getButtonItem(player: Player): ItemStack
        {
            val skull = SkullUtil.generate(player.name, "")

            return ItemBuilder.copyOf(skull).setLore(getDescription(player).toList()).name(Chat.format("&6&lYour Profile")).build()
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            val desc = mutableListOf<String>()
            val profile = player.getProfile()

            if (profile != null)
            {
                desc.add(" ")
                desc.add(Chat.format("&7Currently Bypassed: &f${profile.getAuthStatus().authBypassed}"))
                desc.add(Chat.format("&7Needs Auth: &f${profile.getCurrentRank().staff}"))
                desc.add(Chat.format("&7Your Rank: &f${profile.getCurrentRank().color + profile.getCurrentRank().displayName}"))
                desc.add(" ")
                desc.add(Chat.format("&aYour Secret: &f${profile.getAuthStatus().secret}"))
                desc.add(" ")
            }

            return desc
        }

        override fun getDisplayName(player: Player): String { return "" }

        override fun getData(player: Player): Short { return 0 }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {}

    }

    class StepButton(var step: Int, var desc: MutableList<String>, var displayName: String, val material: Material) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return material
        }

        override fun setCustomAmount(player: Player): Int
        {
            return step
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            return desc.map { Chat.format(it) }.toMutableList()
        }

        override fun getDisplayName(player: Player): String?
        {
            return Chat.format(displayName)
        }

        override fun getData(player: Player): Short
        {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) { }

    }
}