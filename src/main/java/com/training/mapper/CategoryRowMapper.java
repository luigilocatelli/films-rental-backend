package com.training.mapper;

import com.training.models.Category;
import com.training.utils.Functions;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CategoryRowMapper implements RowMapper<Category> {

    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category category = new Category();
        category.setId(rs.getLong("category_id"));
        category.setName(rs.getString("name"));
        category.setLastUpdate(Functions.convertTimestampToLocalDate(rs.getTimestamp("last_update")));

        return category;
    }
}
