package com.example.appinsightdemo.config;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.TelemetryContext;
import org.springframework.stereotype.Service;

@Service
public class CustomTelemetryExample {
    private static final TelemetryClient telemetryClient = ApplicationInsightsInitializer.getTelemetryClient();

    public static void logInitialEvent() {
        // Create a custom event
        String eventName = "InitialEvent";
        telemetryClient.trackEvent(eventName);

        // Add custom properties
        TelemetryContext context = telemetryClient.getContext();
        context.getProperties().put("CustomContextProperty", "ContextValue");
        telemetryClient.flush();
    }
}
