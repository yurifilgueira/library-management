package com.library.entities.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.io.Serializable;
import java.util.Set;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User implements Serializable {

    public Admin() {
    }

    public Admin(String name, String email, String password, Set<Role> roles) {
        super(name, email, password, roles);
    }
}
