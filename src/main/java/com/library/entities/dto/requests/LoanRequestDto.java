package com.library.entities.dto.requests;

import java.util.Date;

public class LoanRequestDto {

    private String id;
    private String customerId;
    private String bookId;
    private Date loanDate;
    private Date dueDate;

    public LoanRequestDto() {
    }

    public LoanRequestDto(String id, String customerId, String bookId, Date loanDate, Date dueDate) {
        this.id = id;
        this.customerId = customerId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
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
}
