package model.tableModel;

public class HireTM {
    private String hireId;
    private String clientId;
    private String taxiId;
    private String vehicleId;
    private String driverId;
    private String date;
    private String time;
    private Double initialMilage;

    public HireTM() {
    }

    public HireTM(String hireId, String clientId, String taxiId, String vehicleId, String driverId, String date, String time, Double initialMilage) {
        this.setHireId(hireId);
        this.setClientId(clientId);
        this.setTaxiId(taxiId);
        this.setVehicleId(vehicleId);
        this.setDriverId(driverId);
        this.setDate(date);
        this.setTime(time);
        this.setInitialMilage(initialMilage);
    }

    public String getHireId() {
        return hireId;
    }

    public void setHireId(String hireId) {
        this.hireId = hireId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(String taxiId) {
        this.taxiId = taxiId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getInitialMilage() {
        return initialMilage;
    }

    public void setInitialMilage(Double initialMilage) {
        this.initialMilage = initialMilage;
    }
}
