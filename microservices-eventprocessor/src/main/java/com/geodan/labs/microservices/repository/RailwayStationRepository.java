package com.geodan.labs.microservices.repository;

import com.geodan.labs.microservices.entity.RailwayStation;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by janb on 18-11-2014.
 */
@Repository
public interface RailwayStationRepository extends CrudRepository<RailwayStation, Long> {

    RailwayStation findByNameIgnoreCase(String name);

    //@Query(value="select r from RailwayStation r ORDER BY ST_Distance(r.geometry, :geometry) ASC")
    @Query(value = "select * from railwaystations order by ST_Distance(geometry, ?1) asc limit 1", nativeQuery = true)
    RailwayStation findClosest(Geometry geometry);

}
