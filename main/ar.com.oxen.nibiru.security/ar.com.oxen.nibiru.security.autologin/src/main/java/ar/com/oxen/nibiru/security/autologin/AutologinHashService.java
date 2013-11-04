package ar.com.oxen.nibiru.security.autologin;

import ar.com.oxen.nibiru.security.api.HashService;

public class AutologinHashService implements HashService {
	@Override
	public String hash(String data) {
		return data;
	}
}
