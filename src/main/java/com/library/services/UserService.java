package com.library.services;

import com.library.dto.UserDto;
import com.library.entities.User;
import com.library.modelMapper.MyModelMapper;
import com.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDto> findAll() {
        var users = userRepository.findAll();

        return MyModelMapper.convertList(users, UserDto.class);
    }

    public UserDto findById(String id) {
        var user = userRepository.findById(id).orElseThrow(NullPointerException::new);

        return MyModelMapper.convertValue(user, UserDto.class);
    }

    public UserDto save(UserDto user) {

        var entity = MyModelMapper.convertValue(user, User.class);
        return MyModelMapper.convertValue(userRepository.save(entity), UserDto.class);
    }

    public UserDto update(UserDto user) {
        var entity = userRepository.findById(user.getId()).orElseThrow(NullPointerException::new);

        entity.setName(user.getName());
        entity.setEmail(user.getEmail());

        return MyModelMapper.convertValue(userRepository.save(entity), UserDto.class);
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}
