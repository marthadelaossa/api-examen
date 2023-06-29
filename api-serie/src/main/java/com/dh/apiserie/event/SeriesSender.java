package com.dh.apiserie.event;

import com.dh.apiserie.config.RabbitTemplateConfig;
import com.dh.apiserie.model.Serie;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
public class SeriesSender {

    private final RabbitTemplate rabbitTemplate;

    public SeriesSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void serieSender(Serie serie) {
        rabbitTemplate.convertAndSend(RabbitTemplateConfig.EXCHANGE_NAME, RabbitTemplateConfig.TOPIC_NEW_SERIE, serie);
    }
}