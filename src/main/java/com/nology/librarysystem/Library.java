package com.nology.librarysystem;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Library {
    private final List<User> allUsers = new ArrayList<>();
    private static final List<Book> allBooks = new ArrayList<>();
    private final static String userFilePath = "users.json";
    private final static String bookFilePath = "books.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

    public Library() {
    }

    static {
        try {
            List<Book> rawBooks = new CsvToBeanBuilder(new FileReader("CSVdata.csv")).withType(Book.class).build().parse();
            File fileRef = new File(bookFilePath);
            writer.writeValue(Paths.get(bookFilePath).toFile(), rawBooks);
            System.out.println("Successfully written to " + bookFilePath);
            allBooks.addAll(bookObjList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newUser(String name, AuthenticationType userType) throws IOException {
        if (allUsers.size() == 0) {
            User newUser = new User(name, getDate(), userType, 1);
            allUsers.add(newUser);
        } else {
            int id = allUsers.get(allUsers.size()-1).getId();
            User newUser = new User(name, getDate(), userType, id + 1);
            allUsers.add(newUser);
        }
        writeToJsonFile("users.json", allUsers);
    }

    public static List<Book> bookObjList() throws IOException {
        String filePath = Files.readString(Path.of(bookFilePath));
        return mapper.readValue(filePath, new TypeReference<>(){});
    }

    private String getDate() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return date.format(myFormatObj);
    }

    public void showAvailableBooks(int userID) throws IOException {
        boolean isValidUser = allUsers.stream().
                anyMatch(user -> user.getId() == userID && user.getAuthenticationType() == AuthenticationType.PUBLIC);
        if (!isValidUser) throw new IllegalArgumentException("Invalid Operation, please try again");
        writeToJsonFile("available-books.json", allBooks.stream().filter(book -> !book.isCurrentlyLoaned()).collect(Collectors.toList()));
        System.out.println("Please check the JSON file 'available-books.json' to see what books you can loan!");
    }

    public void loanBook(int userID, String bookID) throws IOException {
        if (!isValidPublicUser(userID) && !isValidBook(bookID)) throw new IllegalArgumentException("Invalid operation, please try again");
        Book requestedBook = allBooks.remove(Integer.parseInt(bookID) -1);
        requestedBook.setAmountLoaned();
        requestedBook.setCurrentlyLoaned();
        allBooks.add(Integer.parseInt(bookID), requestedBook);
        allUsers.get(userID-1).addBook(requestedBook);
        writeToJsonFile(bookFilePath, allBooks);
        writeToJsonFile(userFilePath, allUsers);
        System.out.println("Thank you for loaning " + "'" + requestedBook.getTitle() + "'" + ". Enjoy!");
    }

    public boolean isValidPublicUser(int userID) {
        return allUsers.stream().
                anyMatch(user -> user.getId() == userID || user.getAuthenticationType() == AuthenticationType.PUBLIC);
    }

    public boolean isValidBook(String bookID) throws IOException {
        return Library.bookObjList().stream().
                anyMatch(book -> Objects.equals(book.getBookID(), bookID));
    }

    public void returnBook (int userID, String bookID) throws IOException {
        if (!isValidPublicUser(userID) && !isValidBook(bookID)) throw new IllegalArgumentException("Invalid operation, please try again");
        allUsers.get(userID-1).removeBook();


    }

    public static void writeToJsonFile(String fileName, List items) {
        try {
            writer.writeValue(Paths.get(fileName).toFile(), items);
        } catch (IOException e){
            System.out.println(e);
        }
    }



}
