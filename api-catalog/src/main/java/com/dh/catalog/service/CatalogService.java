package com.dh.catalog.service;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.model.Catalogo;
import com.dh.catalog.repository.CatalogRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dh.catalog.model.serie.Season;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Service
public class CatalogService {


    private final MovieServiceClient movieServiceClient;
    private final SerieServiceClient serieServiceClient;
    private final CatalogRepository catalogRepository;

    @Autowired
    @Lazy
    private CatalogService self;
    private Catalogo catalog = new Catalogo();

    public CatalogService(MovieServiceClient movieServiceClient, SerieServiceClient serieServiceClient, CatalogRepository catalogRepository) {
        this.movieServiceClient = movieServiceClient;
        this.serieServiceClient = serieServiceClient;
        this.catalogRepository = catalogRepository;
    }

    @Transactional
    public void createMovie(Long movieId, String name, String genre, String urlStream){
        MovieServiceClient.Movie pelicula = self.getMovieByGenre(genre);
        catalog.setMovie(new Catalogo.Movie(pelicula.getId(), pelicula.getName(), pelicula.getGenre(), pelicula.getUrlStream()));
        catalogRepository.save(catalog);
    }



    @Transactional
    public void createSerie(String id, String name, String genre, String urlStream, List<Season> seasons){
        SerieServiceClient.Serie serie = self.getSerieByGenre(genre);
        catalog.setSerie(new Catalogo.Serie(serie.getId(), serie.getName(), serie.getGenre(), serie.getUrlStream(), serie.getSeasons()));
        catalogRepository.save(catalog);
    }

   @Retry(name = "movies")
   @CircuitBreaker(name = "movies", fallbackMethod = "fallbackMovies")
   public MovieServiceClient.Movie getMovieByGenre(String genre) {
        List<MovieServiceClient.Movie> movieList = (List<MovieServiceClient.Movie>) self.getMovieByGenre(genre);
       if (movieList.isEmpty()) {
           movieList = movieServiceClient.getMovieByGenre(genre);
       }
          return (MovieServiceClient.Movie) movieList;
    }


    @Retry(name = "series")
    @CircuitBreaker(name = "series", fallbackMethod = "fallbackSeries")
    private SerieServiceClient.Serie getSerieByGenre(String genre) {
        List<SerieServiceClient.Serie> serieLists = (List<SerieServiceClient.Serie>) self.getSerieByGenre(genre);
        if (serieLists.isEmpty()) {
            serieLists = serieServiceClient.getSerieByGenre(genre);
        }
        return (SerieServiceClient.Serie) serieLists;
    }

    public MovieServiceClient.Movie fallbackMovies(String genre, Throwable t) throws Exception {
        List<MovieServiceClient.Movie> movieList = (List<MovieServiceClient.Movie>) self.getMovieByGenre(genre);
        if (movieList.isEmpty()) {
            throw new RuntimeException("No se pudo encontrar ninguna película en la base de datos local");
        }
        return (MovieServiceClient.Movie) movieList;
    }

    public SerieServiceClient.Serie fallbackSeries(String genre, Throwable t) throws Exception {
        List<SerieServiceClient.Serie> serieLists = (List<SerieServiceClient.Serie>) self.getSerieByGenre(genre);
        if (serieLists.isEmpty()) {
            throw new RuntimeException("No se pudo encontrar ninguna serie en la base de datos local");

        }
        return (SerieServiceClient.Serie) serieLists;
    }


}
