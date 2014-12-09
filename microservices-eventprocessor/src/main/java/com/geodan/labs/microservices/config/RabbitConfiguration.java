package com.geodan.labs.microservices.config;

import com.geodan.labs.microservices.entity.LocationMessage;
import com.geodan.labs.microservices.service.RailwayStaionGeoFenceMessageReceiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
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

    public static final String QUEUE_IN = EXCHANGE + "." + "queue.incoming";

    //public static final String QUEUE_OUT = EXCHANGE + "." + "queue.processed";

    private static final String ROUTING_KEY = "location.key";

    @Autowired
    private MappingJackson2HttpMessageConverter messageConverter;

    @Bean
    TopicExchange locationExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue locationQueue() {
        return new Queue(QUEUE_IN, true);
    }

    @Bean
    Binding locationExchangeBinding(TopicExchange locationExchange, Queue locationQueue) {
        return BindingBuilder.bind(locationQueue).to(locationExchange).with(ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(EXCHANGE);
        rabbitTemplate.setRoutingKey(ROUTING_KEY);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
        jsonMessageConverter.setJsonObjectMapper(messageConverter.getObjectMapper());
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setDefaultType(LocationMessage.class);
        jsonMessageConverter.setClassMapper(classMapper);
        return jsonMessageConverter;
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(QUEUE_IN);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    RailwayStaionGeoFenceMessageReceiver receiver() {
        return new RailwayStaionGeoFenceMessageReceiver();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RailwayStaionGeoFenceMessageReceiver receiver, MessageConverter jsonMessageConverter) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(receiver, "onReceive");
        adapter.setMessageConverter(jsonMessageConverter);

        return adapter;
    }

}
