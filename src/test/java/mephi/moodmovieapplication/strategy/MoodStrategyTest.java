/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.strategy;
import mephi.moodmovieapplication.entity.Mood;
import mephi.moodmovieapplication.tmdb.TmdbSearchParameters;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author alina
 */

class MoodStrategyTest {

    @Test
    void happyMoodShouldCreateComedyFamilyParams() {
        TmdbSearchParameters p = new HappyMoodStrategy().createSearchParameters();

        assertEquals("35|16|10751", p.getGenres());
        assertEquals(6.0, p.getMinVoteAverage());
        assertEquals("popularity.desc", p.getSortBy());
        assertEquals("ru-RU", p.getLanguage());
        assertEquals(1, p.getPage());
    }

    @Test
    void tenseMoodShouldCreateThrillerCrimeParams() {
        TmdbSearchParameters p = new TenseMoodStrategy().createSearchParameters();

        assertEquals("53|80|9648", p.getGenres());
        assertEquals(6.2, p.getMinVoteAverage());
    }

    @Test
    void darkMoodShouldCreateDramaHorrorParams() {
        TmdbSearchParameters p = new DarkMoodStrategy().createSearchParameters();

        assertEquals("18|27|9648", p.getGenres());
        assertEquals(6.0, p.getMinVoteAverage());
    }

    @Test
    void factoryShouldReturnCorrectStrategy() {
        MoodStrategyFactory factory = new MoodStrategyFactory(List.of(
                new HappyMoodStrategy(),
                new TenseMoodStrategy(),
                new DarkMoodStrategy()
        ));

        assertEquals(Mood.HAPPY, factory.getStrategy(Mood.HAPPY).getMood());
        assertEquals(Mood.TENSE, factory.getStrategy(Mood.TENSE).getMood());
        assertEquals(Mood.DARK, factory.getStrategy(Mood.DARK).getMood());
    }
}