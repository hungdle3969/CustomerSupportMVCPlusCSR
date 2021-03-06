/**
 * 
 */
package com.hung.le.site;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author admin
 *
 */
public class AuthenticationFilter implements Filter{

	private static final Logger log = LogManager.getLogger();
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		log.info("AUTHENTICATION FILTER HAS BEEN INVOKED.");
		HttpSession session = ((HttpServletRequest) request).getSession(false);
		
		final Principal principal = UserPrincipal.getPrincipal(session);
		
		if(principal == null){
			log.info("PRINCIPLE IS NULL");
			((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/login");
		}
		else{
			log.info("PRINCIPLE IS NOT NULL");
			chain.doFilter(
                    new HttpServletRequestWrapper((HttpServletRequest)request){
                        @Override
                        public Principal getUserPrincipal() {
                            return principal;
                        }
                    },
                    response
            );
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	
}
