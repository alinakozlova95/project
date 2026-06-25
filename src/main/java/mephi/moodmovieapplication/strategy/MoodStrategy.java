/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.strategy;
import mephi.moodmovieapplication.entity.Mood;
import mephi.moodmovieapplication.tmdb.TmdbSearchParameters;

/**
 *
 * @author alina
 */
public interface MoodStrategy {

    Mood getMood();

    TmdbSearchParameters createSearchParameters();

    String getRulesDescription();
}