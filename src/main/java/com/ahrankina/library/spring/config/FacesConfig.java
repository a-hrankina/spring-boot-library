package com.ahrankina.library.spring.config;

import com.ahrankina.library.jsfui.servlets.PdfContent;
import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.faces.webapp.FacesServlet;
import javax.servlet.MultipartConfigElement;

@Configuration
public class FacesConfig {

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> {
            servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
            servletContext.setInitParameter("primefaces.THEME", "bootstrap");
            servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");
            servletContext.setInitParameter("primefaces.UPLOADER", "commons");
        };
    }

    @Bean
    public FilterRegistrationBean fileUploadFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.addUrlPatterns("*.xhtml", "/javax.faces.resource/*");
        registrationBean.setFilter(new FileUploadFilter());
        return registrationBean;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(MultipartConfigElement multipartConfigElement) {
        FacesServlet servlet = new FacesServlet();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "*.xhtml", "/javax.faces.resource/*");
        servletRegistrationBean.setMultipartConfig(multipartConfigElement);
        return servletRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean pdfServletRegistraiton() {
        ServletRegistrationBean pdfServlet = new ServletRegistrationBean(new PdfContent(), new String[]{"/PdfContent"});
        pdfServlet.setName("PdfContent");
        return pdfServlet;
    }
}
