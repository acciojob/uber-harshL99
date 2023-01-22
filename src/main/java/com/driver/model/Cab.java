package com.driver.model;


import javax.persistence.*;

@Entity
public class Cab{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int perKmRate;
    private boolean available;

    @OneToOne
    @JoinColumn
    private Driver driver; //It's Parent(Driver) object which need to be assigned when giving values to cab...

    public Cab() {
    }

    public Cab(int perKmRate, boolean available) {
        this.perKmRate = perKmRate;
        this.available = available;
    }

    public int getId() {
        return id;
    }


    public int getPerKmRate() {
        return perKmRate;
    }

    public void setPerKmRate(int perKmRate) {
        this.perKmRate = perKmRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}