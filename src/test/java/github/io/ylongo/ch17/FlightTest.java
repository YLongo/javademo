package github.io.ylongo.ch17;

import github.io.ylongo.ch16.spring_junit5_new_feature.Passenger;
import github.io.ylongo.ch16.spring_junit5_new_feature.PassengerRegistrationEvent;
import github.io.ylongo.ch16.spring_junit5_new_feature.RegistrationManager;
import github.io.ylongo.ch17.beans.FlightBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@SpringBootTest // 在测试类所在的当前包以及子包下查找bean
@Import({FlightBuilder.class, RegistrationManager.class})
public class FlightTest {
    
    @Autowired
    private Flight flight;
    
    @Autowired
    private RegistrationManager registrationManager;
    
    @Test
    void testFlightPassengersRegistration() {
        for (Passenger passenger : flight.getPassengers()) {
            Assertions.assertFalse(passenger.isRegistered());
            registrationManager.getApplicationContext().publishEvent(new PassengerRegistrationEvent(passenger));
        }
    }
}
