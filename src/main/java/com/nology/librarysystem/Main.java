package com.nology.librarysystem;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Library myLibrary = new Library();
        myLibrary.newUser("Sean Pursani", AuthenticationType.ADMINISTRATOR);
        myLibrary.newUser("Ruth Campbell", AuthenticationType.PUBLIC);
        myLibrary.newUser("Des Campbell", AuthenticationType.PUBLIC);
        myLibrary.loanBook(2, "10");
        myLibrary.showAvailableBooks(2);
//        myLibrary.returnBook(2, "10");
    }
}
