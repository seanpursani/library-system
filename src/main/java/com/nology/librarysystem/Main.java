package com.nology.librarysystem;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Library myLibrary = new Library();
        User myUser = myLibrary.createUser("Sean Pursani", AuthenticationType.ADMINISTRATOR);
        System.out.println(myLibrary.getUsers(myUser));
    }
}
