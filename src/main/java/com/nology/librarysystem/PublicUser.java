package com.nology.librarysystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PublicUser extends User {
    private final static String filePathJSON = "books.json";
    private final Scanner s = new Scanner(System.in);
    private final List<Book> currentlyLoaned = new ArrayList<>();

    public PublicUser(String name, String dateRegistered, AuthenticationType authenticationType) {
        super(name, dateRegistered, authenticationType);
    }

    public void checkAvailableBooks() throws IOException {
        List<Book> books = Library.bookObjList();
        List<Book> filteredBooks = books.stream().filter(book -> !book.isCurrentlyLoaned()).collect(Collectors.toList());
        Library.writeToJsonFile("available-books.json", filteredBooks);
        System.out.println("Please the check the available-books.json file to see a list of our available books!");
    }

    public void loanBook() throws IOException {
        System.out.println("Please enter book title");
        String userInput = s.nextLine();
        List<Book> requestedBook = Library.bookObjList().stream().filter(book -> Objects.equals(book.getTitle(), userInput)).collect(Collectors.toList());
        if (requestedBook.size() == 0) throw new IllegalArgumentException("Invalid title entered");
        currentlyLoaned.add(requestedBook.get(0));
        System.out.println("Thank you for loaning... \n" + requestedBook.get(0).toString() + "\nEnjoy!");
    }

    public void getCurrentlyLoaned() {
        System.out.println("You are currently loaning...\n" + currentlyLoaned);
    }
}
