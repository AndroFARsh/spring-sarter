package com.farshonok.spring.http.controllers

import jakarta.servlet.http.HttpServletRequest
import jakarta.websocket.server.PathParam
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/api/v1")
class GreetingController {

    @RequestMapping("/hello/{id}", method = [RequestMethod.GET])
    fun hello(request: HttpServletRequest,
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
    fun bye(mv: ModelAndView): ModelAndView {
        mv.viewName = "greeting/bye"
        return mv
    }
}