/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.tmdb;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author alina
 */

class TmdbHttpClientParsingTest {

    @Test
    void shouldParseMoviesFromTmdbJson() throws Exception {
        String json = """
                {
                  "page": 1,
                  "results": [
                    {
                      "id": 862,
                      "title": "История игрушек",
                      "overview": "Описание фильма",
                      "release_date": "1995-11-22",
                      "poster_path": "/poster.jpg",
                      "vote_average": 7.9
                    }
                  ]
                }
                """;

        TmdbHttpClient client = new TmdbHttpClient(null);

        List<TmdbHttpClient.TmdbMovieDto> movies = client.parseMovies(json);

        assertEquals(1, movies.size());
        assertEquals(862L, movies.get(0).getTmdbId());
        assertEquals("История игрушек", movies.get(0).getTitle());
        assertEquals(7.9, movies.get(0).getVoteAverage());
    }

    @Test
    void shouldReturnEmptyListWhenResultsMissing() throws Exception {
        String json = """
                {
                  "page": 1
                }
                """;

        TmdbHttpClient client = new TmdbHttpClient(null);

        List<TmdbHttpClient.TmdbMovieDto> movies = client.parseMovies(json);

        assertTrue(movies.isEmpty());
    }

    @Test
    void shouldLimitMoviesToTenItems() throws Exception {
        StringBuilder json = new StringBuilder("""
                {
                  "results": [
                """);

        for (int i = 1; i <= 12; i++) {
            json.append("""
                    {
                      "id": %d,
                      "title": "Movie %d",
                      "overview": "",
                      "release_date": "2020-01-01",
                      "poster_path": null,
                      "vote_average": 7.0
                    }
                    """.formatted(i, i));

            if (i < 12) {
                json.append(",");
            }
        }

        json.append("""
                  ]
                }
                """);

        TmdbHttpClient client = new TmdbHttpClient(null);

        List<TmdbHttpClient.TmdbMovieDto> movies = client.parseMovies(json.toString());

        assertEquals(10, movies.size());
        assertEquals(10L, movies.get(9).getTmdbId());
    }
}