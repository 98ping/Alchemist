package ltd.matrixstudios.website.user.service

import ltd.matrixstudios.website.user.AlchemistUser
import ltd.matrixstudios.website.user.repository.UserRepository
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
class UserService @Autowired constructor(private val userRepository: UserRepository) : UserDetailsService
{

    @Autowired lateinit var encoder: BCryptPasswordEncoder

    fun findUserByName(name: String): AlchemistUser? {
        return userRepository.findAll().firstOrNull { it.username == name }
    }

    fun findUserByUniqueId(uuid: UUID): AlchemistUser? {
        return userRepository.findAll().firstOrNull { it.minecraft_uuid.toString() == uuid.toString() }
    }

    fun save(user: AlchemistUser): AlchemistUser {
        return userRepository.save(user)
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
        userRepository.save(user)
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