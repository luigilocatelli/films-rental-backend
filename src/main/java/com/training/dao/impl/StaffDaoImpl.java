package com.training.dao.impl;

import com.training.dao.StaffDao;
import com.training.mapper.StaffRowMapper;
import com.training.models.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;

@Repository
public class StaffDaoImpl implements StaffDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StaffRowMapper staffRowMapper;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Staff getById(Long staffId) {
        String query = "select * from staff where staff_id = ?";
        return jdbcTemplate.queryForObject(query, staffRowMapper, staffId);
    }


}
