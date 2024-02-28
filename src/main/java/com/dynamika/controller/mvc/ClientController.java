package com.dynamika.controller.mvc;

import com.dynamika.dto.UserDto;
import com.dynamika.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final UserService userService;

    @GetMapping
    public String getAllClients(Model model) {
        List<UserDto> clients = userService.findAll().join();
        model.addAttribute("clients", clients);
        return "clients/list";
    }

    @GetMapping("/add")
    public String showAddClientForm(Model model) {
        model.addAttribute("client", new UserDto());
        return "clients/add";
    }

    @PostMapping("/add")
    public String addClient(@ModelAttribute("client") UserDto client) {
        userService.create(client);
        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    public String showEditClientForm(@PathVariable Long id, Model model) {
        UserDto client = userService.findById(id).join().orElseThrow(() -> new RuntimeException("Client not found"));
        model.addAttribute("client", client);
        return "clients/edit";
    }

    @PostMapping("/edit/{id}")
    public String editClient(@PathVariable Long id, @ModelAttribute("client") UserDto client) {
        userService.update(id, client);
        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/clients";
    }
}
