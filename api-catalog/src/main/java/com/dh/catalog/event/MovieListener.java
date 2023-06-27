package com.dh.catalog.event;



import com.dh.catalog.config.RabbitTemplateConfig;
import com.dh.catalog.model.movie.Movie;
import com.dh.catalog.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MovieListener {


    private final MovieRepository movieRepository;

    public MovieListener(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @RabbitListener(queues = RabbitTemplateConfig.QUEUE_NEW_MOVIE)
    public void receiveMovie(@Payload Movie movie) {
              movieRepository.save(movie);
    }


}




