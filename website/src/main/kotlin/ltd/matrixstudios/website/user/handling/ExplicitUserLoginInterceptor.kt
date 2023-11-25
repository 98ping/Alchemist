package ltd.matrixstudios.website.user.handling

import ltd.matrixstudios.website.user.AlchemistUser
import org.springframework.stereotype.Service
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
/**
 * Class created on 11/24/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
@Service
class ExplicitUserLoginInterceptor : HandlerInterceptorAdapter() {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val user = request.session.getAttribute("user") as AlchemistUser?
        if (user != null) response.sendRedirect("/")
        return user == null
    }
}