package com.farshonok.spring.http.controllers

import com.farshonok.spring.dto.PersonaInfo
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.SessionAttribute
import org.springframework.web.bind.annotation.SessionAttributes
import org.springframework.web.servlet.ModelAndView
import java.time.LocalDate

@Controller
@RequestMapping("/api/v1")
@SessionAttributes(value = ["user"])
class GreetingController {

    @RequestMapping("/hello")
    fun hello1(
//        request: HttpServletRequest,
        mv: ModelAndView
    ) : ModelAndView {
        val p = PersonaInfo("Ivan", "Inanov", LocalDate.now())
//        request.session.setAttribute("user", p) // sessionScope
//        request.setAttribute("user", p) // requestScope
        mv.addObject("user", p)
        mv.viewName = "greeting/hello"
        return mv
    }

    @RequestMapping("/hello/{id}", method = [RequestMethod.GET])
    fun hello1(request: HttpServletRequest,
              @RequestParam age: Int,
              @RequestHeader("accept") accept: String,
              @CookieValue("JSESSIONID") jsessionId: String,
              @PathVariable("id") id: Long,
    ) : ModelAndView {
        val ageParam = request.getParameter("age")
        val acceptHeader = request.getHeader("accept")
        val cookies = request.cookies
        val jsessionIdCookie = cookies.firstOrNull { cookie -> cookie.name == "JSESSIONID" }

        val mv = ModelAndView()
        mv.viewName = "greeting/hello"
        return mv
    }

    @GetMapping("/bye")
    fun bye(mv: ModelAndView, @SessionAttribute("user") user: PersonaInfo): ModelAndView {
        // call /hello to set user into sessionScope first or exception
        mv.viewName = "greeting/bye"
        return mv
    }
}