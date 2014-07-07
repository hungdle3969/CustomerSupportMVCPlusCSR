package com.hung.le.site;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hung.le.config.annotation.WebController;


/*
 * This controller replaces the SessionListServlet. The code isn't really 
 * that different except that Spring MVC patterns are used instead of 
 * HttpServletRequest and HttpServletResponse tools
 */
@WebController
@RequestMapping("session")
public class SessionListController {

	@Inject SessionRegistry sessionRegistry;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Map<String, Object> model){
		
		model.put("numberOfSessions", this.sessionRegistry.getNumberOfSessions());
		model.put("sessionList", this.sessionRegistry.getAllSessions());
		model.put("timestamp", System.currentTimeMillis());

		return "session/list";
	}
}
