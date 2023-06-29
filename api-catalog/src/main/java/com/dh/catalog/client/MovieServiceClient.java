package com.dh.catalog.client;

import com.dh.catalog.model.movie.Movie;
import lombok.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="api-movie")
public interface MovieServiceClient {

	@GetMapping("/api/v1/movies/{genre}")
	List<Movie> getMovieByGenre(@PathVariable(value = "genre") String genre);



}
