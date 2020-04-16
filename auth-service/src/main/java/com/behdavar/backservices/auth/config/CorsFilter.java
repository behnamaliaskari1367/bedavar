package com.behdavar.backservices.auth.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

@Component
public class CorsFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(CorsFilter.class);

    private static final String VALID_METHODS = "DELETE, HEAD, GET, OPTIONS, POST, PUT";

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        EntryMessage traceEntry = LOGGER.traceEntry("servletRequest: {}, servletResponse:{}, filterChain:{}", req, resp, chain);

        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;

        // No Origin header present means this is not a cross-domain request
        String origin = httpReq.getHeader("Origin");
        if (origin == null) {
            // Return standard response if OPTIONS request w/o Origin header
            if ("OPTIONS".equalsIgnoreCase(httpReq.getMethod())) {
                httpResp.setHeader("Allow", VALID_METHODS);
                httpResp.setStatus(200);
                return;
            }
        }

        // Pass request down the chain, except for OPTIONS
        if (!"OPTIONS".equalsIgnoreCase(httpReq.getMethod())) {
            chain.doFilter(req, resp);
        }

        if (Objects.nonNull(origin)) {
            setHeader(httpResp, "Access-Control-Allow-Origin", origin);
            setHeader(httpResp, "Access-Control-Allow-Methods", VALID_METHODS);
            setHeader(httpResp, "Access-Control-Allow-Credentials", "true");

            String headers = httpReq.getHeader("Access-Control-Request-Headers");
            if (headers != null) {
                setHeader(httpResp, "Access-Control-Allow-Headers", headers);
            }
            // Allow caching cross-domain permission
            setHeader(httpResp, "Access-Control-Max-Age", "3600");
        }

        LOGGER.traceExit(traceEntry);
    }

    private void setHeader(HttpServletResponse httpResp, String name, String value) {
        EntryMessage traceEntry = LOGGER.traceEntry("httpResp: {}, name:{}, value:{}", httpResp, name, value);
        Collection<String> headers = httpResp.getHeaders(name);
        if (Objects.isNull(headers) || headers.isEmpty()) {
            httpResp.setHeader(name, value);
        }
        LOGGER.traceExit(traceEntry);
    }

}
