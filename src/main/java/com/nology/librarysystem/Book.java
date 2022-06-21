package com.nology.librarysystem;

import com.opencsv.bean.CsvBindByName;

public class Book {
    @CsvBindByName(column = "Number")
    private String bookID;
    @CsvBindByName(column = "Title")
    private String title;
    @CsvBindByName(column = "Author")
    private String author;
    @CsvBindByName(column = "Genre")
    private String genre;
    @CsvBindByName(column = "SubGenre")
    private String subGenre;
    @CsvBindByName(column = "Publisher")
    private String publisher;

    public Book() {
    }

    public String getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookID='" + bookID + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", subGenre='" + subGenre + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
