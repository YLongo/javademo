package github.io.ylongo.ch14.jdbc;


import github.io.ylongo.ch14.Passenger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PassengerDaoImpl implements PassengerDao {

    private Connection connection;

    public PassengerDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Passenger passenger) throws PassengerExistsException {
        String sql = "INSERT INTO PASSENGERS (ID, NAME) VALUES (?, ?)";

        if (null != getById(passenger.getIdentifier())) {
            throw new PassengerExistsException(passenger, passenger.toString());
        }

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, passenger.getIdentifier());
            statement.setString(2, passenger.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(String id, String name) {
        String sql = "UPDATE PASSENGERS SET NAME = ? WHERE ID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Passenger passenger) {
        String sql = "DELETE FROM PASSENGERS WHERE ID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, passenger.getIdentifier());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Passenger getById(String id) {
        String sql = "SELECT * FROM PASSENGERS WHERE ID = ?";
        Passenger passenger = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                passenger = new Passenger(resultSet.getString(1), resultSet.getString(2));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return passenger;
    }
}