package com.example.appinsightdemo.config;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;


@Component
public class RequestTelemetryFilter extends OncePerRequestFilter {
    private static final Logger logger = Logger.getLogger(RequestTelemetryFilter.class.getName());

    private final HandlerExceptionResolver resolver;

    public RequestTelemetryFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException {
        logger.info("Entered inside doFilterInternal method");
        TelemetryClient telemetryClient = new TelemetryClient();

        // Start custom request telemetry
        RequestTelemetry requestTelemetry = new RequestTelemetry();
        requestTelemetry.setName(request.getRequestURI());
        requestTelemetry.setTimestamp(new java.util.Date());
        requestTelemetry.setUrl(new URL(request.getRequestURL().toString()));

        // Add custom properties
        String tenantId = request.getHeader("tenantId");
        String contactId = request.getHeader("contactId");
        String traceId = request.getHeader("TraceId");
        requestTelemetry.getProperties().put("TenantId", tenantId);
        requestTelemetry.getProperties().put("ContactId", contactId);
        requestTelemetry.getProperties().put("TraceId", traceId);
        logger.info("Added Properties");

        // Track the custom request telemetry
        telemetryClient.trackRequest(requestTelemetry);

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.info("Failed to add custom properties");
            resolver.resolveException(request, response, null, e);

        }
    }

}
