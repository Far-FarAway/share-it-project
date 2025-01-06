package ru.yandex.practicum.shareIt.review.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.shareIt.review.Review;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewMapper implements RowMapper<Review> {
    public Review mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Review.builder()
                .id(resultSet.getLong("id"))
                .reviewerName(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .build();
    }
}
