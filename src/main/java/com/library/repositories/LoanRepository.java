package com.library.repositories;

import com.library.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, String> {
    List<Loan> findByUserId(String userId);
    List<Loan> findByBookId(String bookId);
}