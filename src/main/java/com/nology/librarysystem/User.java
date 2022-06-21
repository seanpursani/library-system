package com.nology.librarysystem;

import java.io.IOException;
import java.util.List;

public class User {
    private String name;
    private String dateRegistered;
    private AuthenticationType authenticationType;

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
