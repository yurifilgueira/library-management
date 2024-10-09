package com.library.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false, length = 80)
    private String name;
    @Column(nullable = false, length = 80)
    private String email;
    @Column(nullable = false, length = 80)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Loan> loans = new HashSet<>();


    public User() {
    }

    public User(String id, String name, String email, String password, Set<Loan> loans) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.loans = loans;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Loan> getLoans() {
        return Set.copyOf(loans);
    }
}
