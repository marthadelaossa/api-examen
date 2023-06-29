package com.dh.catalog.service;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.model.Catalogo;
import com.dh.catalog.model.serie.Serie;
import com.dh.catalog.model.movie.Movie;
import com.dh.catalog.repository.MovieRepository;
import com.dh.catalog.repository.SerieRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Service
public class CatalogService {


    private final MovieServiceClient movieServiceClient;
    private final SerieServiceClient serieServiceClient;

    @Autowired
    @Lazy
    private CatalogService self;

    public CatalogService(MovieServiceClient movieServiceClient, SerieServiceClient serieServiceClient) {
        this.movieServiceClient = movieServiceClient;
        this.serieServiceClient = serieServiceClient;
    }

   @Retry(name = "myService")
   @CircuitBreaker(name = "myService", fallbackMethod = "fallbackMovies")
   public List<Movie> getMoviesByGenre(@PathVariable String genre) {
        List<Movie> movieList = movieServiceClient.getMovieByGenre(genre);
        return movieList;
    }


    @Retry(name = "myService")
    @CircuitBreaker(name = "myService", fallbackMethod = "fallbackSeries")
    private List<Serie> getSeriesByGenre(String genre) {
        List<Serie> serieLists = serieServiceClient.getSerieByGenre(genre);
        return serieLists;
    }

    public  List<Movie> fallbackMovies(String genre, Throwable t) throws Exception {
           throw new RuntimeException("No se pudo encontrar ninguna película en la base de datos local");
    }

    public List<Serie> fallbackSeries(String genre, Throwable t) throws Exception {
               throw new RuntimeException("No se pudo encontrar ninguna serie en la base de datos local");
    }


}
