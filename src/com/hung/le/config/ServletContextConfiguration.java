package com.hung.le.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebMvc
@ComponentScan(
		basePackages = "com.hung.le.site",
		useDefaultFilters = false,
		includeFilters = @ComponentScan.Filter(Controller.class)
)
public class ServletContextConfiguration extends WebMvcConfigurerAdapter{

	private static final Logger log = LogManager.getLogger();
	
	@Inject ObjectMapper objectMapper;
	@Inject Marshaller marshaller;
	@Inject Unmarshaller unmarshaller;
	
	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		
		log.info("MESSAGE CONVERTERS HAS BEEN CONFIGURED.");
		converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        converters.add(new FormHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter<>());

        MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
        xmlConverter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("application", "xml"),
                new MediaType("text", "xml")
        ));
        xmlConverter.setMarshaller(this.marshaller);
        xmlConverter.setUnmarshaller(this.unmarshaller);
        
        converters.add(xmlConverter);

        MappingJackson2HttpMessageConverter jsonConverter =
                new MappingJackson2HttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("application", "json"),
                new MediaType("text", "json")
        ));
        jsonConverter.setObjectMapper(this.objectMapper);
        
        converters.add(jsonConverter);
    }
	
	@Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer){
        
		log.info("CONTENT NEGOTIATION HAS BEEN CONFIGURED.");
		configurer.favorPathExtension(true).favorParameter(false)
                .parameterName("mediaType").ignoreAcceptHeader(false)
                .useJaf(false).defaultContentType(MediaType.APPLICATION_XML)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("json", MediaType.APPLICATION_JSON);
    }
	
	@Bean
	public ViewResolver viewResolver(){
		
		log.info("VIEW RESOLVER HAS BEEN CREATED.");
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/jsp/view/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Bean
	public RequestToViewNameTranslator viewNameTranslator(){
		
		log.info("VIEW NAME TRANSLATOR HAS BEEN CREATED.");
		return new DefaultRequestToViewNameTranslator();
	}
	
	@Bean
	public MultipartResolver multipartResolver(){
		
		log.info("MULTIPART RESOLVER HAS BEEN CREATED.");
		return new StandardServletMultipartResolver();
	}
}
