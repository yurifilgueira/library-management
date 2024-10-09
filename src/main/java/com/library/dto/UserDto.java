package com.library.dto;

import java.io.Serializable;

public record UserDto(String id, String name, String email) implements Serializable {
}