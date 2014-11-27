package com.geodan.labs.microservices.service;

import com.geodan.labs.microservices.entity.DemoMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by janb on 26-11-2014.
 */
public class AMQPMessageReceiver implements MessageReceiver {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onReceive(DemoMessage message) {
        logger.info("Received message {}.", message);
    }

}
