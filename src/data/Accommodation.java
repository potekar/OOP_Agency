package data;

public class Accommodation {
    private int id;
    private String name;
    private int numberOfStars;
    private String roomType;
    double pricePerNight;

    public Accommodation(int id, String name, int numberOfStars, String roomType, double pricePerNight) {
        this.id = id;
        this.name = name;
        this.numberOfStars = numberOfStars;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfStars() {
        return numberOfStars;
    }

    public void setNumberOfStars(int numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }
}
