package github.io.ylongo.ch16;

import java.util.Objects;

public class Passenger {
    
    private String name;
    
    private Country country;
    
    public Passenger(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public Country getCountry() {
        return country;
    }
    
    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "name='" + name + '\'' +
                ", country=" + country +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return name.equals(passenger.name) &&
                Objects.equals(country, passenger.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country);
    }

}
