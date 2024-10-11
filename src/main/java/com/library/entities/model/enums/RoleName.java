package com.library.entities.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serial;
import java.io.Serializable;

public enum RoleName implements Serializable {

    ROLE_CUSTOMER,
    ROLE_ADMIN;

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonValue
    public String getValue() {
        return name();
    }
}
