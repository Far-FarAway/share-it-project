package ru.yandex.practicum.shareIt.user.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.shareIt.user.dto.UserDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserDtoMapper implements RowMapper<UserDto> {
    public UserDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return UserDto.builder()
                .name(resultSet.getString("name"))
                .build();
    }
}
