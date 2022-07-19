package com.nology.librarysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class LibrarySystemApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(LibrarySystemApplication.class, args);
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
