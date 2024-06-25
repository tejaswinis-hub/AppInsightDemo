package com.example.appinsightdemo.config;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URL;

@Component
public class RequestTelemetryFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        TelemetryClient telemetryClient = new TelemetryClient();

        // Start custom request telemetry
        RequestTelemetry requestTelemetry = new RequestTelemetry();
        requestTelemetry.setName(request.getRequestURI());
        requestTelemetry.setTimestamp(new java.util.Date());
        requestTelemetry.setUrl(new URL(request.getRequestURL().toString()));

        // Optionally, add custom properties
        requestTelemetry.getProperties().put("CustomProperty", "CustomValue");

        // Track the custom request telemetry
        telemetryClient.trackRequest(requestTelemetry);

        try {
            filterChain.doFilter(request, response);
        } finally {
            // End the custom request telemetry
            requestTelemetry.setSuccess(response.getStatus() < 400);
        }
    }


}
