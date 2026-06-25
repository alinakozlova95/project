/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.service;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mephi.moodmovieapplication.exception.GeneralAppException;

/**
 *
 * @author alina
 */
@Service
public class JsonService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            throw new GeneralAppException("Ошибка преобразования данных в JSON");
        }
    }
}
