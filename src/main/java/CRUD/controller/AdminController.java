package CRUD.controller;

import CRUD.model.User;
import CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

//контроллер админа вывел в отдельный класс
@Controller
@RequestMapping("/admin/*")
public class AdminController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = {"","list"}, method = {RequestMethod.GET})
    public String listUser(ModelMap model) {
        List<User> listUser = userService.getAll();
        model.addAttribute("listUser", listUser);
        return "list";
    }
    //https://www.baeldung.com/spring-boot-crud-thymeleaf
    @RequestMapping(value = {"new"}, method = {RequestMethod.GET})
    public String showNewForm(Model model) {
        model.addAttribute("user",new User());
        return "formnew";
    }

    @RequestMapping(value = {"/insert"}, method = {RequestMethod.POST})
    public String insertUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "formnew";
        }
        userService.add(user);
        model.addAttribute("listUser", userService.getAll());
        return "redirect:/admin/";
    }
    @RequestMapping(value = {"/edit/update/{id}"}, method = {RequestMethod.POST})
    public String updateUser(@PathVariable("id") int id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "form";
        }

        userService.update(user);
        model.addAttribute("listUser", userService.getAll());
        return "redirect:/admin/";
    }
    @RequestMapping(value = {"/delete/{id}"}, method = {RequestMethod.GET})
    public String deleteUser(@PathVariable("id") int id, Model model) {
        User user = userService.getbyID(id);
        userService.delete(user);
        model.addAttribute("listUser", userService.getAll());
        return "redirect:/admin/";
    }

    @RequestMapping(value = {"/edit/{id}"}, method = {RequestMethod.GET})
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        User user = userService.getbyID(id);
        model.addAttribute("user", user);
        return "form";
    }

}
