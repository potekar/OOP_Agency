package data;

import java.text.DateFormat;

public class Arrangment {
    private String id;
    private String tripName;
    private String destination;
    private String transportation;
    private String departureDate;
    private String returnDate;
    private double arrangmentPrice;
    private int accommodationID;

    public Arrangment(String id, String tripName, String destination, String transportation, String departureDate, String returnDate, double arrangmentPrice, int accommodationID) {
        this.id = id;
        this.tripName = tripName;
        this.destination = destination;
        this.transportation = transportation;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.arrangmentPrice = arrangmentPrice;
        this.accommodationID = accommodationID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Arrangment{");
        sb.append("id='").append(id).append('\'');
        sb.append(", tripName='").append(tripName).append('\'');
        sb.append(", destination='").append(destination).append('\'');
        sb.append(", transportation='").append(transportation).append('\'');
        sb.append(", departureDate='").append(departureDate).append('\'');
        sb.append(", returnDate='").append(returnDate).append('\'');
        sb.append(", arrangmentPrice=").append(arrangmentPrice);
        sb.append(", accommodationID=").append(accommodationID);
        sb.append("\n");
        return sb.toString();
    }
}
