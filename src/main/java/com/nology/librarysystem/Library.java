package com.nology.librarysystem;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public void createNewUser(String name, AuthenticationType userType) throws IOException {
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

    public void showAvailableBooks(int userID) throws IOException {
        boolean isValidUser = allUsers.stream().
                anyMatch(user -> user.getId() == userID && user.getAuthenticationType() == AuthenticationType.PUBLIC);
        if (!isValidUser) throw new IllegalArgumentException("Please enter a valid User ID");
        writeToJsonFile("available-books.json", allBooks.stream().filter(book -> !book.isCurrentlyLoaned()).collect(Collectors.toList()));
        System.out.println("Please check the file named 'available-books.json' to check out what books are available to loan!");
    }

    public void loanBook(int userID, String bookID) throws IOException {
        if (!isPublicUser(userID) && !isValidBook(bookID)) throw new IllegalArgumentException("Please enter a valid user ID and/or book ID");
        Book requestedBook = allBooks.remove(Integer.parseInt(bookID) -1);
        requestedBook.setAmountLoaned();
        requestedBook.setCurrentlyLoaned();
        allBooks.add(Integer.parseInt(bookID), requestedBook);
        allUsers.get(userID-1).addBook(requestedBook);
        writeToJsonFile(bookFilePath, allBooks);
        writeToJsonFile(userFilePath, allUsers);
        System.out.println("Thank you for loaning " + "'" + requestedBook.getTitle() + "'" + ". Enjoy the read!");
    }


    public void returnBook (int userID, String bookID) throws IOException {
        if (!isPublicUser(userID) && !isValidBook(bookID)) throw new IllegalArgumentException("Please enter a valid user ID and/or book ID");
        allUsers.get(userID-1).removeBook(bookID);
        writeToJsonFile(userFilePath, allUsers);
        allBooks.get(Integer.parseInt(bookID)-1).setCurrentlyLoaned();
        writeToJsonFile(bookFilePath, allBooks);
    }

    public void viewAllUsers(int userID) {
        if (!isAdminUser(userID)) throw new IllegalArgumentException("This user doesn't have the correct permissions to view this information");
        System.out.println(allUsers);
    }

    public void viewUser(int userID, int searchID) {
        if (!isAdminUser(userID)) throw new IllegalArgumentException("This user doesn't have the correct permissions to view this information");
        System.out.println(allUsers.stream().filter(user -> user.getId() == searchID).collect(Collectors.toList()));
    }

    public boolean isPublicUser(int userID) {
        return allUsers.stream().
                anyMatch(user -> user.getId() == userID && user.getAuthenticationType() == AuthenticationType.PUBLIC);
    }

    public boolean isAdminUser(int userID) {
        return allUsers.stream().
                anyMatch(user -> user.getId() == userID && user.getAuthenticationType() == AuthenticationType.ADMINISTRATOR);
    }

    public boolean isValidBook(String bookID) throws IOException {
        return Library.bookObjList().stream().
                anyMatch(book -> Objects.equals(book.getBookID(), bookID));
    }

    private static List<Book> bookObjList() throws IOException {
        String filePath = Files.readString(Path.of(bookFilePath));
        return mapper.readValue(filePath, new TypeReference<>(){});
    }

    private static void writeToJsonFile(String fileName, List items) {
        try {
            writer.writeValue(Paths.get(fileName).toFile(), items);
        } catch (IOException e){
            System.out.println(e);
        }
    }

    private String getDate() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return date.format(myFormatObj);
    }

}
