/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.repository;
import java.util.List;
import mephi.moodmovieapplication.entity.User;
import mephi.moodmovieapplication.entity.MovieSelection;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alina
 */
public interface MovieSelectionRepository extends JpaRepository<MovieSelection, Long> {

    List<MovieSelection> findByUserOrderByCreatedAtDesc(User user);
}
