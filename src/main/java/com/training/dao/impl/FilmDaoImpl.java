package com.training.dao.impl;

import com.training.dao.FilmDao;
import com.training.mapper.FilmRowMapper;
import com.training.models.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmDaoImpl implements FilmDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FilmRowMapper filmRowMapper;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Film> getAllFilms() {
        String query = "select * from film";

        return jdbcTemplate.query(query, filmRowMapper);
    }

    @Override
    public List<Film> getAllNotRentedFilmsByCustomer(Long customerId) {
        String query =  "SELECT DISTINCT f.* FROM film f LEFT JOIN rental r ON r.film_id = f.film_id " +
                        "WHERE r.film_id IS NULL " +
                        "UNION " +
                        "SELECT DISTINCT f.* FROM film f JOIN rental r ON r.film_id = f.film_id " +
                        "WHERE f.film_id NOT IN (SELECT DISTINCT r.film_id FROM rental r WHERE r.customer_id = ?)";
        return jdbcTemplate.query(query, filmRowMapper, customerId);
    }

    @Override
    public List<Film> getAllRentedFilmsByCustomer(Long customerId) {
        String query =  "SELECT DISTINCT f.* FROM film f JOIN rental r ON r.film_id = f.film_id " +
                        "WHERE f.film_id IN (SELECT DISTINCT r.film_id FROM rental r WHERE r.customer_id = ?)";
        return jdbcTemplate.query(query, filmRowMapper, customerId);
    }

    @Override
    public List<Film> getFilmsByCategoryId(int categoryId) {
        String query = "select * from film where category_id = ?";
        return jdbcTemplate.query(query, filmRowMapper, categoryId);
    }

    @Override
    public List<Film> getNotRentedFilmsByCustomerAndCategoryId(int categoryId, Long customerId) {
        String FilmsWithoutAnyRentals =  "SELECT DISTINCT f.* FROM film f LEFT JOIN rental r ON r.film_id = f.film_id " +
                "WHERE r.film_id IS NULL AND f.category_id = ?";
        String filmsWithoutAnyRentalForCustomerInScope = "SELECT DISTINCT f.* FROM film f JOIN rental r ON r.film_id = f.film_id " +
                "WHERE f.category_id = ? AND f.film_id NOT IN (SELECT DISTINCT r.film_id FROM rental r WHERE r.customer_id = ?)";

        List<Film> films = new ArrayList<>();
        films.addAll(jdbcTemplate.query(FilmsWithoutAnyRentals, filmRowMapper, categoryId));
        films.addAll(jdbcTemplate.query(filmsWithoutAnyRentalForCustomerInScope, filmRowMapper, categoryId, customerId));
        return films;
    }

    @Override
    public List<Film> getRentedFilmsByCustomerAndCategoryId(int categoryId, Long customerId) {
        String query = "SELECT DISTINCT f.* FROM film f JOIN rental r ON r.film_id = f.film_id " +
                "WHERE f.category_id = ? AND f.film_id IN (SELECT DISTINCT r.film_id FROM rental r WHERE r.customer_id = ?)";

        return jdbcTemplate.query(query, filmRowMapper, categoryId, customerId);
    }

    @Override
    public Film getById(Long filmId) {
        String query = "select * from film where film_id = ?";
        return jdbcTemplate.queryForObject(query, filmRowMapper, filmId);
    }
}
