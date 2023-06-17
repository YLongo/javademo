package github.io.ylongo.ch14.jdbc;


import github.io.ylongo.ch14.Passenger;

public interface PassengerDao {
	public void insert(Passenger passenger) throws PassengerExistsException;
	public void update(String id, String name);
	public void delete(Passenger passenger);
	public Passenger getById(String id);
}