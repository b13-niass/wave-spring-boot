package com.example.wavespringboot.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.wavespringboot.web.filter.wrapper.ResponseWrapper;

import java.io.IOException;
import java.io.PrintWriter;

public class ResponseFormattingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // Skip filtering for Swagger paths
        if (isSwaggerPath(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        // Use ResponseWrapper to capture response
        ResponseWrapper responseWrapper = new ResponseWrapper(httpResponse);
        chain.doFilter(httpRequest, responseWrapper);

        // If the status is an error (>= 400), do not reformat response
        if (httpResponse.getStatus() >= 400) {
            httpResponse.getWriter().write(responseWrapper.getCaptureAsString());
            return;
        }

        // Successful responses: format response
        String originalResponseBody = responseWrapper.getCaptureAsString();
        String formattedResponseBody = formatResponse("OK", originalResponseBody, true);

        httpResponse.setContentType("application/json");
        httpResponse.setContentLength(formattedResponseBody.length());
        PrintWriter out = httpResponse.getWriter();
        out.write(formattedResponseBody);
        out.flush();
    }

    @Override
    public void destroy() {}

    private boolean isSwaggerPath(String requestURI) {
        // Adjusted to cover common Swagger paths
        return requestURI.contains("swagger-ui") ||
                requestURI.contains("v3/api-docs") ||
                requestURI.contains("swagger-resources");
    }

    private String formatResponse(String status, String originalResponseBody, boolean isSuccess) {
        String data = isSuccess ? originalResponseBody : "null";
        String message = isSuccess ? "Request processed successfully" : "An error occurred";

        return String.format("{\"status\": \"%s\", \"data\": %s, \"message\": \"%s\"}", status, data, message);
    }
}
