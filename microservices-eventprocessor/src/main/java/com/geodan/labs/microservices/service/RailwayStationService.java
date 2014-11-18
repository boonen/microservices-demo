package com.geodan.labs.microservices.service;

import com.geodan.labs.microservices.entity.RailwayStation;

/**
 * Created by janb on 16-11-2014.
 */
public interface RailwayStationService {

    Iterable<RailwayStation> findAll();

    RailwayStation findByName(String name);

}
