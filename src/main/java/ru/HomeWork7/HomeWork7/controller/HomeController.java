package ru.HomeWork7.HomeWork7.controller;

import ru.HomeWork7.HomeWork7.service.AdminService;
import ru.HomeWork7.HomeWork7.service.HomeService;
import ru.HomeWork7.HomeWork7.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * HomeController - класс, реализующий взаимодействие пользователя с приложением.
 */
@Controller
public class HomeController {

    private final HomeService homeService;
    private final UserService userService;
    private final AdminService adminService;

    public HomeController(HomeService homeService, UserService userService, AdminService adminService) {
        this.homeService = homeService;
        this.userService = userService;
        this.adminService = adminService;
    }

    /**
     * Метод home получает информацию для отображения на главной странице и добавляет ее в модель
     * для передачи в представление "index"
     *
     * @param model - модель.
     * @return - главная страница.
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("text", homeService.getText());
        return "index";
    }

    /**
     * Метод index обрабатывает GET-запросы по адресу "/index" и перенаправляет на главную страницу ("/").
     *
     * @return - главная страница.
     */
    @GetMapping("/index")
    public String index() {
        return "redirect:/";
    }

    /**
     * Метод user получает информацию о пользователе и записывает ее в модель для отображения
     * на странице пользователя "user"
     *
     * @param model - модель.
     * @return - страница, соответствующая уровню доступа "user" (Public data).
     */
    @GetMapping("/user")
    public String user(Model model) {
        model.addAttribute("text", userService.getText());
        return "user";
    }

    /**
     * Метод admin получает информацию о пользователе и записывает ее в модель для отображения
     * на странице пользователя "admin"
     *
     * @param model - модель.
     * @return - страница, соответствующая уровню доступа "admin" (Public data).
     */
    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("text", adminService.getText());
        return "admin";
    }
}
