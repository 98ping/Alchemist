package ltd.matrixstudios.alchemist.auth

import com.google.common.net.UrlEscapers
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.auth.AuthFactors
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import org.apache.commons.codec.binary.Base32
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.conversations.ConversationContext
import org.bukkit.conversations.Prompt
import org.bukkit.conversations.StringPrompt
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.map.MapRenderer
import java.awt.image.BufferedImage
import java.io.IOException
import java.net.URL
import java.net.URLEncoder
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.SecureRandom
import java.util.function.Consumer
import javax.imageio.ImageIO


class AuthPrompt : StringPrompt() {
    private var failures = 0

    @Suppress("deprecation")
    override fun getPromptText(context: ConversationContext): String {
        val player = context.forWhom as Player
        if (failures == 0) {
            val secret = generateSecret()
            val image = generateImage(player, secret)
            if (image != null) {
                val mapView = Bukkit.getServer().createMap(player.world)
                mapView.renderers.forEach(Consumer { mapRenderer: MapRenderer? ->
                    mapView.removeRenderer(
                        mapRenderer
                    )
                })
                mapView.addRenderer(AuthCodeMap(image, player.uniqueId))
                val mapItem = ItemStack(Material.MAP, 1, mapView.id)
                val mapMeta = mapItem.itemMeta
                mapMeta.displayName = ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "2FA Code"
                mapMeta.lore = listOf("QR Code Map")
                mapItem.itemMeta = mapMeta
                context.setSessionData("secret", secret)
                context.setSessionData("map", mapItem)
                player.sendMap(mapView)
                player.inventory.addItem(mapItem)
                player.updateInventory()
            }
        }
        return Chat.format("&eYou have received your unique QR Code as a map. You must scan this map with an authentication device in order to finish this process!")
    }

    override fun acceptInput(context: ConversationContext, input: String): Prompt {
        val mapItem = context.getSessionData("map") as ItemStack
        val secret = context.getSessionData("secret") as String
        val player = context.forWhom as Player
        val profile = AlchemistAPI.syncFindProfile(player.uniqueId)!!

        player.inventory.remove(mapItem)

        val code: Int = try {
            input.replace(" ", "").toInt()
        } catch (e: NumberFormatException) {
            return incrementAttempts(context)
        }
        val authfactor = AuthFactors(false, false, null, "")

        if (!authfactor.isAuthValid(secret, code)) {
            return incrementAttempts(context)
        }

        authfactor.setup2fa = true
        authfactor.lastAuth = System.currentTimeMillis()
        authfactor.secret = secret

        profile.authFactor = authfactor

        ProfileGameService.save(profile)

        context.forWhom.sendRawMessage(Chat.format("&aYour Two Factor Authentication was successfully set up! Thanks for keeping the server safe :)"))
        return END_OF_CONVERSATION
    }

    private fun incrementAttempts(context: ConversationContext): Prompt {
        val attempts = failures++
        if (attempts >= 3) {
            context.forWhom.sendRawMessage(Chat.format("&cYou have tried to input your 2FA code too many times!"))
            context.forWhom.sendRawMessage(Chat.format("&cThis process will need to be restarted because of it."))
            return END_OF_CONVERSATION
        }
        context.forWhom.sendRawMessage(Chat.format("&cThis code that you provided is not valid!"))
        context.forWhom.sendRawMessage((ChatColor.RED).toString() + "You currently have " + ChatColor.WHITE + (3 - attempts) + ChatColor.RED + " attempts left.")
        return this
    }

    @Suppress("deprecation")
    private fun generateImage(player: Player, secret: String): BufferedImage? {
        val urlEscape = UrlEscapers.urlFragmentEscaper()
        val issuer: String = "Alchemist"
        val url =
            "otpauth://totp/" + urlEscape.escape(player.name) + "?secret=" + secret + "&issuer=" + urlEscape.escape(
                issuer
            )
        val imageUrl = String.format(IMAGE_URL_FORMAT, URLEncoder.encode(url))
        return try {
            ImageIO.read(URL(imageUrl))
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        private var SECURE_RANDOM: SecureRandom? = null
        private val BASE_32_ENCODER = Base32()
        private const val IMAGE_URL_FORMAT = "https://www.google.com/chart?chs=130x130&chld=M%%7C0&cht=qr&chl=%s"

        init {
            try {
                SECURE_RANDOM = SecureRandom.getInstance("SHA1PRNG", "SUN")
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException("Algorithm does not exist.")
            } catch (e: NoSuchProviderException) {
                throw RuntimeException("Algorithm does not exist.")
            }
        }

        private fun generateSecret(): String {
            val secretKey = ByteArray(10)
            SECURE_RANDOM!!.nextBytes(secretKey)
            return BASE_32_ENCODER.encodeToString(secretKey)
        }
    }
}