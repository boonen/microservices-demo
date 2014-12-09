package com.geodan.labs.microservices.rest;

import com.geodan.labs.microservices.entity.LocationMessage;
import com.geodan.labs.microservices.entity.MessageStatus;
import com.geodan.labs.microservices.service.AMQPIncomingLocationMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by janb on 12-11-2014.
 */
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AMQPIncomingLocationMessageSender messageSender;

    @RequestMapping(value = "/send", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public MessageStatus postMessage(@RequestBody LocationMessage message) {
        logger.info("Received a location: {},{}.", message.getX(), message.getY());
        MessageStatus result = messageSender.send(message);

        return result;
    }

}
