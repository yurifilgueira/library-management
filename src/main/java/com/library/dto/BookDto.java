package com.library.dto;

import java.io.Serializable;

public record BookDto(String id, String title, String author) implements Serializable {
}