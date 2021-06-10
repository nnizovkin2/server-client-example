package com.com.dto;

import java.util.Date;

public class Message {
//logLevel, message, timestamp, index
    private LogLevel logLevel;
    private String message;
    private long timestamp;
    private long index;

    public Message() {
    }

    public Message(LogLevel logLevel, String message, long timestamp, long index) {
        this.logLevel = logLevel;
        this.message = message;
        this.timestamp = timestamp;
        this.index = index;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getIndex() {
        return index;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "logLevel=" + logLevel +
                ", message='" + message + '\'' +
                ", timestamp=" + new Date(timestamp) +
                ", index=" + index +
                '}';
    }
}
