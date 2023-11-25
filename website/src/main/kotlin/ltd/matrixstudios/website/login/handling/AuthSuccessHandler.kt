package ltd.matrixstudios.website.login.handling

import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.website.user.loader.UserServicesComponent
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
/**
 * Class created on 11/24/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
@Component
class AuthSuccessHandler : AuthenticationSuccessHandler
{

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse, authentication: Authentication
    )
    {
        // set our response to OK status
        response.status = HttpServletResponse.SC_OK
        response.sendRedirect(if (request.getAttribute("redirect") == null) "/" else request.getAttribute("redirect") as String)
        val user = UserServicesComponent.userService.findUserByName(authentication.name) ?: throw RuntimeException("Authentication not found")

        request.session.setAttribute("user", user)
        // shouldn't null because we check this. If it does it is not
        // out fault
        request.session.setAttribute("profile", ProfileGameService.byId(user.minecraft_uuid))
    }
}