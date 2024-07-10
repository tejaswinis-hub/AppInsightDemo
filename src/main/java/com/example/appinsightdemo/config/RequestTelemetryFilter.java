package com.example.appinsightdemo.config;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;


@Service
public class RequestTelemetryFilter extends OncePerRequestFilter {
    private static final Logger logger = Logger.getLogger(RequestTelemetryFilter.class.getName());

    private final HandlerExceptionResolver resolver;
    private static final TelemetryClient TELEMETRY_CLIENT = new TelemetryClient();


    public RequestTelemetryFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        logger.info("Entered inside doFilterInternal method");
        try {
            // TelemetryClient telemetryClient = new TelemetryClient();
            // TelemetryClient telemetryClient = TelemetryClientData.getInstance();

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
            // telemetryClient.getContext().setConnectionString("InstrumentationKey=bf0d9b88-d94c-42f2-b638-e086fd4807c3;IngestionEndpoint=https://eastus-8.in.applicationinsights.azure.com/;LiveEndpoint=https://eastus.livediagnostics.monitor.azure.com/;ApplicationId=4a72099e-5e0a-4ec6-ba60-35f1bd6cc23d");

            logger.info("ConnectionString:" + TELEMETRY_CLIENT.getContext().getConnectionString());
            logger.info("Connection String from request telemetry:" + requestTelemetry.getContext().getConnectionString());
            logger.info("Connection String from request telemetry:" + requestTelemetry.getContext().getInstrumentationKey());

            TELEMETRY_CLIENT.trackRequest(requestTelemetry);


            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.info("Failed to add custom properties");
            filterChain.doFilter(request, response);
        }
    }

}
