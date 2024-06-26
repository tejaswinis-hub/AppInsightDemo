package com.example.appinsightdemo.controller;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello,Java World!";
    }

    @GetMapping("/hello1")
    public String sayHello1() {
        TelemetryClient telemetryClient = new TelemetryClient();
        RequestTelemetry requestTelemetry = new RequestTelemetry();
        // Add custom properties
        requestTelemetry.getProperties().put("traceId", "678trace");
        telemetryClient.trackRequest(requestTelemetry);

        return "Hello,Python World!";
    }
}