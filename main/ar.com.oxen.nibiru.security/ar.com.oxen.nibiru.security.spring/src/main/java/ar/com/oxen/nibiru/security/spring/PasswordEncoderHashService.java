package ar.com.oxen.nibiru.security.spring;

import org.springframework.security.authentication.encoding.PasswordEncoder;

import ar.com.oxen.nibiru.security.api.HashService;

public class PasswordEncoderHashService implements HashService {
	private PasswordEncoder passwordEncoder;

	@Override
	public String hash(String data) {
		return this.passwordEncoder.encodePassword(data, null);
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
}
