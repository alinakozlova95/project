/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.strategy;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import mephi.moodmovieapplication.entity.Mood;
import mephi.moodmovieapplication.exception.GeneralAppException;
import org.springframework.stereotype.Component;

/**
 *
 * @author alina
 */
@Component
public class MoodStrategyFactory {

    private final Map<Mood, MoodStrategy> strategies = new EnumMap<>(Mood.class);

    public MoodStrategyFactory(List<MoodStrategy> strategyList) {
        for (MoodStrategy strategy : strategyList) {
            strategies.put(strategy.getMood(), strategy);
        }
    }

    public MoodStrategy getStrategy(Mood mood) {
        MoodStrategy strategy = strategies.get(mood);

        if (strategy == null) {
            throw new GeneralAppException("Для выбранного настроения не найдены правила подбора");
        }

        return strategy;
    }
}
