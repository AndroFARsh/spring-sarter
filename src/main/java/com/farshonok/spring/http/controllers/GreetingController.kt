package com.farshonok.spring.http.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/api/v1")
class GreetingController {

    @RequestMapping("/hello", method = [RequestMethod.GET])
    fun hello() : ModelAndView {
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