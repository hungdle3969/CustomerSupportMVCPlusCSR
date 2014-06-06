package com.hung.le.site;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/*
 * SessionListener is instantiated and managed by the Servlet container, not by Spring Framework. 
 * That's why SessionRegistry used to be a class of static methods. We need them converted to Spring 
 * beans automatically upon instantiation. We can use the option by programmatically add an existing
 *  object to the Spring application context at run time
 *  - Remove @WebListener because the order in which the listener is invoked can be unpredictable with this annotation
 *  - We need to change SessionListener so that it also implements ServletContextListener. 
 *  This way the SessionListener can initialize itself within Spring when the container starts up, right after Spring starts up.
 *  - Use the contextInitialized method in the listener to get the root application context from the ServletContext, 
 *  retrieve the bean factory from the application context, and configure the SessionListener instance as a bean 
 *  in the root application context
 * 
 */
// @WebListener //  remove this because the order in which the listener is invoked can be unpredictable with this annotation
public class SessionListener implements HttpSessionListener, HttpSessionIdListener, ServletContextListener{
	
	private static final Logger log = LogManager.getLogger();
	
	@Inject SessionRegistry sessionRegistry;

	@Override
	public void sessionIdChanged(HttpSessionEvent event, String oldSessionId) {
		log.debug("Session ID " + oldSessionId + " changed to " + event.getSession().getId());
		this.sessionRegistry.updateSessionId(event.getSession(), oldSessionId);
	}

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		log.debug("Session " + event.getSession().getId() + " created.");
        this.sessionRegistry.addSession(event.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		log.debug("Session " + event.getSession().getId() + " destroyed.");
        this.sessionRegistry.removeSession(event.getSession());
	}
	
	/*
	 * When the contextInitialized method completes execution, the SessionRegistry 
	 * implementation is injected, and the SessionListener can begin using it immediately
	 */
	@Override
	public void contextInitialized(ServletContextEvent event){
		
		WebApplicationContext context =
                WebApplicationContextUtils.getRequiredWebApplicationContext(
                        event.getServletContext());
        AutowireCapableBeanFactory factory =
                context.getAutowireCapableBeanFactory();
        factory.autowireBeanProperties(this,
                AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
        factory.initializeBean(this, "sessionListener");
        log.info("Session listener initialized in Spring application context.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
