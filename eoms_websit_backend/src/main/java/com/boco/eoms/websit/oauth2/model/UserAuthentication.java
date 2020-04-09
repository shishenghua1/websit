package com.boco.eoms.websit.oauth2.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication implements Authentication{

	private Collection<? extends GrantedAuthority> authorities;
	private Details details;
	private boolean authenticated;
	private TawEomsSystemUserDetail principal;
	private String credentials;
	private String name;
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	public Details getDetails() {
		return details;
	}
	public void setDetails(Details details) {
		this.details = details;
	}
	public boolean isAuthenticated() {
		return authenticated;
	}
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	public TawEomsSystemUserDetail getPrincipal() {
		return principal;
	}
	public void setPrincipal(TawEomsSystemUserDetail principal) {
		this.principal = principal;
	}
	public String getCredentials() {
		return credentials;
	}
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
