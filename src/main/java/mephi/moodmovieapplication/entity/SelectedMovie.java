/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.entity;
import jakarta.persistence.*;

/**
 *
 * @author alina
 */
@Entity
@Table(name = "selected_movies")
public class SelectedMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private MovieSelection selection;

    @Column(name = "tmdb_id")
    private Long tmdbId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String overview;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "poster_path")
    private String posterPath;

    @Column(name = "vote_average")
    private Double voteAverage;

    @Column(name = "genres_json", columnDefinition = "TEXT")
    private String genresJson;

    public Long getId() {
        return id;
    }

    public MovieSelection getSelection() {
        return selection;
    }

    public Long getTmdbId() {
        return tmdbId;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getGenresJson() {
        return genresJson;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSelection(MovieSelection selection) {
        this.selection = selection;
    }

    public void setTmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setGenresJson(String genresJson) {
        this.genresJson = genresJson;
    }
}
