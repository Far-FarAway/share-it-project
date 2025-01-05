package ru.yandex.practicum.shareIt.item.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.shareIt.item.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class ItemMapper implements RowMapper<Item> {
    public Item mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Item.builder()
                .id(resultSet.getLong("id"))
                .owner(resultSet.getLong("owner"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .available(resultSet.getBoolean("available"))
                //при реализации БД добавить вызов запроса
                .reviews(new ArrayList<>())
                .bookCount(resultSet.getInt("book_count"))
                .build();
    }
}
