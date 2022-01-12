package model;

public class Taxi {
    private String taxiId;
    private String driverId;
    private String vehicleId;

    public Taxi() {
    }

    public Taxi(String taxiId, String driverId, String vehicleId) {
        this.setTaxiId(taxiId);
        this.setDriverId(driverId);
        this.setVehicleId(vehicleId);
    }

    public String getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(String taxiId) {
        this.taxiId = taxiId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
}
