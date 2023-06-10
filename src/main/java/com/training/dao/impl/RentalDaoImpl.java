package com.training.dao.impl;

import com.training.dao.RentalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class RentalDaoImpl implements RentalDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int cancelFilmRentalForCustomer(Long filmId, Long customerId) {
        String query = "delete from rental where film_id = ? and customer_id = ?";
        return jdbcTemplate.update(query, filmId, customerId);
    }
}
