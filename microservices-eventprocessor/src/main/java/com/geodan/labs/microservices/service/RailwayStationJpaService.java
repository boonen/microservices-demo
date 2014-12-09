package com.geodan.labs.microservices.service;

import com.geodan.labs.microservices.entity.RailwayStation;
import com.geodan.labs.microservices.repository.RailwayStationRepository;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by janb on 18-11-2014.
 */
@Service
public class RailwayStationJpaService implements RailwayStationService {

    @Resource
    private RailwayStationRepository railwayStationRepository;

    @Override
    public Iterable<RailwayStation> findAll() {
        return railwayStationRepository.findAll();
    }

    @Override
    public RailwayStation findByName(String name) {
        return railwayStationRepository.findByNameIgnoreCase(name);
    }

    @Override
    public RailwayStation findClosest(Geometry geometry) {
        return railwayStationRepository.findClosest(geometry);
    }
}
