package com.library.controllers;

import com.library.entities.dto.reponses.LoanResponseDto;
import com.library.entities.dto.requests.LoanRequestDto;
import com.library.services.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Retrieve all loans", description = "Get a list of all loans")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(schema = @Schema(implementation = LoanResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<LoanResponseDto>> findAll() {
        List<LoanResponseDto> loans = loanService.findAll();
        return ResponseEntity.ok(loans);
    }

    @Operation(summary = "Retrieve loan by ID", description = "Get a loan by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved loan",
                    content = @Content(schema = @Schema(implementation = LoanResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Loan not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LoanResponseDto> findById(@PathVariable String id) {
        LoanResponseDto loan = loanService.findById(id);
        return ResponseEntity.ok(loan);
    }

    @Operation(summary = "Retrieve loans by customer ID", description = "Get loans made by a specific customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved loans",
                    content = @Content(schema = @Schema(implementation = LoanResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No loans found for the customer"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/users/{id}")
    public List<LoanResponseDto> findLoansByCustomerId(@PathVariable String id) {
        return loanService.findLoansByCustomerId(id);
    }

    @Operation(summary = "Retrieve loans by book ID", description = "Get loans associated with a specific book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved loans",
                    content = @Content(schema = @Schema(implementation = LoanResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No loans found for the book"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/books/{id}")
    public List<LoanResponseDto> findLoansByBookId(@PathVariable String id) {
        return loanService.findLoansByBookId(id);
    }

    @Operation(summary = "Create a new loan", description = "Create a new loan for a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Loan successfully created",
                    content = @Content(schema = @Schema(implementation = LoanResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<LoanResponseDto> create(@RequestBody LoanRequestDto loan) {

        LoanResponseDto loanDto = loanService.save(loan);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanDto);
    }

    @Operation(summary = "Update a loan", description = "Update the details of an existing loan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan successfully updated",
                    content = @Content(schema = @Schema(implementation = LoanResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Loan not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping
    public ResponseEntity<LoanResponseDto> update(@RequestBody LoanRequestDto loan) {
        LoanResponseDto loanDto = loanService.update(loan);
        return ResponseEntity.ok(loanDto);
    }

    @Operation(summary = "Delete a loan", description = "Delete an existing loan by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Loan successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Loan not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        loanService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
