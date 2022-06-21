package com.nology.librarysystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PublicUser extends User {
    private final Scanner s = new Scanner(System.in);
    private List<Book> currentlyLoaned = new ArrayList<>();

    public PublicUser(String name, String dateRegistered, AuthenticationType authenticationType) {
        super(name, dateRegistered, authenticationType);
    }

    public List<Book> checkAvailableBooks() throws IOException {
        List<Book> books = Library.bookObjList();
        return books.stream().filter(book -> !book.isCurrentlyLoaned()).collect(Collectors.toList());
    }

    public void loanBook() throws IOException {
        System.out.println("Please enter title");
        String userInput = s.nextLine();
        List<Book> requestedBook = Library.bookObjList().stream().filter(book -> Objects.equals(book.getTitle(), userInput)).collect(Collectors.toList());
        if (requestedBook.size() == 0) throw new IllegalArgumentException("Invalid title entered");
        requestedBook.get(0).setAmountLoaned();
        requestedBook.get(0).isCurrentlyLoaned();
        currentlyLoaned.add(requestedBook.get(0));
        System.out.println("Successfully loaned" + userInput);
    }
}
