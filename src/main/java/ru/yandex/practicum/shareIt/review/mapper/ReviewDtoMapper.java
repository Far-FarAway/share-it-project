package ru.yandex.practicum.shareIt.review.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.shareIt.review.dto.ReviewDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewDtoMapper implements RowMapper<ReviewDto> {
    public ReviewDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return ReviewDto.builder()
                .reviewerName(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .build();
    }
}
