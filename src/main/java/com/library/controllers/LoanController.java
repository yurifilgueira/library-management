package com.library.controllers;

import com.library.dto.LoanDto;
import com.library.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping
    public ResponseEntity<List<LoanDto>> findAll() {
        List<LoanDto> loans = loanService.findAll();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDto> findById(@PathVariable String id) {
        LoanDto loan = loanService.findById(id);
        return ResponseEntity.ok(loan);
    }

    @PostMapping
    public ResponseEntity<LoanDto> create(@RequestBody LoanDto loan) {

        LoanDto loanDto = loanService.save(loan);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanDto);
    }

    @PutMapping()
    public ResponseEntity<LoanDto> update(@RequestBody LoanDto loan) {
        LoanDto loanDto = loanService.update(loan);
        return ResponseEntity.ok(loanDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        loanService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
