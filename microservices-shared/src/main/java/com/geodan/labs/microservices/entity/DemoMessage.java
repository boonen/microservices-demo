package com.geodan.labs.microservices.entity;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by janb on 16-11-2014.
 */
public class DemoMessage implements Serializable {

    private DateTime dateReceived;

    private UUID messageIdentifier = UUID.randomUUID();

    private String message;

    public DemoMessage() {
        super();
    }

    public DemoMessage(String message) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DemoMessage)) return false;

        DemoMessage that = (DemoMessage) o;

        if (!messageIdentifier.equals(that.messageIdentifier)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return messageIdentifier.hashCode();
    }

    @Override
    public String toString() {
        return messageIdentifier.toString();
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

        public DemoMessage build() {
            return new DemoMessage();
        }
    }
}
