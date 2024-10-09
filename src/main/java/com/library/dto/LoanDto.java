package com.library.dto;

import java.io.Serializable;
import java.util.Date;

public record LoanDto(String id, UserDto user, BookDto book, Date loanDate, Date dueDate) implements Serializable {
}