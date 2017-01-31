package com.example.a46453895j.bicing;

/**
 * Created by 46453895j on 31/01/17.
 */

public class Estaciones {


    public Estaciones(){

    }

    public Estaciones(int id, String type, Double latitude, Double longitude, String streetName, int streetNumber, int slots, int altitude, int bikes, String cercanos, String status) {

        this.id = id;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.slots = slots;
        this.altitude = altitude;
        this.bikes = bikes;
        this.cercanos = cercanos;
        this.status = status;
    }


    int  id;
    String type;
    Double latitude;
    Double longitude;
    String streetName;
    int streetNumber;
    int altitude;
    int slots;
    int bikes;
    String cercanos;
    String status;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getStreetName() {
        return streetName;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public int getAltitude() {
        return altitude;
    }

    public int getSlots() {
        return slots;
    }

    public int getBikes() {
        return bikes;
    }

    public String getCercanos() {
        return cercanos;
    }

    public String getStatus() {
        return status;
    }


}
