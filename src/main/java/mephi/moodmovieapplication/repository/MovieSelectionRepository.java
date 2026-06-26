/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.repository;
import java.util.List;
import mephi.moodmovieapplication.entity.User;
import mephi.moodmovieapplication.entity.MovieSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author alina
 */
public interface MovieSelectionRepository extends JpaRepository<MovieSelection, Long> {

    List<MovieSelection> findByUserOrderByCreatedAtDesc(User user);
    
    @Query("""
       select s from MovieSelection s
       left join fetch s.movies
       left join fetch s.user
       where s.id = :id
       """)
    Optional<MovieSelection> findByIdWithMovies(@Param("id") Long id);
}
