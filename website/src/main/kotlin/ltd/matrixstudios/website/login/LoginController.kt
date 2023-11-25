package ltd.matrixstudios.website.login

import ltd.matrixstudios.website.user.AlchemistUser
import ltd.matrixstudios.website.user.loader.UserServicesComponent
import ltd.matrixstudios.website.utils.mojang.MojangUtils
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView
import javax.validation.Valid

/**
 * Class created on 11/23/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
@Controller
class LoginController
{

    @RequestMapping(value = ["/login"], method = [RequestMethod.GET])
    fun onLoginRequest(): ModelAndView = ModelAndView("login")

    @RequestMapping(value = ["/register"], method = [RequestMethod.GET])
    fun onRegistrationRequest(): ModelAndView
    {
        val modelAndView = ModelAndView()
        val user = AlchemistUser()
        modelAndView.addObject("user", user)
        modelAndView.viewName = "register"
        return modelAndView
    }

    @RequestMapping(value = ["/register"], method = [RequestMethod.POST])
    fun createNewUser(user: @Valid AlchemistUser?, bindingResult: BindingResult): ModelAndView
    {
        val modelAndView = ModelAndView()
        val userExists = UserServicesComponent.userService.findUserByName(user!!.username)

        if (userExists != null)
        {
            bindingResult
                .rejectValue(
                    "username", "error.user",
                    "There is already a user registered with the username provided"
                )
        }

        if (MojangUtils.fetchUUID(user.username) == null)
        {
            bindingResult.rejectValue(
                "username", "error.user",
                "This is not a valid MineCraft player!"
            )
        }
        if (bindingResult.hasErrors())
        {
            modelAndView.viewName = "register"
        } else
        {
            UserServicesComponent.userService.createUser(user)
            modelAndView.addObject("successMessage", "User has been registered successfully")
            modelAndView.addObject("user", user)
            modelAndView.viewName = "login"
        }
        return modelAndView
    }
}