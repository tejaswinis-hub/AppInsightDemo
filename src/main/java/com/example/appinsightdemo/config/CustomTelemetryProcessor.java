package com.example.appinsightdemo.config;

import com.microsoft.applicationinsights.extensibility.TelemetryInitializer;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import com.microsoft.applicationinsights.telemetry.Telemetry;

public class CustomTelemetryProcessor implements TelemetryInitializer {


    @Override
    public void initialize(Telemetry telemetry) {
        if (telemetry instanceof RequestTelemetry) {
            RequestTelemetry requestTelemetry = (RequestTelemetry) telemetry;
            requestTelemetry.getProperties().put("opId", "opId");
        }
    }
}
