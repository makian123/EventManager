package com.mhohos.eventManager.component;

import com.mhohos.eventManager.dto.UserDto;
import com.mhohos.eventManager.model.User;
import com.mhohos.eventManager.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public final UserRepository userRepository;

    public UserMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto toUserDto(User usr){
        return new UserDto(usr.getId(), usr.getUsername(), usr.isAdmin());
    }
    public User toUser(UserDto userDto){
        return userRepository.findById(userDto.id()).orElseThrow();
    }
}
