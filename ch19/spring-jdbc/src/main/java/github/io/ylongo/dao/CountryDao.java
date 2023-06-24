package github.io.ylongo.dao;


import github.io.ylongo.model.Country;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

public class CountryDao extends JdbcDaoSupport {

    private static final String GET_ALL_COUNTRIES_SQL = "select * from country";
    private static final String GET_COUNTRIES_BY_NAME_SQL = "select * from country where name like :name";

    private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();
    
    public List<Country> getCountryList() {
        return getJdbcTemplate().query(GET_ALL_COUNTRIES_SQL, COUNTRY_ROW_MAPPER);
    }

    public List<Country> getCountryListStartWith(String name) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("name", name + "%");
        return namedParameterJdbcTemplate.query(GET_COUNTRIES_BY_NAME_SQL, sqlParameterSource, COUNTRY_ROW_MAPPER);
    }
}
