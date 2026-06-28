package com.eventapp.backend.auth;

public class RegisterRequest {

    private String email;
    private String password;
    private String name;
    private String surname;
    private String deviceId;
    private String deviceType;

    public RegisterRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }
}
