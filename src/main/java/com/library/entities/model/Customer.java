package com.library.entities.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "customer")
    private Set<Loan> loans;

    public Customer() {
    }

    public Customer(String name, String email, String password, Set<Role> roles, Set<Loan> loans) {
        super(name, email, password, roles);
        this.loans = loans;
    }

    public Set<Loan> getLoans() {
        return Set.copyOf(loans);
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
    }
}
