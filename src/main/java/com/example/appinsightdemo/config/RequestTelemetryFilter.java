package com.example.appinsightdemo.config;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.TelemetryContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
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
        logger.info("Entered inside doFilterInternal method for Request telemetry filter data");
        try {
            // Start custom request telemetry
           /* RequestTelemetry requestTelemetry = new RequestTelemetry();
            requestTelemetry.setName(request.getRequestURI());
            try {
                requestTelemetry.setUrl(new URL(request.getRequestURL().toString()));
            } catch (MalformedURLException e) {
                logger.info("Failed to form URL");
            }*/

            // Add custom properties
            String tenantId = request.getHeader("tenantId");
            String contactId = request.getHeader("contactId");
            String traceId = request.getHeader("traceId");

            /*requestTelemetry.getProperties().put(TENANT_ID, tenantId);
            requestTelemetry.getProperties().put(CONTACT_ID, contactId);
            requestTelemetry.getProperties().put(TRACE_ID, traceId);*/


            logger.info("Added Properties");


            //TELEMETRY_CLIENT.trackRequest(requestTelemetry);
            // trackEvent(tenantId, contactId, traceId);
            trackRequest(tenantId, contactId, traceId);


        } catch (Exception e) {
            logger.info("Failed to add custom properties");
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private void trackEvent(String tenantId, String contactId, String traceId) {
        String eventName = "InitialEvent";
        TELEMETRY_CLIENT.trackEvent(eventName);
        // Add custom properties
        TelemetryContext context = TELEMETRY_CLIENT.getContext();
        context.getProperties().put(TENANT_ID, tenantId);
        context.getProperties().put(TRACE_ID, traceId);
        context.getProperties().put(CONTACT_ID, contactId);
        TELEMETRY_CLIENT.flush();
    }

    private void trackRequest(String tenantId, String contactId, String traceId) {
        TelemetryClient singleInstanceTelemetryClient = TelemetryClientSingleton.getInstance();
        TelemetryContext context = singleInstanceTelemetryClient.getContext();
        context.getProperties().put("value1", tenantId);
        context.getProperties().put("value2", traceId);
        context.getProperties().put("value3", contactId);
        singleInstanceTelemetryClient.flush();
    }
}

