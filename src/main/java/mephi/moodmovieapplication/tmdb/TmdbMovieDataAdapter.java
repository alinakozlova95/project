/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.tmdb;
import java.util.ArrayList;
import java.util.List;
import mephi.moodmovieapplication.entity.SelectedMovie;
import org.springframework.stereotype.Component;

/**
 *
 * @author alina
 */
@Component
public class TmdbMovieDataAdapter implements MovieDataProvider {

    private final TmdbHttpClient tmdbHttpClient;

    public TmdbMovieDataAdapter(TmdbHttpClient tmdbHttpClient) {
        this.tmdbHttpClient = tmdbHttpClient;
    }

    @Override
    public List<SelectedMovie> findMovies(TmdbSearchParameters parameters) {
        List<TmdbHttpClient.TmdbMovieDto> tmdbMovies =
                tmdbHttpClient.discoverMovies(parameters);

        List<SelectedMovie> result = new ArrayList<>();

        for (TmdbHttpClient.TmdbMovieDto tmdbMovie : tmdbMovies) {
            SelectedMovie movie = new SelectedMovie();

            movie.setTmdbId(tmdbMovie.getTmdbId());
            movie.setTitle(tmdbMovie.getTitle());
            movie.setOverview(tmdbMovie.getOverview());
            movie.setReleaseDate(tmdbMovie.getReleaseDate());
            movie.setPosterPath(tmdbMovie.getPosterPath());
            movie.setVoteAverage(tmdbMovie.getVoteAverage());

            result.add(movie);
        }

        return result;
    }
}