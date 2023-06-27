package com.dh.movie.event;

import com.dh.movie.config.RabbitTemplateConfig;
import com.dh.movie.model.Movie;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
public class MovieSender {

    private final RabbitTemplate rabbitTemplate;

    public MovieSender(RabbitTemplate rabbitTemplate) {

        this.rabbitTemplate = rabbitTemplate;
    }

    public void execute(Movie newMovie) {
        rabbitTemplate.convertAndSend(RabbitTemplateConfig.EXCHANGE_NAME, RabbitTemplateConfig.TOPIC_NEW_MOVIE, newMovie);
    }
}
