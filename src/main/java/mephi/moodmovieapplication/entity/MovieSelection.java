/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.entity;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 *
 * @author alina
 */
@Entity
@Table(name = "movie_selections")
public class MovieSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Mood mood;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SelectionStatus status;

    @Column(name = "rules_json", columnDefinition = "TEXT")
    private String rulesJson;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(nullable = false)
    private boolean saved = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @OneToMany(mappedBy = "selection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SelectedMovie> movies = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Mood getMood() {
        return mood;
    }

    public SelectionStatus getStatus() {
        return status;
    }

    public String getRulesJson() {
        return rulesJson;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isSaved() {
        return saved;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public List<SelectedMovie> getMovies() {
        return movies;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public void setStatus(SelectionStatus status) {
        this.status = status;
    }

    public void setRulesJson(String rulesJson) {
        this.rulesJson = rulesJson;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public void setMovies(List<SelectedMovie> movies) {
        this.movies = movies;
    }
}
