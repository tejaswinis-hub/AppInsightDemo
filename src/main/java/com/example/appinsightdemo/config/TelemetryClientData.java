package com.example.appinsightdemo.config;

import com.microsoft.applicationinsights.TelemetryClient;

public class TelemetryClientData {

    private static TelemetryClient instance = null;

    // Private constructor to prevent instantiation from outside
    private TelemetryClientData() {
        // Initialization code
    }

    // Public static method to get the singleton instance
    public static TelemetryClient getInstance() {
        if (instance == null) {
            instance = new TelemetryClient();
        }
        return instance;
    }
}
