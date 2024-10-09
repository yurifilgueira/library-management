package com.library.services;

import com.library.entities.Loan;
import com.library.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public Optional<Loan> findById(String id) {
        return loanRepository.findById(id);
    }

    public Loan save(Loan loan) {
        return loanRepository.save(loan);
    }

    public void deleteById(String id) {
        loanRepository.deleteById(id);
    }
}
