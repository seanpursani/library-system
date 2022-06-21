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

public class Library {
    private final List<User> allUsers = new ArrayList<>();
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
            System.out.println("Successfully written to " + filePathJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AdminUser newAdminUser(String name) throws IOException {
            AdminUser adminUser = new AdminUser(name, getDate(), AuthenticationType.ADMINISTRATOR);
            allUsers.add(adminUser);
            writeToJsonFile("users.json", allUsers);
            return adminUser;
    }

    public PublicUser newPublicUser(String name) throws IOException {
        PublicUser publicUser = new PublicUser(name, getDate(), AuthenticationType.PUBLIC);
        allUsers.add(publicUser);
        writeToJsonFile("users.json", allUsers);
        return publicUser;
    }

    public static List<Book> bookObjList() throws IOException {
        String filePath = Files.readString(Path.of(filePathJSON));
        return mapper.readValue(filePath, new TypeReference<>(){});
    }

    private String getDate() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return date.format(myFormatObj);
    }

    public void writeToJsonFile(String fileName, List items) {
        try {
            writer.writeValue(Paths.get(fileName).toFile(), items);
        } catch (IOException e){
            System.out.println(e);
        }
    }

}
