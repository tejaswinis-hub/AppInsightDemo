package com.example.appinsightdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {

       /* TelemetryClient telemetryClient = new TelemetryClient();
        RequestTelemetry requestTelemetry = new RequestTelemetry();

        // Add more properties as needed
        EventTelemetry eventTelemetry = new EventTelemetry();
        eventTelemetry.getProperties().put("CustomProperty1", "Value1");

        // Track an event with custom properties
        telemetryClient.trackEvent(eventTelemetry);

        requestTelemetry.getProperties().put("TenantId", "tenantId");
        telemetryClient.trackRequest(requestTelemetry);
        telemetryClient.trackTrace("custom metric");
        telemetryClient.flush();*/

        return "Hello,Java World!";
    }
}