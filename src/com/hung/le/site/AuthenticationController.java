package com.hung.le.site;

import java.security.Principal;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.hung.le.config.annotation.WebController;
import com.hung.le.site.validation.NotBlank;

/*
 * This filter replaces LoginServlet
 */
@WebController
public class AuthenticationController {

	private static final Logger log = LogManager.getLogger();
	
	@Inject AuthenticationService authenticationService;
	
	@RequestMapping("logout")
    public View logout(HttpServletRequest request, HttpSession session)
    {
        if(log.isDebugEnabled() && request.getUserPrincipal() != null)
            log.debug("User {} logged out.", request.getUserPrincipal().getName());
        session.invalidate();

        return new RedirectView("/login", true, false);
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login(Map<String, Object> model, HttpSession session)
    {
    	log.debug("AUTHENTICATION CONTROLLER - LOGIN GET HAS BEEN INVOKED.");
    	if(UserPrincipal.getPrincipal(session) != null)
            return this.getTicketRedirect();

        model.put("loginFailed", false);
        //attribute name must match with formbean which is LoginForm
        model.put("loginForm", new LoginForm());

        return new ModelAndView("login");
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView login(Map<String, Object> model, HttpSession session,
                              HttpServletRequest request, @Valid LoginForm form, Errors errors)
    {
    	log.debug("AUTHENTICATION CONTROLLER - LOGIN POST HAS BEEN INVOKED.");
    	
        if(UserPrincipal.getPrincipal(session) != null)
            return this.getTicketRedirect();
        
        if(errors.hasErrors()){
        	log.info("Errors has error");
        	form.setPassword(null);
        	return new ModelAndView("login");
        }
        
        Principal principal;
        
        try{
        	log.info("Try principle");
        	principal = this.authenticationService.authenticate(
        			form.getUsername(), form.getPassword()
        		);
        	log.info("Done principle validation");
        }
        catch(ConstraintViolationException e){
        	log.info("catch principle");
        	form.setPassword(null);
        	model.put("validationErrors", e.getConstraintViolations());
        	return new ModelAndView("login");
        }

        if(principal == null){
        	log.info("principle == null");
            log.warn("Login failed for user {}.", form.getUsername());
            form.setPassword(null);
            model.put("loginFailed", true);
            model.put("loginForm", form);
            return new ModelAndView("login");
        }

        log.debug("User {} successfully logged in.", form.getUsername());
        UserPrincipal.setPrincipal(session, principal);
        request.changeSessionId();
        return this.getTicketRedirect();
    }

    private ModelAndView getTicketRedirect()
    {
        return new ModelAndView(new RedirectView("/ticket/list", true, false));
    }
	
	public static class LoginForm{
		
		@NotBlank(message = "{validate.authenticate.username}")
		private String username;
		@NotBlank(message = "{validate.authenticate.password}")
		private String password;
		
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
		
	}
	
}
