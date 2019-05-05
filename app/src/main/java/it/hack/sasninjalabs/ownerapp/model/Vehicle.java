/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.hack.sasninjalabs.ownerapp.model;

import com.google.firebase.database.Exclude;

import java.util.List;

/**
 *
 * @author abdum
 */
public class Vehicle {
    private String vinNumber;
    private String numberPlate;
    private String description;
    private String model;
    private String modelYear;
    private String bodyType;
    private String fuelType;
    private String engineCapacity;
    private String imageUrl;
    private int redIssues;
    private int yellowIssues;
    private int greenIssues;
    private double geoFenceCenterLatitude;
    private double geoFenceCenterLongitude;
    private double radiusMeters;
    @Exclude
    private transient List<ODBDataSnapshot> dataSnapShots;

    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelYear() {
        return modelYear;
    }

    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public double getGeoFenceCenterLatitude() {
        return geoFenceCenterLatitude;
    }

    public void setGeoFenceCenterLatitude(double geoFenceCenterLatitude) {
        this.geoFenceCenterLatitude = geoFenceCenterLatitude;
    }

    public double getGeoFenceCenterLongitude() {
        return geoFenceCenterLongitude;
    }

    public void setGeoFenceCenterLongitude(double geoFenceCenterLongitude) {
        this.geoFenceCenterLongitude = geoFenceCenterLongitude;
    }

    public double getRadiusMeters() {
        return radiusMeters;
    }

    public void setRadiusMeters(double radiusMeters) {
        this.radiusMeters = radiusMeters;
    }



    public List<ODBDataSnapshot> getDataSnapShots() {
        return dataSnapShots;
    }

    public void setDataSnapShots(List<ODBDataSnapshot> dataSnapShots) {
        this.dataSnapShots = dataSnapShots;
    }

    public int getRedIssues() {
        return redIssues;
    }

    public void setRedIssues(int redIssues) {
        this.redIssues = redIssues;
    }

    public int getYellowIssues() {
        return yellowIssues;
    }

    public void setYellowIssues(int yellowIssues) {
        this.yellowIssues = yellowIssues;
    }

    public int getGreenIssues() {
        return greenIssues;
    }

    public void setGreenIssues(int greenIssues) {
        this.greenIssues = greenIssues;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    
    
    
}
