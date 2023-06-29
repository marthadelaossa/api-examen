package com.dh.catalog.event;


import com.dh.catalog.config.RabbitTemplateConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MovieListener {




    @RabbitListener(queues = RabbitTemplateConfig.QUEUE_NEW_MOVIE)
    public void receiveMovie(MovieListener.Movie movie) {
        System.out.print("Movie "+ movie.name);
        //procesamiento
     }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Movie {
        @Id
        private String id;
        private String name;
        private String genre;
    }


}




