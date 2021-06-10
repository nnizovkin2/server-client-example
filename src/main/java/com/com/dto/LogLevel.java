package com.com.dto;

public enum LogLevel {
    INFO(0), DEBUG(1), WARNING(2), ERROR(3);

    int l;

    LogLevel(int l) {
        this.l = l;
    }

    public int get() {
        return l;
    }
}
