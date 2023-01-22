package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Driver{

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int driverId;
    @Column(unique = true,nullable = false)
    private String mobile;
    private String password;

    @OneToOne(mappedBy = "driver",cascade = CascadeType.ALL)
    private Cab cab;

    //This will be a parent to TripBooking class with @OneToMany as for a driver there can be many trips...
    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
    private List<TripBooking> tripBookingList=new ArrayList<>();

    public Driver() {
    }

    public Driver(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public int getDriverId() {
        return driverId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cab getCab() {
        return cab;
    }

    public void setCab(Cab cab) {
        this.cab = cab;
    }

    public List<TripBooking> getTripBookingList() {
        return tripBookingList;
    }

    public void setTripBookingList(List<TripBooking> tripBookingList) {
        this.tripBookingList = tripBookingList;
    }
}