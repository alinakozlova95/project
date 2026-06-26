/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.controller;
import java.util.List;
import jakarta.servlet.http.HttpSession;
import mephi.moodmovieapplication.dto.SelectionDtos.CreateSelectionRequest;
import mephi.moodmovieapplication.dto.SelectionDtos.SelectionResponse;
import mephi.moodmovieapplication.exception.GeneralAppException;
import mephi.moodmovieapplication.service.SelectionFacade;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author alina
 */
@RestController
@RequestMapping("/api/selections")
public class SelectionController {

    private final SelectionFacade selectionFacade;

    public SelectionController(SelectionFacade selectionFacade) {
        this.selectionFacade = selectionFacade;
    }

    @PostMapping
    public SelectionResponse createSelection(@RequestBody CreateSelectionRequest request,
                                             HttpSession session) {
        Long userId = requireUserId(session);
        return selectionFacade.createSelection(request, userId);
    }

    @GetMapping
    public List<SelectionResponse> getHistory(HttpSession session) {
        Long userId = requireUserId(session);
        return selectionFacade.getHistory(userId);
    }

    @GetMapping("/{id}")
    public SelectionResponse getSelection(@PathVariable("id") Long id,
                                          HttpSession session) {
        Long userId = requireUserId(session);
        return selectionFacade.getSelection(id, userId);
    }

    @PostMapping("/{id}/save")
    public SelectionResponse saveSelection(@PathVariable("id") Long id,
                                           HttpSession session) {
        Long userId = requireUserId(session);
        return selectionFacade.saveSelection(id, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteSelection(@PathVariable("id") Long id,
                                HttpSession session) {
        Long userId = requireUserId(session);
        selectionFacade.deleteSelection(id, userId);
    }

    private Long requireUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            throw new GeneralAppException("Пользователь не авторизован");
        }

        return userId;
    }
}
