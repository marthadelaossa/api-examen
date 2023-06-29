package com.dh.catalog.event;


import com.dh.catalog.config.RabbitTemplateConfig;
import com.dh.catalog.model.serie.Season;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeriesListener {


    @RabbitListener(queues = RabbitTemplateConfig.QUEUE_NEW_SERIE)
    public void receiveSerie(SeriesListener.Serie serie) {
        System.out.print("Serie "+ serie.name);
        //procesamiento
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Serie {

        @Id
        private String id;
        private String name;
        private String genre;
        private List<Season> seasons = new ArrayList<>();
    }

}
