package com.dh.apiserie.controller;

import com.dh.apiserie.event.SeriesSender;
import com.dh.apiserie.model.Serie;
import com.dh.apiserie.repository.SerieRepository;
import com.dh.apiserie.service.SerieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/series")
public class SerieController {

    private SerieService serieService;
    private final SerieRepository serieRepository;
    private final SeriesSender seriesSender;

    public SerieController(SerieService serieService, SerieRepository movieRepository, SerieRepository serieRepository, SeriesSender seriesSender) {
        this.serieService = serieService;
        this.serieRepository = serieRepository;
        this.seriesSender = seriesSender;
    }



    @GetMapping("/{genre}")
    ResponseEntity<List<Serie>> getSerieByGenre(@PathVariable String genre){
        return ResponseEntity.ok(serieService.getSeriesBygGenre(genre));
    }

    @PostMapping
    void createNewSerie(@RequestBody Serie serie) {
        createSerie(serie);
        serieService.createSerie(serie);
    }

    public void createSerie(Serie serie) {
        seriesSender.serieSender(serie);
        serieRepository.save(serie);
    }
}
