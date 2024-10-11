package com.library.repositories;

import com.library.entities.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, String> {
    List<Loan> findLoansByCustomerId(String userId);
    List<Loan> findLoansByBookId(String bookId);
}