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
public class HappyMoodStrategy implements MoodStrategy {

    @Override
    public Mood getMood() {
        return Mood.HAPPY;
    }

    @Override
    public TmdbSearchParameters createSearchParameters() {
        TmdbSearchParameters parameters = new TmdbSearchParameters();

        parameters.setGenres("35|16|10751");
        parameters.setMinVoteAverage(6.0);
        parameters.setSortBy("popularity.desc");
        parameters.setLanguage("ru-RU");
        parameters.setPage(1);

        return parameters;
    }

    @Override
    public String getRulesDescription() {
        return "Для весёлого настроения подбираются популярные комедии, анимация и семейные фильмы с оценкой от 6.0.";
    }
}
