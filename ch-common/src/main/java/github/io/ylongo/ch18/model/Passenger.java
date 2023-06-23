package github.io.ylongo.ch18.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Passenger {

    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    @ManyToOne
    private Country country;
    
    private boolean isRegistered;

    // avoid "No default constructor for entity"
    public Passenger() {

    }

    public Passenger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    @Override
    public String toString() {
        return "Passenger{" + "name='" + name + '\'' + ", country=" + country + ", registered=" + isRegistered + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return isRegistered == passenger.isRegistered && Objects.equals(name, passenger.name) && Objects.equals(country, passenger.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country, isRegistered);
    }

}