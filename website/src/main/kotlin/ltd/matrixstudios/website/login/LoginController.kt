package ltd.matrixstudios.website.login

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

/**
 * Class created on 11/23/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
@Controller
class LoginController {

    @RequestMapping(value = ["/login"], method = [RequestMethod.GET])
    fun onLoginRequest(): ModelAndView = ModelAndView("login")
}