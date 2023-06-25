package com.dh.catalog.model;

import com.dh.catalog.model.serie.Season;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Catalog")
public class Catalogo {

    private String id;
    private Movie movie;
    private Serie serie;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Movie{
        private Long id;
        private String name;
        private String genre;
        private String urlStream;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Serie{
        private String id;
        private String name;
        private String genre;
        private String urlStream;
        private List<Season> seasons = new ArrayList<>();
    }
}
