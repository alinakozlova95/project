/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.repository;
import java.util.List;
import mephi.moodmovieapplication.entity.ExternalApi;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alina
 */
public interface ExternalApiRepository extends JpaRepository<ExternalApi, Long> {

    List<ExternalApi> findBySelectionId(Long selectionId);
}
