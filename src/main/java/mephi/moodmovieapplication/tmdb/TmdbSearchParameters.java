/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.tmdb;

/**
 *
 * @author alina
 */
public class TmdbSearchParameters {

    private String genres;
    private Double minVoteAverage;
    private String sortBy;
    private String language;
    private Integer page;

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public Double getMinVoteAverage() {
        return minVoteAverage;
    }

    public void setMinVoteAverage(Double minVoteAverage) {
        this.minVoteAverage = minVoteAverage;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
