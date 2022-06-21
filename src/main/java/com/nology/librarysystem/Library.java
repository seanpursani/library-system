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

public class Library {
    private final static String filePathJSON = "books.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

    public Library() {
    }

    static {
        try {
            List<Book> rawBooks = new CsvToBeanBuilder(new FileReader("CSVdata.csv")).withType(Book.class).build().parse();
            File fileRef = new File(filePathJSON);
            writer.writeValue(Paths.get(filePathJSON).toFile(), rawBooks);
            System.out.println("Successfully written to JSON file 'books.json'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User createUser(String name, AuthenticationType authenticationType) throws IOException {
        String date = getDate();
        User newUser;
        if (authenticationType == AuthenticationType.ADMINISTRATOR) {
            newUser = new AdminUser(name, date, authenticationType);
        } else {
            newUser = new PublicUser(name, date, authenticationType);
        }
        writer.writeValue(new File("users.json"), newUser);
        return newUser;
    }

    private String getDate() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return date.format(myFormatObj);
    }


}
