package com.jwt.model;


public class JwtResponse {
	
	private String jwtToken;
	private String username;
	public JwtResponse(String jwtToken, String username) {
		super();
		this.jwtToken = jwtToken;
		this.username = username;
	}
	public JwtResponse(Builder builder) {
		this.jwtToken =  builder.jwtToken;
		this.username =  builder.username;
	}
	public String getJwtToken() {
		return jwtToken;
	}
	public JwtResponse setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
		return this;
	}
	public String getUsername() {
		return username;
	}
	public JwtResponse setUsername(String username) {
		this.username = username;
		return this;
	}
	@Override
	public String toString() {
		return "JwtResponse [jwtToken=" + jwtToken + ", username=" + username + "]";
	}
	 public static Builder builder() {
	        return new Builder();
	    }
	public static class Builder {
        private String jwtToken;
        private String username;

        private Builder() {
        }

        public Builder jwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public JwtResponse build() {
            return new JwtResponse(this);
        }
    }
	
	

}
