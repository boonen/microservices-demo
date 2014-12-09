package com.geodan.labs.microservices.service;

import com.geodan.labs.microservices.entity.LocationMessage;
import com.geodan.labs.microservices.entity.MessageStatus;

/**
 * Created by janb on 16-11-2014.
 */
public interface MessageSender {

    /**
     * <p>Send a message to any service and receive the status.</p>
     *
     * @param message
     * @return the status that was returned by the service
     */
    MessageStatus send(LocationMessage message);

}
