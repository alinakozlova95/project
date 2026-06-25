/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.service;
import java.util.List;
import org.springframework.stereotype.Service;
import mephi.moodmovieapplication.dto.SelectionDtos.MovieCardDto;
import mephi.moodmovieapplication.dto.SelectionDtos.SelectionResponse;
import mephi.moodmovieapplication.entity.MovieSelection;
import mephi.moodmovieapplication.entity.SelectedMovie;

/**
 *
 * @author alina
 */
@Service
public class SelectionMapper {

    public SelectionResponse toResponse(MovieSelection selection) {
        List<MovieCardDto> movies = selection.getMovies()
                .stream()
                .map(this::toMovieCardDto)
                .toList();

        return new SelectionResponse(
                selection.getId(),
                selection.getMood(),
                selection.getMood().getTitle(),
                selection.getMood().getDescription(),
                selection.getStatus(),
                selection.getErrorMessage(),
                selection.isSaved(),
                selection.getCreatedAt(),
                selection.getCompletedAt(),
                movies
        );
    }

    private MovieCardDto toMovieCardDto(SelectedMovie movie) {
        return new MovieCardDto(
                movie.getTmdbId(),
                movie.getTitle(),
                movie.getOverview(),
                movie.getReleaseDate(),
                movie.getPosterPath(),
                movie.getVoteAverage()
        );
    }
}
