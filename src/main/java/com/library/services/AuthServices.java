package com.library.services;

import com.library.entities.dto.*;
import com.library.entities.dto.reponses.LoginResponseDto;
import com.library.entities.dto.requests.RegisterRequestDto;
import com.library.entities.model.Admin;
import com.library.entities.model.Customer;
import com.library.entities.model.Role;
import com.library.entities.model.User;
import com.library.jwt.JwtTokenProvider;
import com.library.modelMapper.MyModelMapper;
import com.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class AuthServices {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;
    private Logger logger = Logger.getLogger(AuthServices.class.getName());

    public ResponseEntity<LoginResponseDto> signin(SingInRequestDto data) {
        try {
            var email = data.email();
            var password = data.password();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            var user = userRepository.findByEmail(email);
            var tokenDto = new TokenDto();
            var loginResponseVO = new LoginResponseDto();

            if (user != null) {
                UserDto userDto = MyModelMapper.convertValue(user, UserDto.class);

                tokenDto = jwtTokenProvider.createAccessToken(email, user.getRoles());
                loginResponseVO = generateLoginResponseDto(userDto, tokenDto);
            }else {
                throw new UsernameNotFoundException("Email " + email + " not found.");
            }

            return ResponseEntity.ok(loginResponseVO);
        }catch (Exception e) {
            throw new BadCredentialsException("Invalid email or password.");
        }
    }

    public ResponseEntity<User> register(RegisterRequestDto data) {

        var name = data.name();
        var email = data.email();
        var password = encodePassword(data.password());
        var roles = Set.of(new Role("ROLE_CUSTOMER"));

        var user = userRepository.save(new Customer(name, email, password, roles, new HashSet<>()));

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    public ResponseEntity<User> registerAdmin(RegisterRequestDto data) {

        var name = data.name();
        var email = data.email();
        var password = encodePassword(data.password());
        var roles = Set.of(new Role("ROLE_ADMIN"));

        var user = userRepository.save(new Admin(name, email, password, roles));

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    public ResponseEntity<TokenDto> refreshToken(String refreshToken) {
        var token = jwtTokenProvider.refreshToken(refreshToken);
        return ResponseEntity.ok(token);
    }

    public String encodePassword(String password) {

        Map<String, PasswordEncoder> encoders = new HashMap<>();

        Pbkdf2PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder("", 8, 185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        encoders.put("pbkdf2", pbkdf2Encoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);

        return passwordEncoder.encode(password);
    }

    public LoginResponseDto generateLoginResponseDto(UserDto userDto, TokenDto tokenDto) {
        LoginResponseDto loginResponseDto = new LoginResponseDto(
                userDto,
                tokenDto.getAccessToken(),
                tokenDto.getRefreshToken(),
                tokenDto.getExpiration(),
                tokenDto.getRefreshTokenExpiration());

        return loginResponseDto;
    }
}
