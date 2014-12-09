package com.geodan.labs.microservices.service;

import com.geodan.labs.microservices.entity.RailwayStation;
import com.vividsolutions.jts.geom.Geometry;

/**
 * Created by janb on 16-11-2014.
 */
public interface RailwayStationService {

    Iterable<RailwayStation> findAll();

    RailwayStation findByName(String name);

    RailwayStation findClosest(Geometry geometry);
}
