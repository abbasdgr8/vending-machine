package com.abbasdgr8.vendingmachine;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Simple Spring-Boot Application class which starts up a Web Container and
 * Spring (and thereby CXF and the VendingMachine)
 *
 * @author Abbas Attarwala
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    
    /**
     * The main method that starts up the spring-boot app.
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run("classpath:/META-INF/spring/server.xml", args);
    }
    
    /**
     * Registers CXF Servlet that handles the REST requests with the Spring context
     *
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean CXFServletRegistrationBean() {
        CXFServlet cxfServlet = new CXFServlet();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(cxfServlet, "/*");
        LOGGER.info("CXFServlet registered");
        return servletRegistrationBean;
    }
    
}
