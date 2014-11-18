package com.geodan.labs.microservices.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vividsolutions.jts.geom.Geometry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by janb on 17-11-2014.
 */
@Entity
@Table(name = "railwaystations")
public class RailwayStation {

    @Id
    private int id;

    @Column(name = "station")
    private String name;

    @JsonIgnore
    @org.hibernate.annotations.Type(type = "org.hibernate.spatial.GeometryType")
    private Geometry geometry;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RailwayStation)) return false;

        RailwayStation that = (RailwayStation) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

}
