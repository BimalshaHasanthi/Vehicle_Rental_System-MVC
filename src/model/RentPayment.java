package model;

public class RentPayment {
    private String rentInvoiceNo;
    private String rentId;
    private String clientId;
    private String vehicleId;
    private String returnedDate;
    private String damageDetail;
    private Double damageCost;
    private Double discount;
    private Double total;

    public RentPayment() {
    }

    public RentPayment(String rentInvoiceNo, String rentId, String clientId, String vehicleId, String returnedDate, String damageDetail, Double damageCost, Double discount, Double total) {
        this.setRentInvoiceNo(rentInvoiceNo);
        this.setRentId(rentId);
        this.setClientId(clientId);
        this.setVehicleId(vehicleId);
        this.setReturnedDate(returnedDate);
        this.setDamageDetail(damageDetail);
        this.setDamageCost(damageCost);
        this.setDiscount(discount);
        this.setTotal(total);
    }

    public String getRentInvoiceNo() {
        return rentInvoiceNo;
    }

    public void setRentInvoiceNo(String rentInvoiceNo) {
        this.rentInvoiceNo = rentInvoiceNo;
    }

    public String getRentId() {
        return rentId;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }

    public String getDamageDetail() {
        return damageDetail;
    }

    public void setDamageDetail(String damageDetail) {
        this.damageDetail = damageDetail;
    }

    public Double getDamageCost() {
        return damageCost;
    }

    public void setDamageCost(Double damageCost) {
        this.damageCost = damageCost;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
