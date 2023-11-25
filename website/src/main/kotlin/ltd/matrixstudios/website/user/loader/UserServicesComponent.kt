package ltd.matrixstudios.website.user.loader

import ltd.matrixstudios.website.user.repository.UserRepository
import ltd.matrixstudios.website.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct


/**
 * Class created on 11/24/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
@Service
class UserServicesComponent @Autowired constructor(val userRepository: UserRepository) {

    companion object
    {
        lateinit var userService: UserService
    }

    @PostConstruct
    fun postConstruct()
    {
        userService = UserService(userRepository)
    }
}