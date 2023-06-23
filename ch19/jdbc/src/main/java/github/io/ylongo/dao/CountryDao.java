package github.io.ylongo.dao;

import github.io.ylongo.ConnectionManager;
import github.io.ylongo.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDao {

    private static final String GET_ALL_COUNTRIES_SQL = "select * from country";
    
    private static final String GET_COUNTRIES_BY_NAME_SQL = "select * from country where name like ?";

    public List<Country> getCountryList() {
        List<Country> countryList = new ArrayList<>();

        Connection connection = ConnectionManager.openConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_COUNTRIES_SQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                String name = resultSet.getString(2);
                String codeName = resultSet.getString(3);
                countryList.add(new Country(name, codeName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection();
        }
        
        return countryList;
    }
    
    public List<Country> getCountryListStartWith(String name) {
        List<Country> countryList = new ArrayList<>();

        Connection connection = ConnectionManager.openConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_COUNTRIES_BY_NAME_SQL);
            preparedStatement.setString(1, name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                String countryName = resultSet.getString(2);
                String codeName = resultSet.getString(3);
                countryList.add(new Country(countryName, codeName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection();
        }
        return countryList;
    }
}
