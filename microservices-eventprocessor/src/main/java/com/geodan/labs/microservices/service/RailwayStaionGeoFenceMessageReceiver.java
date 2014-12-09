package com.geodan.labs.microservices.service;

import com.geodan.labs.microservices.entity.LocationMessage;
import com.geodan.labs.microservices.entity.RailwayStation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * a receiver of {@link com.geodan.labs.microservices.entity.LocationMessage} objects that applies GeoFencing
 * functionality on incoming messages backed by Railway Station data.
 * <p/>
 * Note that this implementation does not depend on any Spring or AMQP code, it is therefore suitable for other
 * implementations as well.
 */
public class RailwayStaionGeoFenceMessageReceiver implements MessageReceiver {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private RailwayStationService railwayStationService;

    @Override
    public void onReceive(LocationMessage message) {
        RailwayStation closest = railwayStationService.findClosest(message.getGeometry());
        logger.info("Received message {}. Closest railway station: {}.", message, closest);
    }

}
