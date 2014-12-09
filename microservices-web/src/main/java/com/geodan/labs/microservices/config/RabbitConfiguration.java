package com.geodan.labs.microservices.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by janb on 16-11-2014.
 */
@Configuration
public class RabbitConfiguration {

    private static final String PREFIX = "microservices-demos.location";

    private static final String EXCHANGE = PREFIX + "." + "exchange";

    public static final String QUEUE = EXCHANGE + "." + "queue.incoming";

    private static final String ROUTING_KEY = "location.key";

    @Autowired
    private MappingJackson2HttpMessageConverter messageConverter;

    @Bean
    TopicExchange locationExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue locationQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    Binding locationExchangeBinding(TopicExchange locationExchange, Queue locationQueue) {
        return BindingBuilder.bind(locationQueue).to(locationExchange).with(ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(EXCHANGE);
        rabbitTemplate.setRoutingKey(ROUTING_KEY);
        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
        jsonMessageConverter.setJsonObjectMapper(messageConverter.getObjectMapper());
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter createMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
