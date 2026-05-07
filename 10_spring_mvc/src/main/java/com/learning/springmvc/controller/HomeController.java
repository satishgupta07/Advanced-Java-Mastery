package com.learning.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Step 3: a controller method.
 *
 *   - @GetMapping("/")  : "I handle GET /"
 *   - return "home"     : view name; resolver maps to /WEB-INF/views/home.jsp
 *   - Model model       : Spring injects this so we can pass values to the view
 *
 * Compare to Phase 3:
 *   No @WebServlet. No HttpServlet subclass. No doGet override.
 *   Just a method whose return value tells Spring what to render.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to Spring MVC");
        return "home";
    }
}
