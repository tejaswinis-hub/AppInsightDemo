package com.example.appinsightdemo.config;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.EventTelemetry;
import com.microsoft.applicationinsights.telemetry.TelemetryContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;


@Service
public class RequestTelemetryFilter extends OncePerRequestFilter {
    private static final Logger logger = Logger.getLogger(RequestTelemetryFilter.class.getName());

    private static final TelemetryClient TELEMETRY_CLIENT = new TelemetryClient();
    private final AtomicInteger metricValue = new AtomicInteger();

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

            // Start custom request telemetry
            /*RequestTelemetry requestTelemetry = new RequestTelemetry();
            requestTelemetry.setName(request.getRequestURI());
            requestTelemetry.setTimestamp(new java.util.Date());
            requestTelemetry.setUrl(new URL(request.getRequestURL().toString()));*/

            // Add custom properties

            String tenantId = request.getHeader("tenantId");
            String contactId = request.getHeader("contactId");
            String traceId = request.getHeader("TraceId");

           /* requestTelemetry.getProperties().put(TENANT_ID, tenantId);
            requestTelemetry.getProperties().put(CONTACT_ID, contactId);
            requestTelemetry.getProperties().put(TRACE_ID, traceId);*/

            Map<String, String> requestTelemetry = new HashMap<>();
            requestTelemetry.put(TENANT_ID, tenantId);
            requestTelemetry.put(CONTACT_ID, contactId);
            requestTelemetry.put(TRACE_ID, traceId);
            logger.info("Added Properties");


            // TELEMETRY_CLIENT.trackRequest(requestTelemetry);
            //track(requestTelemetry);
            trackEventWithCustomProperties();

        } catch (Exception e) {
            logger.info("Failed to add custom properties");
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private void track(Map<String, String> requestTelemetry) {
        // value represents the sum of the individual metric values in the sample.
        // Because the sample size is 1, value is equal to the metricValue.
        double value = metricValue.get();

        // sampleCount represents a count of the individual values in the sample.
        // Because the sample size is 1, sampleCount is always 1.
        int sampleCount = 1;

        // min represents the minimum value of the sample.
        // Because the sample size is 1, min is equal to the metricValue.
        double min = metricValue.get();

        // max represents the minimum value of the sample.
        // Because the sample size is 1, max is equal to the metricValue.
        double max = metricValue.get();

        // The standard deviation of the sample. When stdDev is 0, all the values in the sample are identical.
        // Because the sample size is 1, stdDev is always 0.
        double stdDev = 0;


        TELEMETRY_CLIENT.trackMetric("Custom properties", value,
                sampleCount,
                min,
                max,
                stdDev,
                requestTelemetry);
        logger.info("Added custom properties to track metric");

    }

    private void trackEventWithCustomProperties() {
        // Example of tracking an event with custom properties
        EventTelemetry telemetry = new EventTelemetry();
        telemetry.getProperties().put(TENANT_ID, "Dummy1");
        telemetry.getProperties().put(CONTACT_ID, "Dummy1");
        telemetry.getProperties().put(TRACE_ID, "Dummy1");




        TELEMETRY_CLIENT.trackEvent(telemetry);
        TELEMETRY_CLIENT.flush();
    }

}
