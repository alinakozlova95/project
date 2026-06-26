/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.mapper;
import mephi.moodmovieapplication.dto.SelectionDtos.SelectionResponse;
import mephi.moodmovieapplication.entity.*;
import mephi.moodmovieapplication.service.SelectionMapper;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author alina
 */

class SelectionMapperTest {

    private final SelectionMapper mapper = new SelectionMapper();

    @Test
    void shouldMapCompletedSelectionToResponse() {
        MovieSelection selection = new MovieSelection();
        selection.setId(1L);
        selection.setMood(Mood.HAPPY);
        selection.setStatus(SelectionStatus.COMPLETED);
        selection.setSaved(false);
        selection.setCreatedAt(LocalDateTime.now());
        selection.setCompletedAt(LocalDateTime.now());

        SelectionResponse response = mapper.toResponse(selection);

        assertEquals(1L, response.id());
        assertEquals(Mood.HAPPY, response.mood());
        assertEquals("Весёлое", response.moodTitle());
        assertEquals(SelectionStatus.COMPLETED, response.status());
    }

    @Test
    void shouldMapMoviesToMovieCards() {
        MovieSelection selection = new MovieSelection();
        selection.setId(2L);
        selection.setMood(Mood.HAPPY);
        selection.setStatus(SelectionStatus.COMPLETED);

        SelectedMovie movie = new SelectedMovie();
        movie.setTmdbId(862L);
        movie.setTitle("История игрушек");
        movie.setOverview("Описание");
        movie.setReleaseDate("1995-11-22");
        movie.setPosterPath("/poster.jpg");
        movie.setVoteAverage(7.9);

        selection.getMovies().add(movie);

        SelectionResponse response = mapper.toResponse(selection);

        assertEquals(1, response.movies().size());
        assertEquals("История игрушек", response.movies().get(0).title());
        assertEquals(862L, response.movies().get(0).tmdbId());
    }

    @Test
    void shouldMapErrorSelectionWithErrorMessage() {
        MovieSelection selection = new MovieSelection();
        selection.setId(3L);
        selection.setMood(Mood.DARK);
        selection.setStatus(SelectionStatus.ERROR);
        selection.setErrorMessage("TMDB недоступен");

        SelectionResponse response = mapper.toResponse(selection);

        assertEquals(SelectionStatus.ERROR, response.status());
        assertEquals("TMDB недоступен", response.errorMessage());
    }
}