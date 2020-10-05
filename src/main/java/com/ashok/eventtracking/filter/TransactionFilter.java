package com.ashok.eventtracking.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author ashok
 * 03/10/20
 */
@Component
@Order(2)
public class TransactionFilter implements Filter {

    private final Logger LOG = LoggerFactory.getLogger(TransactionFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        LOG.debug(
                "Starting a transaction for req : {}",
                req.getRequestURI());

        filterChain.doFilter(servletRequest, servletResponse);
        LOG.debug(
                "Committing a transaction for req : {}",
                req.getRequestURI());
    }
}
