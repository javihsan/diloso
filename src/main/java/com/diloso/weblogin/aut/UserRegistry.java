package com.diloso.weblogin.aut;


public interface UserRegistry {
	AppUser findUser(String email);

	void registerUser(AppUser newUser);

	void removeUser(String email);
}
