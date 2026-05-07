package com.learning.springmvc.controller;

import com.learning.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * Step 4: full Model 2 MVC, the Spring way.
 *
 *   GET  /users         → render the list
 *   POST /users         → create a user, then redirect back to /users
 *
 * Notes:
 *
 *   model.addAttribute("users", ...)
 *      Becomes ${users} inside the JSP — exactly the Phase 4 pattern,
 *      driven by Spring instead of by a hand-written Servlet.
 *
 *   "redirect:/users"
 *      Tells Spring to send a 302 instead of rendering a view. Why?
 *      The Post-Redirect-Get pattern: after a POST, redirect so the
 *      browser's URL bar shows GET /users. Refresh won't re-submit the form.
 */
@Controller
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", service.findAll());
        return "users";                                       // → /WEB-INF/views/users.jsp
    }

    @PostMapping("/users")
    public String create(@RequestParam String name,
                         @RequestParam String email) {
        service.register(name, email);
        return "redirect:/users";                             // PRG pattern
    }
}
