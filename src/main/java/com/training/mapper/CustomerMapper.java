package com.training.mapper;

import com.training.models.Customer;
import com.training.utils.Functions;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getLong("customer_id"));
        customer.setStoreId(rs.getLong("store_id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setEmail(rs.getString("email"));
        customer.setEmail(rs.getString("email"));
        customer.setAddressId(rs.getLong("address_id"));
        customer.setActive(rs.getLong("active"));
        customer.setCreateDate(rs.getDate("create_date"));
        customer.setLastUpdate(Functions.convertTimestampToLocalDate(rs.getTimestamp("last_update")));

        return customer;
    }
}
