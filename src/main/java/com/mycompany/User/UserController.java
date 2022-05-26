package com.mycompany.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired private UserService service;
    @GetMapping("/users")
    public String showUserList(Model model){
        List<User> users =  service.listAll();
        model.addAttribute("listUsers",users);
        model.addAttribute("pageTitle","Add new User");
        return "users";
    }

    @GetMapping("/user/new")
    public String showNewForm(Model model){
        model.addAttribute("user", new User());
        return "user_form";
    }
    @PostMapping("/user/save")
    public String saveUser(User user, RedirectAttributes ra){
        service.save(user);
        ra.addFlashAttribute("message","The User has been saved successfully");
        return "redirect:/users";
    }
    @GetMapping("/user/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model,RedirectAttributes ra){
        try {
            User user = service.get(id);
            model.addAttribute("user",user);
            model.addAttribute("pageTitle","Edit User (ID: " + id+ ")");
            return "user_form";
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            ra.addFlashAttribute("message",e.getMessage());
            return "redirect:/users";
        }
    }
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id,RedirectAttributes ra){
        try {
            service.delete(id);
            ra.addFlashAttribute("message","The User Id " + id + "has been deleted");
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            ra.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/users";
    }
}
