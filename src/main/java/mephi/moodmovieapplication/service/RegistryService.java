/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.service;
import mephi.moodmovieapplication.dto.RegistryDtos.LoginRequest;
import mephi.moodmovieapplication.dto.RegistryDtos.RegisterRequest;
import mephi.moodmovieapplication.dto.RegistryDtos.UserResponse;
import mephi.moodmovieapplication.entity.User;
import mephi.moodmovieapplication.exception.GeneralAppException;
import mephi.moodmovieapplication.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author alina
 */
@Service
public class RegistryService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegistryService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserResponse register(RegisterRequest request) {
        if (request.username() == null || request.username().isBlank()) {
            throw new GeneralAppException("Имя пользователя не может быть пустым");
        }

        if (request.email() == null || request.email().isBlank()) {
            throw new GeneralAppException("Email не может быть пустым");
        }

        if (request.password() == null || request.password().length() < 6) {
            throw new GeneralAppException("Пароль должен содержать минимум 6 символов");
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new GeneralAppException("Пользователь с таким именем уже существует");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new GeneralAppException("Пользователь с таким email уже существует");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        User savedUser = userRepository.save(user);

        return toUserResponse(savedUser);
    }

    public UserResponse login(LoginRequest request) {
        if (request.username() == null || request.username().isBlank()) {
            throw new GeneralAppException("Введите имя пользователя");
        }

        if (request.password() == null || request.password().isBlank()) {
            throw new GeneralAppException("Введите пароль");
        }

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new GeneralAppException("Пользователь не найден"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new GeneralAppException("Неверный пароль");
        }

        return toUserResponse(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralAppException("Пользователь не найден"));
    }

    public UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
