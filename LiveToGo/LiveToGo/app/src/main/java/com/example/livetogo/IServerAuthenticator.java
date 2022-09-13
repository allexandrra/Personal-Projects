package com.example.livetogo;

public interface IServerAuthenticator {
    public String signUp(final String email, final String password);
    public String logIn(final String email, final String password);
}
