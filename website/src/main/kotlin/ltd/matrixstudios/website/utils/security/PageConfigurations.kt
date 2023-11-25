package ltd.matrixstudios.website.utils.security

import ltd.matrixstudios.website.user.handling.ExplicitUserLoginInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class PageConfigurations : WebMvcConfigurer
{
    override fun addViewControllers(registry: ViewControllerRegistry)
    {
        registry.addViewController("/home").setViewName("home")
        registry.addViewController("/").setViewName("home")
        registry.addViewController("/login").setViewName("login")
    }

    @Bean
    open fun passwordEncoder(): BCryptPasswordEncoder
    {
        return BCryptPasswordEncoder()
    }

    override fun addInterceptors(registry: InterceptorRegistry)
    {
        registry.addInterceptor(ExplicitUserLoginInterceptor()).addPathPatterns("/register", "/login")
    }
}