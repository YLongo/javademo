package github.io.ylongo.dao;

import github.io.ylongo.model.Country;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryRowMapper implements RowMapper<Country> {

    @Override
    public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Country(rs.getString("name"), rs.getString("code_name"));
    }
}
