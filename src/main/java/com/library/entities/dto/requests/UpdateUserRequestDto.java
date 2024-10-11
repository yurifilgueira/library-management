package com.library.entities.dto.requests;

import java.io.Serializable;

public record UpdateUserRequestDto(String id, String name, String email, String newPassword, String confirmNewPassword) implements Serializable {
}
