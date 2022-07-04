package com.nology.librarysystem;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Library myLibrary = new Library();
        myLibrary.createNewUser("Sean Pursani", AuthenticationType.ADMINISTRATOR);
        myLibrary.createNewUser("Ruth Campbell", AuthenticationType.PUBLIC);
        myLibrary.createNewUser("Desmond Campbell", AuthenticationType.PUBLIC);
        myLibrary.viewAllUsers(1);
        myLibrary.loanBook(2, "10");
        myLibrary.showAvailableBooks(2);
        myLibrary.returnBook(2, "8");
        myLibrary.viewUser(3, 2);
    }
}
