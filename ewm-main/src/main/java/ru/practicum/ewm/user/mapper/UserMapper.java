package ru.practicum.ewm.user.mapper;

import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.model.User;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);
}
