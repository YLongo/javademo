package github.io.ylongo.ch16.spring_junit5_new_feature;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class PassengerRegistrationListener {

    /**
     * 监听PassengerRegistrationEvent事件
     */
    @EventListener
    public void confirmRegistration(PassengerRegistrationEvent passengerRegistrationEvent) {
        Passenger passenger = passengerRegistrationEvent.getPassenger();
        passenger.setIsRegistered(true);
        System.out.println("Confirming registration for the passenger: " + passenger);
    }
}
