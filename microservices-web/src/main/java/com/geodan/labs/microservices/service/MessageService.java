package com.geodan.labs.microservices.service;

import com.geodan.labs.microservices.entity.LocationMessage;

import java.util.Collection;

/**
 * Created by janb on 16-11-2014.
 */
public interface MessageService {

    Collection<LocationMessage> findAll();

}
