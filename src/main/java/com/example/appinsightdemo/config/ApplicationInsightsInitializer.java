package com.example.appinsightdemo.config;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.TelemetryConfiguration;
import org.springframework.stereotype.Service;

@Service
public class ApplicationInsightsInitializer {
    private static TelemetryClient telemetryClient;

    static {
        TelemetryConfiguration configuration = TelemetryConfiguration.getActive();
        configuration.setInstrumentationKey("bf0d9b88-d94c-42f2-b638-e086fd4807c3");
        telemetryClient = new TelemetryClient(configuration);
    }

    public static TelemetryClient getTelemetryClient() {
        return telemetryClient;
    }
}
