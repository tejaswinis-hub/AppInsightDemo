package com.example.appinsightdemo.telemetryconfig;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.extensibility.TelemetryInitializer;
import com.microsoft.applicationinsights.telemetry.EventTelemetry;
import com.microsoft.applicationinsights.telemetry.ExceptionTelemetry;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import com.microsoft.applicationinsights.telemetry.Telemetry;
import com.microsoft.applicationinsights.telemetry.TraceTelemetry;
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
            requestTelemetry.getContext().getProperties().put("CustomRequestProperty", "Value");
            TELEMETRY_CLIENT.trackRequest(requestTelemetry);
        } else if (telemetry instanceof ExceptionTelemetry) {
            ExceptionTelemetry exceptionTelemetry = (ExceptionTelemetry) telemetry;
            exceptionTelemetry.getContext().getProperties().put("CustomExceptionProperty", "Value");
            TELEMETRY_CLIENT.trackException(exceptionTelemetry);
        } else if (telemetry instanceof TraceTelemetry) {
            TraceTelemetry traceTelemetry = (TraceTelemetry) telemetry;
            traceTelemetry.getContext().getProperties().put("CustomTraceProperty", "Value");
            TELEMETRY_CLIENT.trackTrace(traceTelemetry);
        } else if (telemetry instanceof EventTelemetry) {
            TraceTelemetry eventTelemetry = (TraceTelemetry) telemetry;
            eventTelemetry.getContext().getProperties().put("CustomTraceProperty", "Value");
            TELEMETRY_CLIENT.trackEvent(String.valueOf(eventTelemetry));
        }

    }
}


