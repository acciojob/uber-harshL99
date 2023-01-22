package com.driver.services.impl;

import com.driver.model.*;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
        Customer customertoBeDeleted=customerRepository2.findById(customerId).get();
		customerRepository2.deleteByMobile(customertoBeDeleted.getMobile());
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> drivers=driverRepository2.findAll();
		if(drivers.equals(null))
			return null;

		int min=Integer.MAX_VALUE;
		Driver suitableDriver=null;

		for(Driver driver : drivers){
			if(driver.getDriverId()<min && driver.getCab().getAvailable()){
				suitableDriver=driver;
			}
		}
		if(suitableDriver==null)
         return null;

			Customer customer = customerRepository2.findById(customerId).get();
			TripBooking tripBooking = new TripBooking();
			tripBooking.setFromLocation(fromLocation);
			tripBooking.setToLocation(toLocation);
			tripBooking.setDistanceInKm(distanceInKm);
			tripBooking.setStatus(TripStatus.CONFIRMED);
			tripBooking.setCustomer(customer);
			tripBooking.setDriver(suitableDriver);

		    return tripBookingRepository2.save(tripBooking);
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking=tripBookingRepository2.findById(tripId).get();

		tripBooking.setFromLocation(null);
		tripBooking.setToLocation(null);
		tripBooking.setDistanceInKm(0);
		tripBooking.setStatus(TripStatus.CANCELED);
		tripBooking.setBill(0);

		tripBookingRepository2.save(tripBooking);
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		int bill=0;
		TripBooking tripBooking=tripBookingRepository2.findById(tripId).get();
		Driver driver=tripBooking.getDriver();
		Cab cab=driver.getCab();

		bill=cab.getPerKmRate()*tripBooking.getDistanceInKm();
		tripBooking.setStatus(TripStatus.COMPLETED);
		tripBooking.setBill(bill);

		tripBookingRepository2.save(tripBooking);
	}
}
