package com.geodan.labs.microservices.service;

import com.geodan.labs.microservices.entity.LocationMessage;

/**
 * Created by janb on 20-11-2014.
 */
public interface MessageReceiver {

    /**
     * <p>Recives a message from any service.</p>
     *
     * @param message the Message that is received.
     * @return the status that was returned by the service
     */
    void onReceive(LocationMessage message);

}
