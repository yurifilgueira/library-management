package com.library.entities.dto.reponses;

import com.library.entities.dto.UserDto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class LoginResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UserDto user;
    private String accessToken;
    private String refreshToken;
    private Date expiresAt;
    private Date refreshTokenExpiresAt;

    public LoginResponseDto() {
    }

    public LoginResponseDto(UserDto user, String accessToken, String refreshToken, Date expiresAt, Date refreshTokenExpiresAt) {
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getRefreshTokenExpiresAt() {
        return refreshTokenExpiresAt;
    }

    public void setRefreshTokenExpiresAt(Date refreshTokenExpiresAt) {
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }
}
