package ltd.matrixstudios.website.landing

import ltd.matrixstudios.website.user.AlchemistUser
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest

/**
 * Class created on 11/24/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
@Controller
class LandingController {

    @RequestMapping(value = ["/", "/home"], method = [RequestMethod.GET])
    fun onLandRequest(): ModelAndView = ModelAndView("login")

    @RequestMapping(value = ["/dashboard", "/panel"], method = [RequestMethod.GET])
    fun onDashboardRequest(request: HttpServletRequest) : ModelAndView
    {
        val modelAndView = ModelAndView("home")
        val profile = request.session.getAttribute("user") as AlchemistUser? ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "You must be logged in to view this page")

        modelAndView.addObject("user", profile)
        return modelAndView
    }
}