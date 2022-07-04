package com.nology.librarysystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private String dateRegistered;
    private AuthenticationType authenticationType;
    private List<Book> currentlyLoaned = new ArrayList<>();

    public User(String name, String dateRegistered, AuthenticationType authenticationType, int id) {
        this.name = name;
        this.dateRegistered = dateRegistered;
        this.authenticationType = authenticationType;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public List<Book> getCurrentlyLoaned() {
        return currentlyLoaned;
    }

    public void addBook(Book bookToLoan) {
        this.currentlyLoaned.add(bookToLoan);
    }

    public void removeBook(String bookID) {
        try {
            currentlyLoaned.removeIf(id -> id == bookID);
        }
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
