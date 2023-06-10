package com.training.mapper;

import com.training.models.Staff;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StaffRowMapper implements RowMapper<Staff> {

    @Override
    public Staff mapRow(ResultSet rs, int rowNum) throws SQLException {
        Staff staff = new Staff();
        staff.setId(rs.getLong(1));
        staff.setFirstName(rs.getString(2));
        staff.setLastName(rs.getString(3));

        return staff;
    }
}
