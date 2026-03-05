package com.devsuperior.dsmovie.tests;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.entities.ScoreEntity;

public class MovieFactory {

	public static MovieEntity createMovieEntity() {

		MovieEntity movie = new MovieEntity(
				1L,
				"Test Movie",
				4.0,
				1,
				"https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jBJWaqoSCiARWtfV0GlqHrcdidd.jpg"
		);

		ScoreEntity score = new ScoreEntity();
		score.setValue(2.0);
		score.setMovie(movie);

		movie.getScores().add(score);

		return movie;
	}

	public static MovieDTO createMovieDTO() {
		MovieEntity movie = createMovieEntity();
		return new MovieDTO(movie);
	}
}