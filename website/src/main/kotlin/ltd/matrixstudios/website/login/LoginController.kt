package ltd.matrixstudios.website.login

import ltd.matrixstudios.alchemist.models.website.AlchemistUser
import ltd.matrixstudios.website.login.form.AlchemistFormSubmission
import ltd.matrixstudios.website.user.loader.UserServicesComponent
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
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
    fun createNewUser(form: AlchemistFormSubmission?, bindingResult: BindingResult): ModelAndView
    {
        val modelAndView = ModelAndView("register")
        val existing =  UserServicesComponent.userService.findUserByName(form!!.username)

        if (existing == null)
        {
            bindingResult
                .rejectValue(
                    "username", "error.user",
                    "Unable to verify your account. Be sure to register in-game first!"
                )

            return modelAndView
        }

        if (existing.authenticated)
        {
            bindingResult
                .rejectValue(
                    "username", "error.user",
                    "You have already authenticated and proven that it was you!"
                )

            println(existing.username + " tried proving themselves again in the same form!")
            return modelAndView
        }

        val secret = form.secret ?: return modelAndView

        if (secret == existing.secret)
        {
            if (!bindingResult.hasErrors())
            {
                modelAndView.viewName = "login"

                // Set passwords up and encode
                existing.password = form.password
                UserServicesComponent.userService.createUser(existing)
                println("Successfully verified an account under the username " + form.username)

                // Send user to login page
                return modelAndView
            }
        }

        return modelAndView
    }
}