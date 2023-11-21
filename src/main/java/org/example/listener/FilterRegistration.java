package org.example.listener;

import org.example.filters.AuthFilter;
import org.example.filters.LoggerFilter;
import org.example.filters.RoleUpdateFilter;
import org.example.filters.SecurityFilter;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class FilterRegistration implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        servletContext.addFilter("LoggerFilter", LoggerFilter.class).addMappingForUrlPatterns(null, true, "/users", "/roles");
        servletContext.addFilter("AuthFilter", AuthFilter.class).addMappingForUrlPatterns(null, true, "/users", "/roles");
        servletContext.addFilter("SecurityFilter", SecurityFilter.class).addMappingForUrlPatterns(null, true, "/users", "/roles");
        servletContext.addFilter("RoleUpdateFilter", RoleUpdateFilter.class).addMappingForUrlPatterns(null, true, "/users");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
