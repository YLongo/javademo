
package github.io.ylongo.ch18.exceptions;

public class PassengerNotFoundException extends RuntimeException {

    public PassengerNotFoundException(Long id) {
        super("Passenger id not found : " + id);
    }

}