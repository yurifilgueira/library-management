package com.library.services;

import com.library.entities.dto.BookDto;
import com.library.entities.dto.requests.LoanRequestDto;
import com.library.entities.dto.reponses.LoanResponseDto;
import com.library.entities.dto.UserDto;
import com.library.entities.model.Book;
import com.library.entities.model.Customer;
import com.library.entities.model.Loan;
import com.library.exceptions.ObjectNotFoundException;
import com.library.modelMapper.MyModelMapper;
import com.library.repositories.BookRepository;
import com.library.repositories.LoanRepository;
import com.library.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public List<LoanResponseDto> findAll() {
        var loans = loanRepository.findAll();
        return getLoanResponseDtos(loans);
    }

    public LoanResponseDto findById(String id) {
        var loan =  loanRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Loan not found"));

        var loanResponseDto = MyModelMapper.convertValue(loan, LoanResponseDto.class);
        loanResponseDto.setBook(MyModelMapper.convertValue(loan.getBook(), BookDto.class));
        loanResponseDto.setUser(MyModelMapper.convertValue(loan.getCustomer(), UserDto.class));

        return loanResponseDto;
    }

    public List<LoanResponseDto> findLoansByCustomerId(String id) {

        userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User not found"));

        var loans = loanRepository.findLoansByCustomerId(id);

        return getLoanResponseDtos(loans);
    }

    public List<LoanResponseDto> findLoansByBookId(String id) {
        bookRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Book not found"));

        var loans = loanRepository.findLoansByBookId(id);

        return getLoanResponseDtos(loans);
    }

    @Transactional
    public LoanResponseDto save(LoanRequestDto loanRequestDto) {

        var loan = MyModelMapper.convertValue(loanRequestDto, Loan.class);

        var book = bookRepository.findById(loanRequestDto.getBookId()).orElseThrow(() -> new ObjectNotFoundException("Book not found"));
        var user = (Customer) userRepository.findById(loanRequestDto.getCustomerId()).orElseThrow(() -> new ObjectNotFoundException("User not found"));

        loan.setBook(book);
        loan.setCustomer(user);

        Loan savedLoan = loanRepository.save(loan);

        book.addLoan(savedLoan);
        book.setQuantity(book.getQuantity() - 1);

        user.addLoan(savedLoan);

        bookRepository.save(book);
        userRepository.save(user);

        var loanResponseDto = MyModelMapper.convertValue(savedLoan, LoanResponseDto.class);
        loanResponseDto.setBook(MyModelMapper.convertValue(book, BookDto.class));
        loanResponseDto.setUser(MyModelMapper.convertValue(user, UserDto.class));

        return loanResponseDto;

    }

    public LoanResponseDto update(LoanRequestDto loanRequestDto) {

        Loan loan = loanRepository.findById(loanRequestDto.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Loan not found"));

        var book = bookRepository.findById(loanRequestDto.getBookId()).orElseThrow(() -> new ObjectNotFoundException("Book not found"));
        var user = (Customer) userRepository.findById(loanRequestDto.getCustomerId()).orElseThrow(() -> new ObjectNotFoundException("User not found"));

        loan.setBook(book);
        loan.setCustomer(user);
        loan.setLoanDate(loanRequestDto.getLoanDate());
        loan.setDueDate(loanRequestDto.getDueDate());

        loanRepository.save(loan);

        book.removeLoan(loan);
        book.addLoan(loan);

        user.removeLoan(loan);
        user.addLoan(loan);

        bookRepository.save(book);
        userRepository.save(user);

        var loanResponseDto = MyModelMapper.convertValue(loan, LoanResponseDto.class);
        loanResponseDto.setBook(MyModelMapper.convertValue(book, BookDto.class));
        loanResponseDto.setUser(MyModelMapper.convertValue(user, UserDto.class));

        return loanResponseDto;
    }

    @Transactional
    public void delete(String id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Loan not found"));

        Book book = bookRepository.findById(loan.getBook().getId())
                .orElseThrow(() -> new ObjectNotFoundException("Book not found"));
        Customer user = (Customer) userRepository.findById(loan.getCustomer().getId())
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));

        book.setQuantity(book.getQuantity() + 1);

        book.removeLoan(loan);
        user.removeLoan(loan);

        bookRepository.save(book);
        userRepository.save(user);

        loanRepository.delete(loan);
    }

    private List<LoanResponseDto> getLoanResponseDtos(List<Loan> loans) {
        return loans.stream().map(loan -> {
            LoanResponseDto loanResponseDto = new LoanResponseDto();
            loanResponseDto.setId(loan.getId());

            var user = MyModelMapper.convertValue(loan.getCustomer(), UserDto.class);
            loanResponseDto.setUser(user);

            var book = MyModelMapper.convertValue(loan.getBook(), BookDto.class);
            loanResponseDto.setBook(book);

            loanResponseDto.setLoanDate(loan.getLoanDate());
            loanResponseDto.setDueDate(loan.getDueDate());
            return loanResponseDto;
        }).collect(Collectors.toList());
    }
}
