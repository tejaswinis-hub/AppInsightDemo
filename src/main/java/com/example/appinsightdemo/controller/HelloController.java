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
        TelemetryClient telemetryClient = new TelemetryClient();
        RequestTelemetry requestTelemetry = new RequestTelemetry();
        requestTelemetry.getProperties().put("TenantId", "tenantId");
        telemetryClient.trackRequest(requestTelemetry);
        telemetryClient.trackTrace("custom metric");
        telemetryClient.flush();

        return "Hello,Java World!";
    }
}