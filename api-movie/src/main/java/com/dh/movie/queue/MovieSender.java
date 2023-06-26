package com.dh.movie.queue;


import com.dh.movie.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MovieSender {

    private final RabbitTemplate rabbitTemplate;

    private final Queue serieQueue;

    public void sendMovie(Movie movie) {
        this.rabbitTemplate.convertAndSend(this.serieQueue.getName(), movie);
    }
}
