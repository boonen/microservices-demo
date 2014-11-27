package com.geodan.labs.microservices.service;

import com.geodan.labs.microservices.entity.DemoMessage;
import com.geodan.labs.microservices.entity.MessageStatus;
import com.geodan.labs.microservices.entity.StatusType;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by janb on 20-11-2014.
 */
@Service
public class AMQPGeofenceMessageSender implements MessageSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public MessageStatus send(DemoMessage message) {
        try {
            rabbitTemplate.convertAndSend(message);
        } catch (AmqpException e) {
            return MessageStatus.Builder.messageStatus().withStatus(StatusType.ERROR).build();
        }
        return MessageStatus.Builder.messageStatus().withStatus(StatusType.OK).build();
    }

}
