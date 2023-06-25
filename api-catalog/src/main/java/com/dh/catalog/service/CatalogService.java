package com.dh.catalog.service;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.model.Catalogo;
import com.dh.catalog.model.serie.Season;
import com.dh.catalog.repository.CatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
/*
    @Transactional
    public void createM(Long movieId, String name, String genre, String urlStream){
        MovieServiceClient.Movie peli = self.searchMGenres(genre);
        catalog.setMovie(new Catalogo.Movie(peli.getId(), peli.getName(), peli.getGenre(), peli.getUrlStream()));
        catalogRepository.save(catalog);
    }


    @Transactional
    public void createS(String id, String name, String genre, String urlStream, List<Season> seasons){
        SerieServiceClient.Serie serie = self.searchSGenres(genre);
        catalog.setSerie(new Catalogo.Serie(serie.getId(), serie.getName(), serie.getGenre(), serie.getUrlStream(), serie.getSeasons()));
        catalogRepository.save(catalog);
    }

   //circuit breaker:
    @Retry(name = "retryGenre")
    @CircuitBreaker(name = "searchGenres", fallbackMethod = "searchGenresMFallBack") //modificar en config
    public MovieServiceClient.Movie searchMGenres(String genre) {
        List<MovieServiceClient.Movie> resMovies = (List<MovieServiceClient.Movie>) self.searchMGenres(genre);
        return (MovieServiceClient.Movie) resMovies;
    }

    @Retry(name = "retryGenreS")
    @CircuitBreaker(name = "searchGenresS", fallbackMethod = "searchGenresSFallBack")
    private SerieServiceClient.Serie searchSGenres(String genre) {
        List<SerieServiceClient.Serie> resSeries = (List<SerieServiceClient.Serie>) self.searchSGenres(genre);
        return (SerieServiceClient.Serie) resSeries;
    }

    public MovieServiceClient.Movie searchGenresMFallBack(String genre, Throwable t) throws Exception {
        throw new Exception("No se encontraron peliculas de ese genero");
    }

    public SerieServiceClient.Serie searchGenresSFallBack(String genre, Throwable t) throws Exception {
        throw new Exception("No se encontraron series de ese genero");
    }*/


}
