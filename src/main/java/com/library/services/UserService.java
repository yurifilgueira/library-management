package com.library.services;

import com.library.entities.dto.reponses.LoginResponseDto;
import com.library.entities.dto.requests.UpdateUserRequestDto;
import com.library.entities.dto.UserDto;
import com.library.entities.model.User;
import com.library.exceptions.ObjectNotFoundException;
import com.library.jwt.JwtTokenProvider;
import com.library.modelMapper.MyModelMapper;
import com.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthServices authServices;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public List<UserDto> findAll() {
        var users = userRepository.findAll();

        return MyModelMapper.convertList(users, UserDto.class);
    }

    public UserDto findById(String id) {
        var user = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User not found"));

        return MyModelMapper.convertValue(user, UserDto.class);
    }

    public ResponseEntity<?> update(UpdateUserRequestDto data) {
        User user = userRepository.findById(data.id()).orElseThrow(() -> new ObjectNotFoundException("User not found"));

        user.setName(data.name());
        user.setEmail(data.email());

        var password = authServices.encodePassword(data.newPassword());
        user.setPassword(password);

        user = userRepository.save(user);
        try {
            var loginResponseDto = new LoginResponseDto();

            var userDto = MyModelMapper.convertValue(user, UserDto.class);

            var tokenDto = jwtTokenProvider.createAccessToken(user.getUsername(), user.getRoles());
            loginResponseDto = authServices.generateLoginResponseDto(userDto, tokenDto);

            return ResponseEntity.ok().body(loginResponseDto);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid email or password.");
        }
    }

    public void delete(String id) {

        var entity = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User not found"));
        userRepository.delete(entity);
    }
}
