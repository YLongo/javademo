package github.io.ylongo;

import github.io.ylongo.dao.CountryDao;
import github.io.ylongo.model.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:application-context.xml")
class CountriesDatabaseTest {
    
    @Autowired
    private CountryDao countryDao;
    
    @Autowired
    private CountriesLoader countriesLoader;

    private final List<Country> expectedCountryList = new ArrayList<>();
    
    private final List<Country> expectedCountryListStartsWithA = new ArrayList<>();
    
    @BeforeEach
    public void setUp() {
        initExpectedCountryLists();
        countriesLoader.loadCountries();
    }

    
    @Test
    @DirtiesContext // Spring will reset context after this test
    void testCountryList() {
        List<Country> countryList = countryDao.getCountryList();
        
        Assertions.assertNotNull(countryList);
        Assertions.assertEquals(expectedCountryList.size(), countryList.size());

        for (int i = 0; i < expectedCountryList.size(); i++) {
            Assertions.assertEquals(expectedCountryList.get(i), countryList.get(i));    
        }
        
    }

    @Test
    @DirtiesContext
    void testCountryListStartsWithA() {
        List<Country> countryList = countryDao.getCountryListStartWith("A");
        Assertions.assertNotNull(countryList);
        Assertions.assertEquals(expectedCountryListStartsWithA.size(), countryList.size());
        for (int i = 0; i < expectedCountryListStartsWithA.size(); i++) {
            Assertions.assertEquals(expectedCountryListStartsWithA.get(i), countryList.get(i));
        }
    }
    
    
    private void initExpectedCountryLists() {
        for (int i = 0; i < CountriesLoader.COUNTRY_INIT_DATA.length; i++) {
            String[] countryInitData = CountriesLoader.COUNTRY_INIT_DATA[i];
            Country country = new Country(countryInitData[0], countryInitData[1]);
            expectedCountryList.add(country);
            if (country.getName().startsWith("A")) {
                expectedCountryListStartsWithA.add(country);
            }
        }
    }
}
