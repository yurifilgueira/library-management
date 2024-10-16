package com.library.entities.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table
public class Loan implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Column
    private Date loanDate;
    @Column
    private Date dueDate;

    public Loan() {
    }

    public Loan(String id, User customer, Book book, Date loanDate, Date dueDate) {
        this.id = id;
        this.customer = customer;
        this.book = book;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Objects.equals(id, loan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
