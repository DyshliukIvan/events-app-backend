package com.eventapp.backend.auth;

public class LoginRequest {

    private String email;
    private String password;
    private String deviceId;
    private String deviceType;

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }
}
