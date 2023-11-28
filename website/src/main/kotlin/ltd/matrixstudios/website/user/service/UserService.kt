package ltd.matrixstudios.website.user.service

import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.website.AlchemistUser
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.website.WebProfileService
import ltd.matrixstudios.website.utils.mojang.MojangUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Class created on 11/24/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
@Service
class UserService : UserDetailsService
{

    @Autowired lateinit var encoder: BCryptPasswordEncoder

    fun findUserByName(name: String): AlchemistUser? {
        return WebProfileService.handler.retrieveAll().firstOrNull { it.username == name }
    }

    fun findUserByUniqueId(uuid: UUID): AlchemistUser? {
        return WebProfileService.byId(uuid)
    }

    /**
     * This is gonna be one incredibly intensive function
     * because we need to transform their uuid. Yikes!
     */
    fun findProfileByNiceUUID(niceUUID: String) : GameProfile? {
         return ProfileGameService.handler.retrieveAll()
             .firstOrNull { it.uuid.toString().replace("-", "") == niceUUID }
    }

    fun save(user: AlchemistUser) {
        WebProfileService.save(user)
    }

    /**
     * Creating registered users for the first time
     *
     * @param user User to register
     */
    @Throws(Exception::class)
    fun createUser(user: AlchemistUser)
    {
        user.minecraft_uuid = MojangUtils.fetchUUID(user.username)!!
        user.username = user.username
        user.password = encoder.encode(user.password)
        save(user)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails
    {
        val user = findUserByName(username)

        return if (user != null)
        {
            val authorities = getUserAuthority(user)
            buildUserForAuthentication(user, authorities)
        } else
        {
            throw UsernameNotFoundException("Username not found")
        }
    }

    private fun getUserAuthority(user: AlchemistUser): List<GrantedAuthority>
    {
        val permissions: MutableList<GrantedAuthority> = ArrayList()

        for (permission in user.permissions)
        {
            if (permissions.none { it.authority == permission })
            {
                permissions.add(SimpleGrantedAuthority(permission));
            }
        }
        return permissions
    }

    private fun buildUserForAuthentication(user: AlchemistUser, authorities: List<GrantedAuthority>): UserDetails {
        return org.springframework.security.core.userdetails.User(user.username, user.password, authorities)
    }
}