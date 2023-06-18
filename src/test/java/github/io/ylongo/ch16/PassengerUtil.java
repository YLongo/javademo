package github.io.ylongo.ch16;

public class PassengerUtil {

    public static Passenger getExpectedPassenger() {
        Passenger passenger = new Passenger("John Smith");

        Country country = new Country("USA", "US");

        passenger.setCountry(country);

        return passenger;
    }
}