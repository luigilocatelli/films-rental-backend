package com.training.dao.impl;

import com.training.dao.CustomerDao;
import com.training.mapper.CustomerMapper;
import com.training.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class CustomerDaoImpl implements CustomerDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CustomerMapper customerRowMapper;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Customer getById(Long customerId) {
        String query = "select * from customer where customer_id = ?";
        return jdbcTemplate.queryForObject(query, customerRowMapper, customerId);
    }
}
