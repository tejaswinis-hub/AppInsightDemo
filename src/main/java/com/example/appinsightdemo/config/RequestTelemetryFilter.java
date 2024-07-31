package com.example.appinsightdemo.config;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.EventTelemetry;
import com.microsoft.applicationinsights.telemetry.MetricTelemetry;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import com.microsoft.applicationinsights.telemetry.TelemetryContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
            RequestTelemetry requestTelemetry = new RequestTelemetry();
            requestTelemetry.setName(request.getMethod() + " " + request.getRequestURI());
            try {
                requestTelemetry.setUrl(new URL(request.getRequestURL().toString()));
            } catch (MalformedURLException e) {
                logger.info("Failed to form URL");
            }
            requestTelemetry.setResponseCode("200");

            // Add custom properties
            String tenantId = request.getHeader("tenantId");
            String contactId = request.getHeader("contactId");
            String traceId = request.getHeader("traceId");

            TelemetryClient singleInstanceTelemetryClient = TelemetryClientSingleton.getInstance();
            TelemetryContext context = singleInstanceTelemetryClient.getContext();

            requestTelemetry.setId(context.getOperation().getId());


            context.getProperties().put(TENANT_ID, tenantId);
            context.getProperties().put(CONTACT_ID, contactId);
            context.getProperties().put(TRACE_ID, traceId);
            singleInstanceTelemetryClient.trackRequest(requestTelemetry);
            singleInstanceTelemetryClient.flush();

           /* requestTelemetry.getProperties().put(TENANT_ID, tenantId);
            requestTelemetry.getProperties().put(CONTACT_ID, contactId);
            requestTelemetry.getProperties().put(TRACE_ID, traceId);*/


            logger.info("Added Properties");

            //ApplicationInsightsInitializer.getTelemetryClient().trackRequest(requestTelemetry);
            //TELEMETRY_CLIENT.trackRequest(requestTelemetry);
            // trackEvent(tenantId, contactId, traceId);
            trackCustomPropertiesUnderEvent(tenantId, contactId, traceId);
            trackCustomPropertiesUnderMetric(tenantId, contactId, traceId);


        } catch (Exception e) {
            logger.info("Failed to add custom properties");
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private void trackCustomPropertiesUnderEvent(String tenantId, String contactId, String traceId) {
        logger.info("Entered inside event telemetry");
        TelemetryClient singleInstanceTelemetryClient = TelemetryClientSingleton.getInstance();
        TelemetryContext context = singleInstanceTelemetryClient.getContext();
        EventTelemetry eventTelemetry = new EventTelemetry("Request Event");
        singleInstanceTelemetryClient.trackEvent(eventTelemetry);
        context.getProperties().put("value1", tenantId);
        context.getProperties().put("value2", traceId);
        context.getProperties().put("value3", contactId);
        singleInstanceTelemetryClient.flush();
        logger.info("Completed event telemetry");

    }

    private void trackCustomPropertiesUnderMetric(String tenantId, String contactId, String traceId) {
        logger.info("Entered inside event telemetry");
        TelemetryClient singleInstanceTelemetryClient = TelemetryClientSingleton.getInstance();
        TelemetryContext context = singleInstanceTelemetryClient.getContext();
        MetricTelemetry metricTelemetry = new MetricTelemetry("RequestMetric", 15);
        singleInstanceTelemetryClient.trackMetric(metricTelemetry);
        context.getProperties().put("key1", tenantId);
        context.getProperties().put("key2", traceId);
        context.getProperties().put("key3", contactId);
        singleInstanceTelemetryClient.flush();
        logger.info("Completed metric telemetry");

    }
}

