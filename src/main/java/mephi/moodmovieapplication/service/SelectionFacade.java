/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.moodmovieapplication.service;
import java.util.List;
import org.springframework.stereotype.Service;
import mephi.moodmovieapplication.dto.SelectionDtos.CreateSelectionRequest;
import mephi.moodmovieapplication.dto.SelectionDtos.SelectionResponse;
import mephi.moodmovieapplication.entity.MovieSelection;
import mephi.moodmovieapplication.entity.SelectedMovie;
import mephi.moodmovieapplication.entity.SelectionStatus;
import mephi.moodmovieapplication.entity.User;
import mephi.moodmovieapplication.exception.GeneralAppException;
import mephi.moodmovieapplication.repository.MovieSelectionRepository;
import mephi.moodmovieapplication.strategy.MoodStrategy;
import mephi.moodmovieapplication.strategy.MoodStrategyFactory;
import mephi.moodmovieapplication.tmdb.MovieDataProvider;
import mephi.moodmovieapplication.tmdb.TmdbSearchParameters;
import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author alina
 */
@Service
public class SelectionFacade {

    private final MovieSelectionRepository movieSelectionRepository;
    private final RegistryService registryService;
    private final MoodStrategyFactory moodStrategyFactory;
    private final MovieDataProvider movieDataProvider;
    private final SelectionMapper selectionMapper;
    private final JsonService jsonService;

    public SelectionFacade(MovieSelectionRepository movieSelectionRepository,
                           RegistryService registryService,
                           MoodStrategyFactory moodStrategyFactory,
                           MovieDataProvider movieDataProvider,
                           SelectionMapper selectionMapper,
                           JsonService jsonService) {
        this.movieSelectionRepository = movieSelectionRepository;
        this.registryService = registryService;
        this.moodStrategyFactory = moodStrategyFactory;
        this.movieDataProvider = movieDataProvider;
        this.selectionMapper = selectionMapper;
        this.jsonService = jsonService;
    }

    @Transactional
    public SelectionResponse createSelection(CreateSelectionRequest request, Long userId) {
        if (request.mood() == null) {
            throw new GeneralAppException("Не выбрано настроение");
        }

        User user = registryService.getUserById(userId);

        MovieSelection selection = new MovieSelection();
        selection.setUser(user);
        selection.setMood(request.mood());
        selection.setStatus(SelectionStatus.RUNNING);

        selection = movieSelectionRepository.save(selection);

        try {
            MoodStrategy strategy = moodStrategyFactory.getStrategy(request.mood());
            TmdbSearchParameters parameters = strategy.createSearchParameters();

            selection.setRulesJson(jsonService.toJson(parameters));

            List<SelectedMovie> movies = movieDataProvider.findMovies(parameters);

            for (SelectedMovie movie : movies) {
                movie.setSelection(selection);
                selection.getMovies().add(movie);
            }

            selection.setStatus(SelectionStatus.COMPLETED);
            selection.setCompletedAt(LocalDateTime.now());

        } catch (Exception exception) {
            selection.setStatus(SelectionStatus.ERROR);
            selection.setErrorMessage(exception.getMessage());
            selection.setCompletedAt(LocalDateTime.now());
        }

        MovieSelection savedSelection = movieSelectionRepository.save(selection);
        return selectionMapper.toResponse(savedSelection);
    }

    @Transactional(readOnly = true)
    public List<SelectionResponse> getHistory(Long userId) {
        User user = registryService.getUserById(userId);

        return movieSelectionRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(selectionMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SelectionResponse getSelection(Long selectionId, Long userId) {
        MovieSelection selection = findUserSelection(selectionId, userId);
        return selectionMapper.toResponse(selection);
    }

    @Transactional
    public SelectionResponse saveSelection(Long selectionId, Long userId) {
        MovieSelection selection = findUserSelection(selectionId, userId);
        selection.setSaved(true);

        MovieSelection savedSelection = movieSelectionRepository.save(selection);
        return selectionMapper.toResponse(savedSelection);
    }

    @Transactional
    public void deleteSelection(Long selectionId, Long userId) {
        MovieSelection selection = findUserSelection(selectionId, userId);
        movieSelectionRepository.delete(selection);
    }

    private MovieSelection findUserSelection(Long selectionId, Long userId) {
        MovieSelection selection = movieSelectionRepository.findByIdWithMovies(selectionId)
                .orElseThrow(() -> new GeneralAppException("Подборка не найдена"));

        if (!selection.getUser().getId().equals(userId)) {
            throw new GeneralAppException("Нет доступа к этой подборке");
        }

        return selection;
    }
}
