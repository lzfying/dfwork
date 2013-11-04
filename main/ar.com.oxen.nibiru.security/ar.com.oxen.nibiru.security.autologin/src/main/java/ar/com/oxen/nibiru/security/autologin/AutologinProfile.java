package ar.com.oxen.nibiru.security.autologin;

import ar.com.oxen.nibiru.security.api.Profile;

public class AutologinProfile implements Profile {
	public static final String USERNAME = "Nibiru";

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public String getUsername() {
		return USERNAME;
	}

	@Override
	public String getFirstName() {
		return null;
	}

	@Override
	public String getLastName() {
		return null;
	}

	@Override
	public void activate(String username, String firstName, String lastName) {
	}

	@Override
	public void deactivate() {
	}
}
