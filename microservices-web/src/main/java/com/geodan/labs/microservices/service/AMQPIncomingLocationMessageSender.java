package com.geodan.labs.microservices.service;

import com.geodan.labs.microservices.entity.LocationMessage;
import com.geodan.labs.microservices.entity.MessageStatus;
import com.geodan.labs.microservices.entity.StatusType;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by janb on 16-11-2014.
 */
@Service
public class AMQPIncomingLocationMessageSender implements MessageSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public MessageStatus send(LocationMessage message) {
        try {
            rabbitTemplate.convertAndSend(message);
        } catch (AmqpException e) {
            return MessageStatus.Builder.messageStatus().withStatus(StatusType.ERROR).build();
        }
        return MessageStatus.Builder.messageStatus().withStatus(StatusType.PROCESSING).build();
    }

}
