/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.tmdb;
import java.util.List;
import mephi.moodmovieapplication.entity.SelectedMovie;

/**
 *
 * @author alina
 */
public interface MovieDataProvider {

    List<SelectedMovie> findMovies(TmdbSearchParameters parameters);
}
