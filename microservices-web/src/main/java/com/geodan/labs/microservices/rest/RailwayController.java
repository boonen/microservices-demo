package com.geodan.labs.microservices.rest;

import com.geodan.labs.microservices.entity.RailwayStation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Created by janb on 17-11-2014.
 */
@RestController
public class RailwayController {

    @RequestMapping(value = "/railway_stations", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<RailwayStation> getRailwayStations() {
        return Collections.emptyList();
    }

}
