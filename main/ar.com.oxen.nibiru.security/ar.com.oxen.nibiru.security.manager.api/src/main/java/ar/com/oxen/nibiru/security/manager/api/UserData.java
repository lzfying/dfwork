package ar.com.oxen.nibiru.security.manager.api;

public interface UserData {
	String getUsername();

	String getPassword();

	String getFirstName();

	String getLastName();

	Iterable<String> getRoles();
}
