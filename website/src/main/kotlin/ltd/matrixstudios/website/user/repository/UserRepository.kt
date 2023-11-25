package ltd.matrixstudios.website.user.repository

import ltd.matrixstudios.website.user.AlchemistUser
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

/**
 * Class created on 11/24/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
@Repository
interface UserRepository : MongoRepository<AlchemistUser, UUID>