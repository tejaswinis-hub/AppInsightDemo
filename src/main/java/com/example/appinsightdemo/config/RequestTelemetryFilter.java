package com.example.appinsightdemo.config;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;


@Service
public class RequestTelemetryFilter extends OncePerRequestFilter {
    private static final Logger logger = Logger.getLogger(RequestTelemetryFilter.class.getName());

    private static final TelemetryClient TELEMETRY_CLIENT = new TelemetryClient();
    private static final String TENANT_ID = "TenantId";
    private static final String CONTACT_ID = "ContactId";
    private static final String TRACE_ID = "TraceId";


    public RequestTelemetryFilter() {
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        logger.info("Entered inside doFilterInternal method");
        try {
            //to clear azure default custom properties
            TELEMETRY_CLIENT.getContext().getProperties().clear();

            // Start custom request telemetry
            RequestTelemetry requestTelemetry = new RequestTelemetry();
            //RequestTelemetry requestTelemetry = new RequestTelemetry("API details", null, null, null, true);


            // Add custom properties
            String tenantId = request.getHeader("tenantId");
            String contactId = request.getHeader("contactId");
            String traceId = request.getHeader("traceId");

            requestTelemetry.getProperties().put(TENANT_ID, tenantId);
            requestTelemetry.getProperties().put(CONTACT_ID, contactId);
            requestTelemetry.getProperties().put(TRACE_ID, traceId);

            logger.info("Added Properties");

            TELEMETRY_CLIENT.trackRequest(requestTelemetry);

        } catch (Exception e) {
            logger.info("Failed to add custom properties");
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
