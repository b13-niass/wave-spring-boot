package com.example.wavespringboot.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.wavespringboot.web.filter.wrapper.ResponseWrapper;

import java.io.IOException;
import java.io.PrintWriter;

public class ResponseFormattingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        if (isSwaggerPath(requestURI)) {
            chain.doFilter(request, response);
        } else {
            ResponseWrapper responseWrapper = new ResponseWrapper(httpResponse);

            // Process the request through the filter chain
            chain.doFilter(httpRequest, responseWrapper);

            String originalResponseBody = responseWrapper.getCaptureAsString();
            boolean isSuccess = httpResponse.getStatus() < 400;
            String status = isSuccess ? "OK" : "KO";

            String formattedResponseBody = formatResponse(status, originalResponseBody, isSuccess);

            httpResponse.setContentType("application/json");
            PrintWriter out = httpResponse.getWriter();
            out.write(formattedResponseBody);
            out.flush();
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }

    private boolean isSwaggerPath(String requestURI) {
        return requestURI.startsWith("/api/v1/swagger-ui/") ||
                requestURI.startsWith("/api/v1/v3/api-docs") ||
                requestURI.startsWith("/api/v1/swagger-ui.html") ||
                requestURI.startsWith("/api/v1/swagger-resources/");
    }

    private String formatResponse(String status, String originalResponseBody, boolean isSuccess) {
        String data = isSuccess ? originalResponseBody : "null";
        String message = isSuccess ? "Request processed successfully" : originalResponseBody;

        return String.format("{\"status\": \"%s\", \"data\": %s, \"message\": \"%s\"}", status, data, message);
    }
}
