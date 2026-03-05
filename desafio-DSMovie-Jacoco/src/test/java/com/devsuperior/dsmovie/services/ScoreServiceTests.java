package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.dto.ScoreDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.entities.ScoreEntity;
import com.devsuperior.dsmovie.entities.UserEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dsmovie.tests.MovieFactory;
import com.devsuperior.dsmovie.tests.UserFactory;
import com.devsuperior.dsmovie.utils.CustomUserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class ScoreServiceTests {
	
	@InjectMocks
	private ScoreService service;

	@Mock
	private ScoreRepository scoreRepository;

	@Mock
	private MovieRepository movieRepository;

	@Mock
	private UserService userService;


	private long existingMovieId, nonExistingMovieId;
	private String existingUsername;
	private MovieEntity movieEntity;
	private MovieDTO movieDTO;
	private ScoreEntity scoreEntity;
	private UserEntity userEntity;

    @BeforeEach
	void setUp() {
		 existingMovieId = 1L;
		 nonExistingMovieId = 2L;

         existingUsername = "maria@gmail.com";

        movieEntity = MovieFactory.createMovieEntity();
        movieDTO = new MovieDTO(movieEntity);

		userEntity = UserFactory.createUserEntity();

		Mockito.when(userService.authenticated()).thenReturn(userEntity);

		Mockito.when(movieRepository.findById(existingMovieId)).thenReturn(Optional.of(movieEntity));
		Mockito.when(movieRepository.findById(nonExistingMovieId)).thenReturn(Optional.empty());
		Mockito.when(movieRepository.save(any())).thenReturn(movieEntity);

		Mockito.when(scoreRepository.saveAndFlush(any())).thenReturn(new ScoreEntity());
	}

	@Test
	public void saveScoreShouldReturnMovieDTO() {

		ScoreEntity s1 = new ScoreEntity();
		s1.setValue(4.0);
		s1.setMovie(movieEntity);
		s1.setUser(userEntity);

		ScoreEntity s2 = new ScoreEntity();
		s2.setValue(2.0);
		s2.setMovie(movieEntity);
		s2.setUser(userEntity);

		movieEntity.getScores().add(s1);
		movieEntity.getScores().add(s2);

		ScoreDTO dto = new ScoreDTO();
		dto.setMovieId(existingMovieId);
		dto.setScore(3.0);

		MovieDTO result = service.saveScore(dto);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(3.0, result.getScore());
		Assertions.assertEquals(2, result.getCount());

		Mockito.verify(scoreRepository).saveAndFlush(any());
		Mockito.verify(movieRepository).save(any());
	}
	
	@Test
	public void saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId() {
		ScoreDTO dto = new ScoreDTO();
		dto.setMovieId(nonExistingMovieId);
		dto.setScore(4.0);

		Assertions.assertThrows(ResourceNotFoundException.class, () -> service.saveScore(dto));
	}
}
