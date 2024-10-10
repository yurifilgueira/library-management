package com.library.services;

import com.library.dto.LoanDto;
import com.library.entities.Book;
import com.library.entities.Loan;
import com.library.entities.User;
import com.library.modelMapper.MyModelMapper;
import com.library.repositories.BookRepository;
import com.library.repositories.LoanRepository;
import com.library.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public List<LoanDto> findAll() {
        var loans = loanRepository.findAll();
        return MyModelMapper.convertList(loans, LoanDto.class);
    }

    public LoanDto findById(String id) {
        var loan =  loanRepository.findById(id).orElseThrow(NullPointerException::new);

        return MyModelMapper.convertValue(loan, LoanDto.class);
    }

    @Transactional
    public LoanDto save(LoanDto dto) {

        var loan = MyModelMapper.convertValue(dto, Loan.class);

        Book book = bookRepository.findById(loan.getBook().getId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        User user = userRepository.findById(loan.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        loan.setBook(book);
        loan.setUser(user);

        Loan savedLoan = loanRepository.save(loan);

        book.addLoan(savedLoan);
        book.setQuantity(book.getQuantity() - 1);

        user.addLoan(savedLoan);

        bookRepository.save(book);
        userRepository.save(user);

        return MyModelMapper.convertValue(savedLoan, LoanDto.class);
    }

    public LoanDto update(LoanDto dto) {

        Loan loan = loanRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        var book = bookRepository.findById(dto.getBook().getId()).orElseThrow(NullPointerException::new);
        var user = userRepository.findById(dto.getUser().getId()).orElseThrow(NullPointerException::new);

        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(dto.getLoanDate());
        loan.setDueDate(dto.getDueDate());

        return MyModelMapper.convertValue(loanRepository.save(loan), LoanDto.class);
    }

    @Transactional
    public void delete(String id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        Book book = bookRepository.findById(loan.getBook().getId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        User user = userRepository.findById(loan.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        book.setQuantity(book.getQuantity() + 1);

        book.removeLoan(loan);
        user.removeLoan(loan);

        bookRepository.save(book);
        userRepository.save(user);

        loanRepository.delete(loan);
    }
}
