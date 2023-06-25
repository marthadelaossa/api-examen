package com.dh.catalog.controller;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.model.serie.Season;
import com.dh.catalog.service.CatalogService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1/catalog")
public class CatalogController {

	private CatalogService catalogService;
	private final MovieServiceClient movieServiceClient;
	private final SerieServiceClient serieServiceClient;

	public CatalogController(CatalogService catalogService, MovieServiceClient movieServiceClient, SerieServiceClient serieServiceClient) {
		this.catalogService = catalogService;
		this.movieServiceClient = movieServiceClient;
		this.serieServiceClient = serieServiceClient;
	}
	@GetMapping("/movies/{genre}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
	ResponseEntity<List<MovieServiceClient.Movie>> getPGenre(@PathVariable String genre) {
		return ResponseEntity.ok(movieServiceClient.getMovieByGenre(genre));
	}

	@GetMapping("/series/{genre}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
	ResponseEntity<List<SerieServiceClient.Serie>> getSGenre(@PathVariable String genre) {
		return ResponseEntity.ok(serieServiceClient.getSerieByGenre(genre));
	}
}
