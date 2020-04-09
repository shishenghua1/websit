package com.boco.eoms.websit.oauth2.model;


public class PrincipalUser {
	private Authoritie[] authorities;
	private Details details;
	private boolean authenticated;
	private TawEomsSystemUserDetail principal;
	private String credentials;
	private boolean clientOnly;
	private String name;
	public Authoritie[] getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Authoritie[] authorities) {
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
	public boolean isClientOnly() {
		return clientOnly;
	}
	public void setClientOnly(boolean clientOnly) {
		this.clientOnly = clientOnly;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
