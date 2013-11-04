package ar.com.oxen.nibiru.security.generic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ar.com.oxen.nibiru.security.api.HashService;

public class MessageDigestHashService implements HashService {
	private MessageDigest messageDigest;

	@Override
	public String hash(String data) {
		return new String(this.messageDigest.digest(data.getBytes()));
	}

	public void setAlgorithm(String algorithm) {
		try {
			this.messageDigest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Invalid alfgorithm: "
					+ algorithm, e);
		}
	}
}
