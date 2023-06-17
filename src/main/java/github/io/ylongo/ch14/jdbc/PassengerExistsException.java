package github.io.ylongo.ch14.jdbc;


import github.io.ylongo.ch14.Passenger;

public class PassengerExistsException extends Exception {
    private Passenger passenger;

    public PassengerExistsException(Passenger passenger, String message) {
        super(message);
        this.passenger = passenger;
    }
}