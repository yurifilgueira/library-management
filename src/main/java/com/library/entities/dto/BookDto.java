package com.library.entities.dto;

import java.io.Serializable;

public class BookDto implements Serializable {

    private String id;
    private String title;
    private String author;
    private Integer quantity;

    public BookDto() {
    }

    public BookDto(String id, String title, String author, Integer quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}