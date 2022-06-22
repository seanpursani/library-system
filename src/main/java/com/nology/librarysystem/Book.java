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
    private boolean currentlyLoaned = false;
    private int amountLoaned = 0;

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

    public String getSubGenre() {
        return subGenre;
    }

    public boolean isCurrentlyLoaned() {
        return currentlyLoaned;
    }

    public int getAmountLoaned() {
        return amountLoaned;
    }

    public void setAmountLoaned() {
        this.amountLoaned += 1;
    }

    public void setCurrentlyLoaned() {
        this.currentlyLoaned = !currentlyLoaned;
    }

    @Override
    public String toString() {
        return "Book ID= '" + bookID + '\'' +
                ", Title= '" + title + '\'' +
                ", Author= '" + author + '\'' +
                ", Genre= '" + genre + '\'' +
                ", Sub Genre= '" + subGenre + '\'' +
                ", Publisher= '" + publisher + '\'';
    }
}
