package com.library.entities.dto.reponses;

import java.util.Date;

public record RefreshTokenResponseDto(String token, String refreshToken, Date expiresAt, Date refreshExpiresAt) {
}
