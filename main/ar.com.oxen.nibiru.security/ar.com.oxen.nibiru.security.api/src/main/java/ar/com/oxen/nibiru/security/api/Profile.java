package ar.com.oxen.nibiru.security.api;

public interface Profile {
	boolean isActive();

	String getUsername();

	String getFirstName();

	String getLastName();

	void activate(String username, String firstName, String lastName);

	void deactivate();
}
