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
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author alina
 */

class RegistryServiceTest {

    @Test
    void shouldRegisterUser() {
        UserRepository repository = mock(UserRepository.class);
        RegistryService service = new RegistryService(repository);

        when(repository.existsByUsername("alina")).thenReturn(false);
        when(repository.existsByEmail("alina@test.com")).thenReturn(false);

        when(repository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        UserResponse response = service.register(
                new RegisterRequest("alina", "alina@test.com", "123456")
        );

        assertEquals(1L, response.id());
        assertEquals("alina", response.username());
        assertEquals("alina@test.com", response.email());
    }

    @Test
    void shouldNotRegisterUserWithShortPassword() {
        UserRepository repository = mock(UserRepository.class);
        RegistryService service = new RegistryService(repository);

        assertThrows(GeneralAppException.class, () ->
                service.register(new RegisterRequest("alina", "alina@test.com", "123"))
        );
    }

    @Test
    void shouldNotRegisterDuplicateUsername() {
        UserRepository repository = mock(UserRepository.class);
        RegistryService service = new RegistryService(repository);

        when(repository.existsByUsername("alina")).thenReturn(true);

        assertThrows(GeneralAppException.class, () ->
                service.register(new RegisterRequest("alina", "alina@test.com", "123456"))
        );
    }

@Test
void shouldLoginUserWithCorrectPassword() {
    UserRepository repository = mock(UserRepository.class);
    RegistryService service = new RegistryService(repository);

    when(repository.existsByUsername("alina")).thenReturn(false);
    when(repository.existsByEmail("alina@test.com")).thenReturn(false);

    final String[] passwordHash = new String[1];

    when(repository.save(any(User.class))).thenAnswer(invocation -> {
        User user = invocation.getArgument(0);
        user.setId(1L);
        passwordHash[0] = user.getPasswordHash();
        return user;
    });

    service.register(new RegisterRequest("alina", "alina@test.com", "123456"));

    User userForLogin = new User();
    userForLogin.setId(1L);
    userForLogin.setUsername("alina");
    userForLogin.setEmail("alina@test.com");
    userForLogin.setPasswordHash(passwordHash[0]);

    when(repository.findByUsername("alina")).thenReturn(Optional.of(userForLogin));

    UserResponse response = service.login(new LoginRequest("alina", "123456"));

    assertEquals("alina", response.username());
    assertEquals("alina@test.com", response.email());
}

    @Test
    void shouldRejectWrongPassword() {
        UserRepository repository = mock(UserRepository.class);
        RegistryService service = new RegistryService(repository);

        User user = new User();
        user.setId(1L);
        user.setUsername("alina");
        user.setEmail("alina@test.com");
        user.setPasswordHash("$2a$10$wrongwrongwrongwrongwrongwrongwrongwrongwrongwrongwro");

        when(repository.findByUsername("alina")).thenReturn(Optional.of(user));

        assertThrows(GeneralAppException.class, () -> service.login(new LoginRequest("alina", "badpassword"))
        );
    }
}