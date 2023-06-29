package com.dh.movie.event;

import com.dh.movie.config.RabbitTemplateConfig;
import com.dh.movie.model.Movie;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
public class MovieSender {

    private final RabbitTemplate rabbitTemplate;

    public MovieSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void movieSender(Movie movie) {
        rabbitTemplate.convertAndSend(RabbitTemplateConfig.EXCHANGE_NAME, RabbitTemplateConfig.TOPIC_NEW_MOVIE, movie);
    }
}
