package com.dh.catalog.event;



import com.dh.catalog.config.RabbitTemplateConfig;
import com.dh.catalog.model.serie.Serie;
import com.dh.catalog.repository.SerieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class SeriesListener {



    private final SerieRepository serieRepository;

    public SeriesListener( SerieRepository serieRepository) {
           this.serieRepository = serieRepository;
    }

    @RabbitListener(queues = RabbitTemplateConfig.QUEUE_NEW_SERIE)
    public void receiveSerie(@Payload Serie serie) {
              serieRepository.save(serie);
    }

}
