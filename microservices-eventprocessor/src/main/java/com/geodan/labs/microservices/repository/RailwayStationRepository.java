package com.geodan.labs.microservices.repository;

import com.geodan.labs.microservices.entity.RailwayStation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by janb on 18-11-2014.
 */
@Repository
public interface RailwayStationRepository extends CrudRepository<RailwayStation, Long> {

    RailwayStation findByNameIgnoreCase(String name);

}
