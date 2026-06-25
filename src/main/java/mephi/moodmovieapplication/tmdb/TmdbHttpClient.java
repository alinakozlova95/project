/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.tmdb;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mephi.moodmovieapplication.entity.ExternalApi;
import mephi.moodmovieapplication.exception.GeneralAppException;
import mephi.moodmovieapplication.repository.ExternalApiRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URI;
import java.net.InetSocketAddress;

/**
 *
 * @author alina
 */

@Component
public class TmdbHttpClient {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ExternalApiRepository externalApiRepository;

    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    public TmdbHttpClient(ExternalApiRepository externalApiRepository) {
        this.externalApiRepository = externalApiRepository;
    }

    public List<TmdbMovieDto> discoverMovies(TmdbSearchParameters parameters) {
        String url = buildDiscoverUrl(parameters);

        ExternalApi log = new ExternalApi();
        log.setEndpoint("/discover/movie");
        log.setRequestUrl(url);

        try {
            HttpURLConnection connection = (HttpURLConnection)
                    URI.create(url).toURL().openConnection(
                        new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10809))
                );

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);

            int status = connection.getResponseCode();
            log.setHttpStatus(status);

            InputStream stream = status >= 200 && status < 300
                    ? connection.getInputStream()
                    : connection.getErrorStream();

            String responseBody = readBody(stream);

            if (status >= 200 && status < 300) {
                log.setSuccess(true);
                externalApiRepository.save(log);
                return parseMovies(responseBody);
            }

            log.setSuccess(false);
            log.setErrorMessage(responseBody);
            externalApiRepository.save(log);

            if (status == 401) {
                throw new GeneralAppException("Ошибка авторизации TMDB: проверьте API key");
            }

            if (status == 429) {
                throw new GeneralAppException("Превышен лимит запросов к TMDB");
            }

            throw new GeneralAppException("Ошибка TMDB, HTTP статус: " + status + ", ответ: " + responseBody);

        } catch (GeneralAppException exception) {
            throw exception;
        } catch (Exception exception) {

            log.setSuccess(false);
            log.setErrorMessage(exception.getMessage());
            externalApiRepository.save(log);

            throw new GeneralAppException("TMDB временно недоступен: " + exception.getMessage());
        }
    }

    private String buildDiscoverUrl(TmdbSearchParameters parameters) {
        String baseUrl = "https://api.themoviedb.org/3/discover/movie";

        return baseUrl
                + "?api_key=" + encode(tmdbApiKey)
                + "&language=" + encode(parameters.getLanguage())
                + "&sort_by=" + encode(parameters.getSortBy())
                + "&with_genres=" + encode(parameters.getGenres())
                + "&vote_average.gte=" + parameters.getMinVoteAverage()
                + "&include_adult=false"
                + "&include_video=false"
                + "&page=" + parameters.getPage();
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private String readBody(InputStream stream) throws Exception {
        if (stream == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8))) {

            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }

        return result.toString();
    }

    private List<TmdbMovieDto> parseMovies(String json) throws Exception {
        JsonNode root = objectMapper.readTree(json);
        JsonNode results = root.get("results");

        List<TmdbMovieDto> movies = new ArrayList<>();

        if (results == null || !results.isArray()) {
            return movies;
        }

        int limit = Math.min(results.size(), 10);

        for (int i = 0; i < limit; i++) {
            JsonNode node = results.get(i);

            TmdbMovieDto movie = new TmdbMovieDto();
            movie.setTmdbId(node.path("id").asLong());
            movie.setTitle(node.path("title").asText());
            movie.setOverview(node.path("overview").asText());
            movie.setReleaseDate(node.path("release_date").asText());
            movie.setPosterPath(node.path("poster_path").asText(null));
            movie.setVoteAverage(node.path("vote_average").asDouble());

            movies.add(movie);
        }

        return movies;
    }

    public static class TmdbMovieDto {

        private Long tmdbId;
        private String title;
        private String overview;
        private String releaseDate;
        private String posterPath;
        private Double voteAverage;

        public Long getTmdbId() {
            return tmdbId;
        }

        public void setTmdbId(Long tmdbId) {
            this.tmdbId = tmdbId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }

        public void setVoteAverage(Double voteAverage) {
            this.voteAverage = voteAverage;
        }

        public Double getVoteAverage() {
            return voteAverage;
        }
    }
}