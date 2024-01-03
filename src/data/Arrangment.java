package data;

import java.text.DateFormat;

public class Arrangment {
    private int id;
    private String tripName;
    private String destination;
    private String transportation;
    private String departureDate;
    private String returnDate;
    private double arrangmentPrice;
    private int accommodationID;

    public Arrangment(int id, String tripName, String destination, String transportation, String departureDate, String returnDate, double arrangmentPrice, int accommodationID) {
        this.id = id;
        this.tripName = tripName;
        this.destination = destination;
        this.transportation = transportation;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.arrangmentPrice = arrangmentPrice;
        this.accommodationID = accommodationID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public double getArrangmentPrice() {
        return arrangmentPrice;
    }

    public void setArrangmentPrice(double arrangmentPrice) {
        this.arrangmentPrice = arrangmentPrice;
    }

    public int getAccommodationID() {
        return accommodationID;
    }

    public void setAccommodationID(int accommodationID) {
        this.accommodationID = accommodationID;
    }
}
