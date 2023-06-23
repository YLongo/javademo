package github.io.ylongo;

import github.io.ylongo.dao.CountryDao;
import github.io.ylongo.model.Country;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static github.io.ylongo.CountriesLoader.COUNTRY_INIT_DATA;

public class CountriesDatabaseTest {
    
    private CountryDao countryDao = new CountryDao();
    
    private CountriesLoader countriesLoader = new CountriesLoader();
    
    private List<Country> expectedCountryList = new ArrayList<>();
    
    private List<Country> expectedCountryListStartsWithA = new ArrayList<>();
    
    @BeforeEach
    public void setUp() {
        TablesManager.createTable();
        initExpectedCountryLists();
        countriesLoader.loadCountries();
    }
    
    @Test
    public void testCountryList() {
        List<Country> countryList = countryDao.getCountryList();
        assert countryList.size() == expectedCountryList.size();
        assert countryList.containsAll(expectedCountryList);
    }
    
    @AfterEach
    public void tearDown() {
        TablesManager.dropTable();
    }

    private void initExpectedCountryLists() {
        for (int i = 0; i < COUNTRY_INIT_DATA.length; i++) {
            String[] countryInitData = COUNTRY_INIT_DATA[i];
            Country country = new Country(countryInitData[0], countryInitData[1]);
            expectedCountryList.add(country);
            if (country.getName().startsWith("A")) {
                expectedCountryListStartsWithA.add(country);
            }
        }
    }

}
