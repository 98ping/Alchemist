package ltd.matrixstudios.website.utils.security

import ltd.matrixstudios.website.user.loader.UserServicesComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import javax.servlet.http.Cookie

/**
 * Class created on 11/24/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
@Configuration
@EnableWebSecurity
open class WebSecurity : WebSecurityConfigurerAdapter()
{
    @Autowired
    private val bCryptPasswordEncoder: BCryptPasswordEncoder? = null

    @Bean
    open fun constructUserDetails(): UserDetailsService
    {
        return UserServicesComponent.userService
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder)
    {
        val userDetailsService = constructUserDetails()

        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(bCryptPasswordEncoder)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity)
    {
        http
            .csrf().and()
            .authorizeRequests()
            .antMatchers("/**").permitAll()
            .anyRequest()
            .authenticated().and().formLogin()
            .loginPage("/login").failureUrl("/login?error=true")
            .usernameParameter("username")
            .passwordParameter("password")
            .and().logout()
            .logoutRequestMatcher(AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/").addLogoutHandler { req, res, auth ->
                val cookie = Cookie("guest", "")
                cookie.maxAge = 0
                res.addCookie(cookie)
            }.and().exceptionHandling()
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity)
    {
        web
            .ignoring()
            .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**")
    }

    @Bean
    open fun corsConfigurationSource(): CorsConfigurationSource
    {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}