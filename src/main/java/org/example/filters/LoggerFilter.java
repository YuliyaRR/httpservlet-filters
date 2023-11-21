package org.example.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LoggerFilter implements Filter {
    private static Logger logger = Logger.getLogger(LoggerFilter.class);

    @Override
    public void init(FilterConfig filterConfig){
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            Collections
                    .list(req.getHeaderNames())
                    .stream()
                    .collect(Collectors.toMap(Function.identity(),
                            h -> Collections.list(req.getHeaders(h))))
                    .forEach((k, v) -> logger.info(k + " : " + v));

            String body = req.getReader().lines().collect(Collectors.joining());
            logger.info(body);
            request.setAttribute("body", body);
            chain.doFilter(request, response);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }
    }

    @Override
    public void destroy() {

    }
}
