/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.strategy;
import mephi.moodmovieapplication.entity.Mood;
import mephi.moodmovieapplication.tmdb.TmdbSearchParameters;
import org.springframework.stereotype.Component;

/**
 *
 * @author alina
 */
@Component
public class TenseMoodStrategy implements MoodStrategy {

    @Override
    public Mood getMood() {
        return Mood.TENSE;
    }

    @Override
    public TmdbSearchParameters createSearchParameters() {
        TmdbSearchParameters parameters = new TmdbSearchParameters();

        parameters.setGenres("53|80|9648");
        parameters.setMinVoteAverage(6.2);
        parameters.setSortBy("popularity.desc");
        parameters.setLanguage("ru-RU");
        parameters.setPage(1);

        return parameters;
    }

    @Override
    public String getRulesDescription() {
        return "Для напряжённого настроения используются триллеры, криминальные фильмы и детективы с оценкой от 6.2.";
    }
}
