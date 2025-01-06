package ru.yandex.practicum.shareIt.item.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDtoMapper implements RowMapper<ItemDto> {
    public ItemDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return ItemDto.builder()
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .available(resultSet.getBoolean("available"))
                .bookCount(resultSet.getInt("bookCount"))
                .build();
    }
}
