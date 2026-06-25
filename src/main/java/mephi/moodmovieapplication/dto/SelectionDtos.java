/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.dto;
import java.util.List;
import java.time.LocalDateTime;
import mephi.moodmovieapplication.entity.Mood;
import mephi.moodmovieapplication.entity.SelectionStatus;

/**
 *
 * @author alina
 */
public class SelectionDtos {

    public record CreateSelectionRequest(
            Mood mood
    ) {
    }

    public record MovieCardDto(
            Long tmdbId,
            String title,
            String overview,
            String releaseDate,
            String posterPath,
            Double voteAverage
    ) {
    }

    public record SelectionResponse(
            Long id,
            Mood mood,
            String moodTitle,
            String moodDescription,
            SelectionStatus status,
            String errorMessage,
            boolean saved,
            LocalDateTime createdAt,
            LocalDateTime completedAt,
            List<MovieCardDto> movies
    ) {
    }

    public record MoodInfoDto(
            Mood mood,
            String title,
            String description
    ) {
    }
}
