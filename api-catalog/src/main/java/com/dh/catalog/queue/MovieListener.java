package com.dh.catalog.queue;

import com.dh.catalog.repository.MovieRepository;
import com.dh.catalog.model.movie.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MovieListener {


    private final RabbitTemplate rabbitTemplate;

    private final MovieRepository movieRepository;
    private final Logger logger = LoggerFactory.getLogger(MovieListener.class);

    public MovieListener(RabbitTemplate rabbitTemplate, MovieRepository movieRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.movieRepository = movieRepository;
    }

    @RabbitListener(queues = {"${queue.movie.name}"})
    public void receive(@Payload Movie movie) {
        try {
            logger.info("Leyendo cola :" + movie);
            movieRepository.save(movie);
        } catch (Exception e) {
            logger.error("Error al crear la pelicula: {}", e.getMessage());
            rabbitTemplate.convertAndSend("error.exchange", "error.routingKey", movie);
        }
    }
}
