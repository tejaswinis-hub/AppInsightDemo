package com.example.appinsightdemo.telemetryconfig;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.extensibility.TelemetryInitializer;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import com.microsoft.applicationinsights.telemetry.Telemetry;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class CustomTelemetryInitializer implements TelemetryInitializer {
    private static final Logger logger = Logger.getLogger(CustomTelemetryInitializer.class.getName());

    private static final TelemetryClient TELEMETRY_CLIENT = new TelemetryClient();

    @Override
    public void initialize(Telemetry telemetry) {
        logger.info("Initialized telemetry");
        if (telemetry instanceof RequestTelemetry) {
            RequestTelemetry requestTelemetry = (RequestTelemetry) telemetry;

            // Add custom properties to the request telemetry
            requestTelemetry.getProperties().put("CustomProperty1", "Value1");
            requestTelemetry.getProperties().put("CustomProperty2", "Value2");

            // Optionally, track the telemetry if not already tracked
            TELEMETRY_CLIENT.trackRequest(requestTelemetry);
            logger.info("Added custom properties to Initialized telemetry");
        }
    }
}

