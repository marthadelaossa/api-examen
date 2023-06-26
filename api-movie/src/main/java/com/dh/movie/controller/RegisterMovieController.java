package com.dh.movie.controller;

import com.dh.movie.service.MovieService;
import com.dh.movie.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class RegisterMovieController {

    @Autowired
    private MovieService service;

    @PostMapping("/save/local")
    public ResponseEntity<?> saveSerie(@RequestBody Movie movie) {
        service.save(movie);
        return ResponseEntity.ok("Pelicula creada exitosamente");
    }
}
