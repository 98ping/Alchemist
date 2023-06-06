package ltd.matrixstudios.alchemist.auth

import lombok.AllArgsConstructor
import org.bukkit.entity.Player
import org.bukkit.map.MapCanvas
import org.bukkit.map.MapRenderer
import org.bukkit.map.MapView
import java.awt.Image
import java.util.*



class AuthCodeMap(
    val qrCodeImage: Image? = null,
    val uuid: UUID? = null
) : MapRenderer() {
    override fun render(view: MapView, canvas: MapCanvas, player: Player) {
        if (player.uniqueId == uuid) {
            canvas.drawImage(0, 0, qrCodeImage)
        }
    }
}
