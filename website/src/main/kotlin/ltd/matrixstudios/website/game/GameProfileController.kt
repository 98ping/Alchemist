package ltd.matrixstudios.website.game

import com.google.gson.JsonObject
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.website.AlchemistUser
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.website.user.loader.UserServicesComponent
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.ModelAndView
import java.util.*
import javax.servlet.http.HttpServletRequest

@Controller
class GameProfileController
{
    @RequestMapping(value = ["/api/users"], method = [RequestMethod.GET])
    fun getAllUsers(request: HttpServletRequest): ModelAndView
    {
        val modelAndView = ModelAndView("user/users")

        val profile = request.session.getAttribute("user") as AlchemistUser?
            ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "You must be logged in to view this page")
        if (!profile.hasPermission("alchemist.website.users")) throw ResponseStatusException(
            HttpStatus.FORBIDDEN,
            "You do not have permission to view this page."
        )

        // Limit results to 10 a page
        val page = 1
        val users = ProfileGameService.handler.retrieveAll().take(page * 10)

        modelAndView.addObject("section", "profiles")
        modelAndView.addObject("user", profile)
        modelAndView.addObject("users", users)
        modelAndView.addObject("page", page)
        return modelAndView
    }

    @RequestMapping(value = ["/api/users/lookup/{id}"], method = [RequestMethod.GET])
    fun onLookupProfile(
        @PathVariable id: String,
        request: HttpServletRequest
    ) : ModelAndView {
        val modelAndView = ModelAndView("user/user-lookup")

        val profile = request.session.getAttribute("user") as AlchemistUser?
            ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "You must be logged in to view this page")
        if (!profile.hasPermission("alchemist.website.users")) throw ResponseStatusException(
            HttpStatus.FORBIDDEN,
            "You do not have permission to view this page."
        )

        val found = UserServicesComponent.userService.findProfileByNiceUUID(id)
            ?: throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "The user requested does not exist. Please ensure that the UUID is correct!"
            )

        val decoyProfile = GameProfile(UUID.fromString("401202a3-0102-4ed8-979a-e5d4832c8a9b"), "itsjhalt", "itsjhalt", JsonObject(), "12", arrayListOf(), arrayListOf(), null, null, null, arrayListOf(), System.currentTimeMillis())

        modelAndView.addObject("target", found)
        modelAndView.addObject("page", 1)
        modelAndView.addObject("users", listOf(decoyProfile, decoyProfile, decoyProfile, decoyProfile, decoyProfile, decoyProfile, decoyProfile, decoyProfile, decoyProfile, decoyProfile))
        modelAndView.addObject("section", "userLookup")

        return modelAndView
    }
}