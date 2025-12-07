/*
 * Book.java
 * Represents a book in the library.
 */

import java.util.Objects;

public class Book implements Comparable<Book> {
    private int bookId;
    private String title;
    private String author;
    private String category;
    private boolean isIssued;

    public Book(int bookId, String title, String author, String category, boolean isIssued) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isIssued = isIssued;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void markAsIssued() {
        this.isIssued = true;
    }

    public void markAsReturned() {
        this.isIssued = false;
    }

    public void displayBookDetails() {
        System.out.println("ID: " + bookId + ", Title: " + title + ", Author: " + author + ", Category: " + category + ", Issued: " + isIssued);
    }

    @Override
    public int compareTo(Book other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    @Override
    public String toString() {
        // Use '|' as delimiter; issued flag as true/false
        return bookId + "|" + title + "|" + author + "|" + category + "|" + isIssued;
    }

    public static Book fromString(String line) {
        // Expected format: id|title|author|category|isIssued
        String[] parts = line.split("\\|", -1);
        if (parts.length < 5) return null;
        try {
            int id = Integer.parseInt(parts[0]);
            String title = parts[1];
            String author = parts[2];
            String category = parts[3];
            boolean iss = Boolean.parseBoolean(parts[4]);
            return new Book(id, title, author, category, iss);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return bookId == book.bookId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId);
    }
}
