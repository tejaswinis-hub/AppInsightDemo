package com.example.appinsightdemo.config;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.TelemetryConfiguration;

public class TelemetryClientSingleton {
    private static volatile TelemetryClient instance;

    // Private constructor to prevent instantiation from other classes
    private TelemetryClientSingleton() {
        // Private constructor to prevent instantiation
    }

    // Public method to provide access to the singleton instance
    public static TelemetryClient getInstance() {
        if (instance == null) {
            synchronized (TelemetryClientSingleton.class) {
                if (instance == null) {
                    // Initialize TelemetryClient only once
                    TelemetryConfiguration configuration = TelemetryConfiguration.getActive();
                    configuration.setInstrumentationKey("bf0d9b88-d94c-42f2-b638-e086fd4807c3");
                    instance = new TelemetryClient(configuration);
                }
            }
        }
        return instance;
    }
}

