package com.geodan.labs.microservices.rest;

import com.geodan.labs.microservices.entity.RailwayStation;
import com.geodan.labs.microservices.service.RailwayStationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by janb on 17-11-2014.
 */
@RestController
public class RailwayController {

    @Resource
    private RailwayStationService railwayStationService;

    @RequestMapping(value = "/railway_stations", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Iterable<RailwayStation> getRailwayStations() {
        return railwayStationService.findAll();
    }

}
