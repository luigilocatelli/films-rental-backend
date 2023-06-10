package com.training.dao.impl;

import com.training.dao.CategoryDao;
import com.training.mapper.CategoryRowMapper;
import com.training.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;


@Repository
public class CategoryDaoImpl implements CategoryDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CategoryRowMapper categoryRowMapper;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Category getCategoryById(Long id) {
        String query = "select * from category where category_id = ?";
        return jdbcTemplate.queryForObject(query, categoryRowMapper, id);
    }

    public List<Category> getAllCategories() {
        String query = "select * from category";
        return jdbcTemplate.query(query, categoryRowMapper);
    }



}
