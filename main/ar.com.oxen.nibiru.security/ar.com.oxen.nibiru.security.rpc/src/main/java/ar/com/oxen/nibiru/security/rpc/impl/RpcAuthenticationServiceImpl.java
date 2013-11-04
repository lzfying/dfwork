package ar.com.oxen.nibiru.security.rpc.impl;

import ar.com.oxen.nibiru.security.rpc.LoginDto;
import ar.com.oxen.nibiru.security.rpc.RpcAuthenticationService;
import ar.com.oxen.nibiru.security.rpc.UserDto;
import ar.com.oxen.nibiru.security.api.AuthenticationService;
import ar.com.oxen.nibiru.security.api.BadCredentialsException;
import ar.com.oxen.nibiru.security.api.Profile;

public class RpcAuthenticationServiceImpl implements RpcAuthenticationService {
	private AuthenticationService authenticationService;
	private Profile profile;

	@Override
	public UserDto login(LoginDto loginDto) {
		try {
			this.authenticationService.login(loginDto.getUsername(),
					loginDto.getPassword());
			return new UserDto(this.profile.getFirstName(),
					this.profile.getLastName());
		} catch (BadCredentialsException e) {
			return null;
		}
	}

	public void setAuthenticationService(
			AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
