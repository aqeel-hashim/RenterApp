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
public class ODBDataSnapshot {
    private String vehicleVinNumber;
    private double lat;
    private double lon;
    private int sparkAdvance;
    private int rpm;
    private int speed;
    private int pedalPosition;
    private double airflowRate;
    private double coolantTemperature;
    private double drivenDistanceWithMilOn;
    private long timeInMillis;
    private Vehicle vehicle;  
    
    public String getVehicleVinNumber() {
        return vehicleVinNumber;
    }

    public void setVehicleVinNumber(String vehicleVinNumber) {
        this.vehicleVinNumber = vehicleVinNumber;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    
    public int getSparkAdvance() {
        return sparkAdvance;
    }

    public void setSparkAdvance(int sparkAdvance) {
        this.sparkAdvance = sparkAdvance;
    }

    public int getRpm() {
        return rpm;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPedalPosition() {
        return pedalPosition;
    }

    public void setPedalPosition(int pedalPosition) {
        this.pedalPosition = pedalPosition;
    }

    public double getAirflowRate() {
        return airflowRate;
    }

    public void setAirflowRate(double airflowRate) {
        this.airflowRate = airflowRate;
    }

    public double getCoolantTemperature() {
        return coolantTemperature;
    }

    public void setCoolantTemperature(double coolantTemperature) {
        this.coolantTemperature = coolantTemperature;
    }

    public double getDrivenDistanceWithMilOn() {
        return drivenDistanceWithMilOn;
    }

    public void setDrivenDistanceWithMilOn(double drivenDistanceWithMilOn) {
        this.drivenDistanceWithMilOn = drivenDistanceWithMilOn;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }
    
    
    
    
}
