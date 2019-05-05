/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.hack.sasninjalabs.ownerapp.model;

import java.util.Date;

/**
 *
 * @author abdum
 */
public class Rental {
    private Vehicle vehicle;
    private Customer customer;
    private long rentalDateMillis;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public long getRentalDateMillis() {
        return rentalDateMillis;
    }

    public void setRentalDateMillis(long rentalDateMillis) {
        this.rentalDateMillis = rentalDateMillis;
    }
    
    
}
