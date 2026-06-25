/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.entity;

/**
 *
 * @author alina
 */
public enum Mood {
    HAPPY("Весёлое", "Лёгкие, добрые и смешные фильмы"),
    TENSE("Напряжённое", "Фильмы с интригой, напряжением и динамикой"),
    DARK("Мрачное", "Атмосферные, серьёзные или тёмные фильмы");

    private final String title;
    private final String description;

    Mood(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
