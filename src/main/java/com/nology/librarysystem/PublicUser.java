package com.nology.librarysystem;

import java.util.List;

public class PublicUser extends User {
    private List<Book> currentlyLoaned;

    public PublicUser(String name, String dateRegistered, AuthenticationType authenticationType) {
        super(name, dateRegistered, authenticationType);
    }
}
