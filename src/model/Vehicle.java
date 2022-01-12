package model;

public class Vehicle {
    private String vehicleId;
    private String vehicleNo;
    private String vehicleType;
    private String brand;
    private String colour;
    private Double rentalCost;
    private Double hireCost;
    private Double discount;
    private String vStatus;
    private String insurenceId;

    public Vehicle() {
    }

    public Vehicle(String vehicleId, String vehicleNo, String vehicleType, String brand, String colour, Double rentalCost, Double hireCost, Double discount, String vStatus, String insurenceId) {
        this.vehicleId = vehicleId;
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.brand = brand;
        this.colour = colour;
        this.rentalCost = rentalCost;
        this.hireCost = hireCost;
        this.discount = discount;
        this.vStatus = vStatus;
        this.insurenceId = insurenceId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Double getRentalCost() {
        return rentalCost;
    }

    public void setRentalCost(Double rentalCost) {
        this.rentalCost = rentalCost;
    }

    public Double getHireCost() {
        return hireCost;
    }

    public void setHireCost(Double hireCost) {
        this.hireCost = hireCost;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getvStatus() {
        return vStatus;
    }

    public void setvStatus(String vStatus) {
        this.vStatus = vStatus;
    }

    public String getInsurenceId() {
        return insurenceId;
    }

    public void setInsurenceId(String insurenceId) {
        this.insurenceId = insurenceId;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId='" + vehicleId + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", brand='" + brand + '\'' +
                ", colour='" + colour + '\'' +
                ", rentalCost=" + rentalCost +
                ", hireCost=" + hireCost +
                ", discount=" + discount +
                ", vStatus='" + vStatus + '\'' +
                ", insurenceId='" + insurenceId + '\'' +
                '}';
    }
}
