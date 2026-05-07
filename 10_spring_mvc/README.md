# Phase 10 — Spring MVC

> *Spring MVC is one Servlet — `DispatcherServlet` — that delegates every request to the right `@Controller` method. That's the whole architecture.*

In Phase 3 you wrote a `@WebServlet("/users")` for each URL. In Phase 4 you upgraded to Model 2 by forwarding to a JSP. Spring MVC formalises the same pattern and removes the `@WebServlet`-per-URL boilerplate.

---

## The Front Controller pattern

```
                   ┌──────────────────── DispatcherServlet ──────────────────┐
   request ──────► │ HandlerMapping     → which @Controller method?          │
                   │ HandlerAdapter     → invoke it, build a ModelAndView    │
                   │ ViewResolver       → resolve "users" → /WEB-INF/views/users.jsp │
                   │ View renderer      → write HTML to response             │
                   └────────────────────────────────────────────────────────┘
```

ONE Servlet, registered at `/`, intercepts every request. It reads the URL, finds the `@RequestMapping` that matches, calls your method, takes whatever you return, and renders it through a `View`.

You stop writing Servlets. You write `@Controller` methods.

---

## Steps in this phase

| # | File | Topic |
|---|------|-------|
| 1 | [config/AppInitializer.java](src/main/java/com/learning/springmvc/config/AppInitializer.java) | Replaces `web.xml` — registers `DispatcherServlet` programmatically |
| 2 | [config/WebConfig.java](src/main/java/com/learning/springmvc/config/WebConfig.java) | `@EnableWebMvc` + `InternalResourceViewResolver` |
| 3 | [controller/HomeController.java](src/main/java/com/learning/springmvc/controller/HomeController.java) | `@GetMapping("/")` — returns a view name |
| 4 | [controller/UserController.java](src/main/java/com/learning/springmvc/controller/UserController.java) | Model attributes + JSP view (Model 2 done right) |
| 5 | [controller/UserRestController.java](src/main/java/com/learning/springmvc/controller/UserRestController.java) | `@RestController` — returns JSON, no view |
| - | [WEB-INF/views/users.jsp](src/main/webapp/WEB-INF/views/users.jsp) | The view, driven by `${users}` from the controller |

---

## `@Controller` vs `@RestController`

| Annotation | Returns | Used for |
|------------|---------|----------|
| `@Controller`     | A *view name* — Spring resolves it and renders HTML | Server-side rendering (JSP, Thymeleaf) |
| `@RestController` | The *return value itself*, serialized to JSON | REST APIs |

`@RestController` is shorthand for `@Controller + @ResponseBody on every method`. Use it for APIs.

---

## The annotation cheat sheet

```java
@GetMapping("/users")              // GET /users
@PostMapping("/users")             // POST /users
@PutMapping("/users/{id}")         // PUT /users/42
@DeleteMapping("/users/{id}")      // DELETE /users/42

@PathVariable Long id              // pulls {id} from the URL
@RequestParam String q             // pulls ?q=... from the query string
@RequestBody User u                // deserializes JSON request body to User
@ModelAttribute User u             // binds form fields to a User object

Model model                        // pass values to the view: model.addAttribute("users", list)
```

---

## How to run

```powershell
mvn clean package
# Drop target/spring-mvc-1.0.0.war into Tomcat 10's webapps/, then visit:
#   http://localhost:8080/spring-mvc/             — home page
#   http://localhost:8080/spring-mvc/users        — JSP-rendered table
#   http://localhost:8080/spring-mvc/api/users    — JSON
```

---

> Prerequisite: complete [../07_spring_framework/](../07_spring_framework/) and [../04_jsp/](../04_jsp/).
> Next phase: [../11_spring_boot/](../11_spring_boot/)
