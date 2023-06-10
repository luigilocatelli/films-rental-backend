package com.training.mapper;

import com.training.dao.CategoryDao;
import com.training.models.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FilmRowMapper implements RowMapper<Film> {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong(1));
        film.setTitle(rs.getString(2));
        film.setDescription(rs.getString(3));
        film.setCategory(categoryDao.getCategoryById(rs.getLong(4)));
        film.setRental_rate(rs.getDouble(9));
        film.setReplacement_cost(rs.getDouble(11));

        return film;

    }
}
