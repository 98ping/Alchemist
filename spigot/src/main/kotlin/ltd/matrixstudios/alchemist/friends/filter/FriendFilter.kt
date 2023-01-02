package ltd.matrixstudios.alchemist.friends.filter

import com.mysql.jdbc.TimeUtil
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import java.util.concurrent.TimeUnit

enum class FriendFilter(val displayName: String)
{
    ALL("All"), ONLINE("Online Friends"), OFFLINE("Offline Friends"), YOUR_SERVER("Your Server"), RECENTLY_JOINED("Recently Joined");

}