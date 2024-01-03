package data;

public class Reservation {
    private int clientId;
    private int arrangementId;
    private double totalPrice;
    private double paidPrice;

    public Reservation(int clientId, int arrangementId, double totalPrice, double paidPrice) {
        this.clientId = clientId;
        this.arrangementId = arrangementId;
        this.totalPrice = totalPrice;
        this.paidPrice = paidPrice;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getArrangementId() {
        return arrangementId;
    }

    public void setArrangementId(int arrangementId) {
        this.arrangementId = arrangementId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(double paidPrice) {
        this.paidPrice = paidPrice;
    }
}
