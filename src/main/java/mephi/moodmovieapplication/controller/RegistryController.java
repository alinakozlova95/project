/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.controller;
import jakarta.servlet.http.HttpSession;
import mephi.moodmovieapplication.dto.RegistryDtos.LoginRequest;
import mephi.moodmovieapplication.dto.RegistryDtos.RegisterRequest;
import mephi.moodmovieapplication.dto.RegistryDtos.UserResponse;
import mephi.moodmovieapplication.service.RegistryService;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author alina
 */
@RestController
@RequestMapping("/api/registry")
public class RegistryController {

    private final RegistryService registryService;

    public RegistryController(RegistryService registryService) {
        this.registryService = registryService;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request, HttpSession session) {
        UserResponse user = registryService.register(request);
        session.setAttribute("userId", user.id());
        return user;
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest request, HttpSession session) {
        UserResponse user = registryService.login(request);
        session.setAttribute("userId", user.id());
        return user;
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @GetMapping("/me")
    public UserResponse currentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return null;
        }

        return registryService.toUserResponse(registryService.getUserById(userId));
    }
}
