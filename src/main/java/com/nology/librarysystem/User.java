package com.nology.librarysystem;

public abstract class User {
    String name;
    String dateRegistered;
    AuthenticationType authenticationType;

    public User(String name, String dateRegistered, AuthenticationType authenticationType) {
        this.name = name;
        this.dateRegistered = dateRegistered;
        this.authenticationType = authenticationType;
    }

    public String getName() {
        return name;
    }

    public String getDateRegistered() {
        return dateRegistered;
    }

    public AuthenticationType getAuthenticationType() {
        return authenticationType;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", dateRegistered='" + dateRegistered + '\'' +
                ", authenticationType=" + authenticationType +
                '}';
    }
}
