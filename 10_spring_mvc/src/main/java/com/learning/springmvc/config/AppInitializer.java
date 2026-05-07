package com.learning.springmvc.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*
 * The Java replacement for web.xml.
 *
 * On Servlet 3.0+ containers (Tomcat 7+), the container scans for
 * implementations of WebApplicationInitializer and runs them at boot.
 * Spring's AbstractAnnotationConfigDispatcherServletInitializer is one
 * such implementation that:
 *
 *   1. Boots a Spring context from your @Configuration classes.
 *   2. Registers the DispatcherServlet at the URL pattern you choose.
 *
 * No web.xml. No <servlet-mapping>. The whole web app boots from this
 * one class.
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // "Root" context — services, repositories, infrastructure.
        // We use null and consolidate everything in WebConfig for this small example.
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };          // DispatcherServlet handles every URL
    }
}
