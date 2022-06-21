package com.nology.librarysystem;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private final List<User> users = new ArrayList<>();
    private final static String booksJSON = "books.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

    public Library() throws IOException {
    }

    static {
        try {
            List<Book> rawBooks = new CsvToBeanBuilder(new FileReader("CSVdata.csv")).withType(Book.class).build().parse();
            List<Item> booksAsItems = rawBooks.stream().map(Item::new).collect(Collectors.toList());
            File fileRef = new File(booksJSON);
            writer.writeValue(Paths.get(booksJSON).toFile(), booksAsItems);
            System.out.println("Successfully written to JSON file 'books.json'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User createUser(String name, AuthenticationType authenticationType) {
        String date = getDate();
        User newUser;
        if (authenticationType == AuthenticationType.PUBLIC) {
            newUser = new PublicUser(name, date, authenticationType);
        } else {
            newUser = new AdminUser(name, date, authenticationType);
        }
        users.add(newUser);
        return newUser;
    }

    private String getDate() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return date.format(myFormatObj);
    }

    public List<User> getUsers(User myUser) {
        return myUser.getAuthenticationType() == AuthenticationType.ADMINISTRATOR || myUser.getAuthenticationType() == AuthenticationType.ACCOUNTMANAGER ? users : new ArrayList<>();
    }


}
