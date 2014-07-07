package com.hung.le.site;

import java.security.Principal;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private static final Logger log = LogManager.getLogger();
	
	@Inject UserRepository userRepository;
	
	@Override
	public Principal authenticate(String username, String password) {

		log.info("authenticationService is invoked");
		String currentPassword = this.userRepository.getPasswordForUser(username);
		
		if(currentPassword == null){
			log.warn("Authentication failed for non-existent user {}.", username);
			return null;
		}
		
		if(!currentPassword.equals(password)){
			log.warn("Authentication failed for user {}.", username);
			return null;
		}
		
		log.debug("User {} successfully authenticated.", username);
		
		return new UserPrincipal(username);
	}

}
