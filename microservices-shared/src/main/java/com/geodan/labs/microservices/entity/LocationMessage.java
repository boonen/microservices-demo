package com.geodan.labs.microservices.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by janb on 16-11-2014.
 */
public class LocationMessage implements Serializable {

    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 28992);

    private DateTime dateReceived;

    private UUID messageIdentifier = UUID.randomUUID();

    private String message;

    private double x;

    private double y;

    public LocationMessage() {
        super();
    }

    public LocationMessage(String message) {
        this();
        this.message = message;
    }

    public DateTime getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(DateTime dateReceived) {
        this.dateReceived = dateReceived;
    }

    public UUID getMessageIdentifier() {
        return messageIdentifier;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        return messageIdentifier.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocationMessage)) return false;

        LocationMessage that = (LocationMessage) o;

        if (!messageIdentifier.equals(that.messageIdentifier)) return false;

        return true;
    }

    @Override
    public String toString() {
        return messageIdentifier.toString();
    }

    @JsonIgnore
    public Geometry getGeometry() {
        CoordinateSequence coordinate = new CoordinateArraySequence(new Coordinate[]{new Coordinate(x, y)});
        return new Point(coordinate, geometryFactory);
    }

    public static class Builder {
        private String message;

        private Builder() {
        }

        public static Builder demoMessage() {
            return new Builder();
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public LocationMessage build() {
            return new LocationMessage();
        }
    }
}
