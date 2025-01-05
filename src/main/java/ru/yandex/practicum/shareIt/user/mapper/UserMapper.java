package ru.yandex.practicum.shareIt.user.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.shareIt.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User> {
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
