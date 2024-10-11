package com.library.controllers;

import com.library.entities.dto.SingInRequestDto;
import com.library.repositories.UserRepository;
import com.library.services.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthServices authServices;
    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "User Signin", description = "Authenticate a user and return a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Bad credentials",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "403", description = "Invalid client request",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping(value = "/signin")
    public ResponseEntity<?> signin(@RequestBody SingInRequestDto data) {
        if (dataIsNull(data)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        }

        try {
            var loginResponseVO = authServices.signin(data);
            return Objects.requireNonNullElseGet(loginResponseVO, () -> ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!"));
        }catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @Operation(summary = "Refresh Token", description = "Refresh a JWT token using a refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "403", description = "Invalid client request",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping(value = "/refresh")
    public ResponseEntity<?> register(@RequestHeader("Authorization") String refreshToken) {

        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        }

        return authServices.refreshToken(refreshToken);
    }

    private boolean dataIsNull(SingInRequestDto data) {

        if (data == null) return true;
        else if (data.email() == null || data.email().isEmpty()) return true;
        else if (data.password() == null || data.password().isEmpty()) return true;

        return false;
    }
}
