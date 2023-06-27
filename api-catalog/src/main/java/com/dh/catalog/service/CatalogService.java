package com.dh.catalog.service;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.model.Catalogo;
import com.dh.catalog.repository.CatalogRepository;
import com.dh.catalog.repository.MovieRepository;
import com.dh.catalog.repository.SerieRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CatalogService {


    private final MovieServiceClient movieServiceClient;
    private final SerieServiceClient serieServiceClient;
    private final MovieRepository movieRepository;


    private final SerieRepository serieRepository;

    @Autowired
    @Lazy
    private CatalogService self;
    private Catalogo catalog = new Catalogo();

    public CatalogService(MovieServiceClient movieServiceClient, SerieServiceClient serieServiceClient, SerieRepository serieRepository, MovieRepository movieRepository) {
        this.movieServiceClient = movieServiceClient;
        this.serieServiceClient = serieServiceClient;
        this.movieRepository = movieRepository;
        this.serieRepository = serieRepository;
    }

   @Retry(name = "movies")
   @CircuitBreaker(name = "movies", fallbackMethod = "fallbackMovies")
   public List<MovieServiceClient.Movie> getMovieByGenre(String genre) {
        List<MovieServiceClient.Movie> movieList =  movieServiceClient.getMovieByGenre(genre);
        return movieList;
    }


    @Retry(name = "series")
    @CircuitBreaker(name = "series", fallbackMethod = "fallbackSeries")
    private  List<SerieServiceClient.Serie> getSerieByGenre(String genre) {
        List<SerieServiceClient.Serie> serieLists = serieServiceClient.getSerieByGenre(genre);
        return serieLists;
    }

    public MovieServiceClient.Movie fallbackMovies(String genre, Throwable t) throws Exception {
        List<MovieServiceClient.Movie> movieList = movieServiceClient.getMovieByGenre(genre);
          return (MovieServiceClient.Movie) movieList;
    }

    public SerieServiceClient.Serie fallbackSeries(String genre, Throwable t) throws Exception {
        List<SerieServiceClient.Serie> serieLists = serieServiceClient.getSerieByGenre(genre);
        if (serieLists.isEmpty()) {
            throw new RuntimeException("No se pudo encontrar ninguna serie en la base de datos local");

        }
        return (SerieServiceClient.Serie) serieLists;
    }


}
