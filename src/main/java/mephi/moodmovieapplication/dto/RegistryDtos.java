/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.dto;

/**
 *
 * @author alina
 */
public class RegistryDtos {

    public record RegisterRequest(
            String username,
            String email,
            String password
    ) {
    }

    public record LoginRequest(
            String username,
            String password
    ) {
    }

    public record UserResponse(
            Long id,
            String username,
            String email
    ) {
    }
}
