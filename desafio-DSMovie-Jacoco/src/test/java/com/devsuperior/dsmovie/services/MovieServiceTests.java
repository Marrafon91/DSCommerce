package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.services.exceptions.DatabaseException;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dsmovie.tests.MovieFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class MovieServiceTests {
	
	@InjectMocks
	private MovieService service;

	@Mock
	private MovieRepository repository;

	private long existingMovieId, nonExistingMovieId, dependentMovieId;
	private MovieEntity movieEntity;
	private MovieDTO movieDTO;
	private String title;

	@BeforeEach
	public  void setUp() {
		existingMovieId = 1L;
		nonExistingMovieId = 2L;
		dependentMovieId = 3L;

		title = "Test Movie";

		movieEntity = MovieFactory.createMovieEntity();
		PageImpl<MovieEntity> page = new PageImpl<>(List.of(movieEntity));

		movieDTO = new MovieDTO(movieEntity);

		Mockito.when(repository.findById(existingMovieId)).thenReturn(Optional.of(movieEntity));
		Mockito.when(repository.findById(nonExistingMovieId)).thenReturn(Optional.empty());

		Mockito.when(repository.searchByTitle(any(), any())).thenReturn(page);

		Mockito.when(repository.save(any())).thenReturn(movieEntity);

		Mockito.when(repository.getReferenceById(existingMovieId)).thenReturn(movieEntity);
		Mockito.when(repository.getReferenceById(nonExistingMovieId)).thenThrow(EntityNotFoundException.class);

		Mockito.when(repository.existsById(existingMovieId)).thenReturn(true);
		Mockito.when(repository.existsById(dependentMovieId)).thenReturn(true);
		Mockito.when(repository.existsById(nonExistingMovieId)).thenReturn(false);

		Mockito.doNothing().when(repository).deleteById(existingMovieId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentMovieId);
	}

	@Test
	public void findAllShouldReturnPagedMovieDTO() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<MovieDTO> page = service.findAll(title, pageable);

		Assertions.assertNotNull(page);
		Assertions.assertFalse(page.isEmpty());
		Assertions.assertEquals(1, page.getTotalElements());
		Assertions.assertEquals(page.iterator().next().getTitle(), title);
	}
	
	@Test
	public void findByIdShouldReturnMovieDTOWhenIdExists() {
		MovieDTO result = service.findById(existingMovieId);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(existingMovieId, result.getId());
		Assertions.assertEquals(title, result.getTitle());
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class,
				() ->  service.findById(nonExistingMovieId)
		);
	}
	
	@Test
	public void insertShouldReturnMovieDTO() {
		MovieDTO result = service.insert(movieDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(movieDTO.getId(), result.getId());
		Assertions.assertEquals(movieDTO.getTitle(), result.getTitle());
	}
	
	@Test
	public void updateShouldReturnMovieDTOWhenIdExists() {
		MovieDTO result = service.update(existingMovieId, movieDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(movieDTO.getId(), result.getId());
		Assertions.assertEquals(movieDTO.getTitle(), result.getTitle());
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class,
				() ->  service.update(nonExistingMovieId, movieDTO)
		);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> service.delete(existingMovieId));
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class,
				() ->  service.delete(nonExistingMovieId)
		);
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
		Assertions.assertThrows(DatabaseException.class,
				() ->  service.delete(dependentMovieId)
		);
	}
}
