package ltd.matrixstudios.website.ranks

import ltd.matrixstudios.alchemist.models.website.AlchemistUser
import ltd.matrixstudios.alchemist.service.ranks.RankService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.ModelAndView
import java.lang.NumberFormatException
import javax.servlet.http.HttpServletRequest

/**
 * Class created on 11/24/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
@Controller
class RankController @Autowired constructor(private val repository: RankRepository) {

    @RequestMapping(value = ["/api/ranks"], method = [RequestMethod.GET])
    fun getAllRanks(request: HttpServletRequest): ModelAndView
    {
        val modelAndView = ModelAndView("ranks")

        val profile = request.session.getAttribute("user") as AlchemistUser? ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "You must be logged in to view this page")
        if (!profile.hasPermission("alchemist.website.ranks")) throw ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to view this page.")
        val ranks = repository.findAll();

        modelAndView.addObject("section", "ranks")
        modelAndView.addObject("ranks", ranks)
        modelAndView.addObject("user", profile)
        return modelAndView
    }

    @RequestMapping(value = ["/api/rank-editor/{id}"], method = [RequestMethod.GET])
    fun onShowRankEditor(
        @PathVariable id: String,
        model: Model,
        request: HttpServletRequest
    ) : ModelAndView {
        val modelAndView = ModelAndView("rank-editor")

        val profile = request.session.getAttribute("user") as AlchemistUser? ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "You must be logged in to view this page")
        if (!profile.hasPermission("alchemist.website.ranks")) throw ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to view this page.")
        val rank = repository.findById(id.lowercase())

        if (rank.isEmpty)
        {
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Unable to handle request because rank does not exist!"
            )
        }

        modelAndView.addObject("section", "rank-editor")
        modelAndView.addObject("rank", rank.get())
        modelAndView.addObject("user", profile)
        return modelAndView
    }

    @RequestMapping(value = ["/api/change-rank/{id}"], method = [RequestMethod.POST])
    fun onAPIRankChange(
        @PathVariable id: String,
        @RequestBody ref: String,
        request: HttpServletRequest
    ) : ModelAndView {
        val profile = request.session.getAttribute("user") as AlchemistUser? ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "You must be logged in to view this page")
        if (!profile.hasPermission("alchemist.website.ranks")) throw ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to view this page.")
        val rankOptional = repository.findById(id.lowercase())

        if (rankOptional.isEmpty)
        {
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Unable to handle request because rank does not exist!"
            )
        }

        val rank = rankOptional.get()

        // we dont really need to do this because it is all admins editing the ranks
        // but just in case we are gonna take extra steps
        val safeRef = ref
            .replace("<script>", "<script type=\"javascript/blocked\">")
            .replace("<div>", "<bdiv>")
            .replace("</div>", "</bdiv>")

        var updated = false

        safeRef.split("&").forEach { decoded ->
            val input = decoded.split("=")[0]
            val toSet = decoded.split("=")[1].replace("+", " ")

            when (input) {
                "displayName" -> {
                    if (toSet.isNotEmpty()) {
                        rank.displayName = toSet
                        updated = true
                    }
                }

                "prefix" -> {
                    if (toSet.isNotEmpty()) {
                        rank.prefix = toSet.replace("%26", "&")
                        updated = true
                    }
                }

                "color" -> {
                    if (toSet.isNotEmpty()) {
                        rank.color = toSet.replace("%26", "&")
                        updated = true
                    }
                }

                "weight" -> {
                    if (toSet.isNotEmpty()) {
                        var integer = Integer.MAX_VALUE

                        try {
                            integer = Integer.parseInt(toSet)
                        } catch (_: NumberFormatException) {}

                        // Ensure that people cant set strings to ints without
                        // checking
                        if (integer != Integer.MAX_VALUE) {
                            rank.weight = integer
                            updated = true
                        }
                    }
                }
            }
        }

        if (updated) {
            RankService.save(rank)
            println("Saved the rank ${rank.displayName} to mongo.")
        }

        val modelAndView = ModelAndView("rank-editor")

        modelAndView.addObject("section", "rank-editor")
        modelAndView.addObject("rank", rank)
        modelAndView.addObject("user", profile)

        return modelAndView
    }
}