package com.dh.movie.controller;

import com.dh.movie.event.MovieSender;
import com.dh.movie.model.Movie;
import com.dh.movie.repository.MovieRepository;
import com.dh.movie.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {


    private final MovieService movieService;

    private final MovieRepository movieRepository;
    private final MovieSender movieSender;

    public MovieController(MovieService movieService, MovieRepository repository, MovieRepository movieRepository, MovieSender movieSender) {
        this.movieService = movieService;
        this.movieRepository = movieRepository;
        this.movieSender = movieSender;
    }




    @GetMapping("/{genre}")
    ResponseEntity<List<Movie>> getMovieByGenre(@PathVariable String genre) {
        return ResponseEntity.ok().body(movieService.findByGenre(genre));
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
        createMovie(movie);
        return ResponseEntity.ok().body(movieService.save(movie));
    }


    public void createMovie(Movie movie) {
        movieSender.movieSender(movie);
        movieRepository.save(movie);
    }
}
