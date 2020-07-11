package com.example.crisol.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    String id, name, lastname, email;
    List<String> favoriteBooks;

    public User(String id, String name, String lastname, String email, List<String> favoriteBooks) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.favoriteBooks = favoriteBooks;
    }

    public User(String id, String name, String lastname, String email) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }

    public User() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavoriteBooks(List<String> favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    public void setFavoriteBook(String bookId) {
        if (this.favoriteBooks == null) {
            this.favoriteBooks = new ArrayList<String>();
        }
        this.favoriteBooks.add(bookId);
    }
}
