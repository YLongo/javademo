package github.io.ylongo;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class CountriesLoader extends JdbcDaoSupport {
    private static final String LOAD_COUNTRIES_SQL = "insert into country (name, code_name) values ";

    public static final String[][] COUNTRY_INIT_DATA = {{"Australia", "AU"}, {"Canada", "CA"}, {"France", "FR"},
            {"Germany", "DE"}, {"Italy", "IT"}, {"Japan", "JP"}, {"Romania", "RO"},
            {"Russian Federation", "RU"}, {"Spain", "ES"}, {"Switzerland", "CH"},
            {"United Kingdom", "UK"}, {"United States", "US"}};

    public void loadCountries() {
        for (String[] countryData : COUNTRY_INIT_DATA) {
            String sql = LOAD_COUNTRIES_SQL + "('" + countryData[0] + "', '" + countryData[1] + "');";
            getJdbcTemplate().execute(sql);
        }
    }
}