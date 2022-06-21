package com.nology.librarysystem;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Library myLibrary = new Library();
        AdminUser firstUser = myLibrary.newAdminUser("Sean Pursani");
        PublicUser secondUser = myLibrary.newPublicUser("Ruth Campbell");
        System.out.println(secondUser.checkAvailableBooks());
        secondUser.loanBook();
    }
}
