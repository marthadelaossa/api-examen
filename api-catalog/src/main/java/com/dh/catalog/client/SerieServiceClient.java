package com.dh.catalog.client;

import com.dh.catalog.model.Catalogo;
import com.dh.catalog.model.serie.Season;
import com.dh.catalog.model.serie.Serie;
import lombok.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name="api-serie")//,url = "http://localhost:8080")
public interface SerieServiceClient {

	@GetMapping("/api/v1/series/{genre}")
	List<Serie> getSerieByGenre(@PathVariable (value = "genre") String genre);


}
