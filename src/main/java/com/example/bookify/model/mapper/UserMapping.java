package com.example.bookify.model.mapper;

import com.example.bookify.model.dto.UserRegisterDTO;
import com.example.bookify.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapping {

    @Mapping(target = "active", constant = "true")
    User userDtoToUser(UserRegisterDTO userRegisterDTO);
}

