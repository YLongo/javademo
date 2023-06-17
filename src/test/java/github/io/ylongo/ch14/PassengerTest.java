package github.io.ylongo.ch14;

import github.io.ylongo.ch14.extensions.DataAccessObjectParameterResolver;
import github.io.ylongo.ch14.extensions.DatabaseOperationsExtension;
import github.io.ylongo.ch14.extensions.ExecutionContextExtension;
import github.io.ylongo.ch14.extensions.LogPassengerExistsExceptionExtension;
import github.io.ylongo.ch14.jdbc.PassengerDao;
import github.io.ylongo.ch14.jdbc.PassengerExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({
        ExecutionContextExtension.class, 
        DatabaseOperationsExtension.class,
        DataAccessObjectParameterResolver.class,
        LogPassengerExistsExceptionExtension.class
        
})
public class PassengerTest {

    private final PassengerDao passengerDao;

    /**
     * @see DataAccessObjectParameterResolver
     */
    public PassengerTest(PassengerDao passengerDao) {
        this.passengerDao = passengerDao;
    }
    
    @Test
    void testPassenger() {
        Passenger passenger = new Passenger("123-456-789", "John Smith");
        assertEquals("Passenger John Smith with identifier: 123-456-789", passenger.toString());
    }
    
    @Test
    void testInsertPassenger() throws PassengerExistsException {
        Passenger passenger = new Passenger("123-456-789", "John Smith");
        passengerDao.insert(passenger);
    }
    
    @Test
    void testUpdatePassenger() throws PassengerExistsException {
        Passenger passenger = new Passenger("123-456-789", "John Smith");
        passengerDao.insert(passenger);
        passengerDao.update("123-456-789", "John Smith II");
        Assertions.assertEquals("John Smith II", passengerDao.getById("123-456-789").getName());
    }
    
    @Test
    void testDeletePassenger() throws PassengerExistsException {
        Passenger passenger = new Passenger("123-456-789", "John Smith");
        passengerDao.insert(passenger);
        passengerDao.delete(passenger);
        Assertions.assertNull(passengerDao.getById("123-456-789"));
    }

    /**
     * @see LogPassengerExistsExceptionExtension
     */
    @Test
    void testInsertExistingPassenger() throws PassengerExistsException {
        Passenger passenger = new Passenger("123-456-789", "John Smith");
        passengerDao.insert(passenger);
        passengerDao.insert(passenger);
        assertEquals("John Smith", passengerDao.getById("123-456-789").getName());
    }

}
