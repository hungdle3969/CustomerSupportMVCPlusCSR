package com.hung.le.site;

import java.util.Hashtable;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

	private final Map<String, String> userDatabase = new Hashtable<>();
	
	public UserRepositoryImpl(){
		userDatabase.put("hungle", "hungpass");
		userDatabase.put("lanle", "lanpass");
		userDatabase.put("chasele", "chasepass");
		userDatabase.put("audreyle", "audreypass");
		userDatabase.put("danhle", "danhpass");
	}
	@Override
	public String getPasswordForUser(String username) {

		return this.userDatabase.get(username);
	}

}
