/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.hack.sasninjalabs.ownerapp.model;

/**
 *
 * @author abdum
 */
public class DistanceAlert {
    private transient Vehicle vehicle;
    private String vehicleVIN;
    private double exceededDistance;
    private long timeInMillis;

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }
    
    
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getVehicleVIN() {
        return vehicleVIN;
    }

    public void setVehicleVIN(String vehicleVIN) {
        this.vehicleVIN = vehicleVIN;
    }

    public double getExceededDistance() {
        return exceededDistance;
    }

    public void setExceededDistance(double exceededDistance) {
        this.exceededDistance = exceededDistance;
    }
    
    
    
}
