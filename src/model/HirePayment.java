package model;

public class HirePayment {
    private String hireInvoiceNo;
    private String hireId;
    private String clientId;
    private String vehicleId;
    private String driverId;
    private String arrivalTime;
    private Double returnMileage;
    private Double totalPayment;

    public HirePayment() {
    }

    public HirePayment(String hireInvoiceNo, String hireId, String clientId, String vehicleId, String driverId, String arrivalTime, Double returnMileage, Double totalPayment) {
        this.setHireInvoiceNo(hireInvoiceNo);
        this.setHireId(hireId);
        this.setClientId(clientId);
        this.setVehicleId(vehicleId);
        this.setDriverId(driverId);
        this.setArrivalTime(arrivalTime);
        this.setReturnMileage(returnMileage);
        this.setTotalPayment(totalPayment);
    }

    public String getHireInvoiceNo() {
        return hireInvoiceNo;
    }

    public void setHireInvoiceNo(String hireInvoiceNo) {
        this.hireInvoiceNo = hireInvoiceNo;
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

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Double getReturnMileage() {
        return returnMileage;
    }

    public void setReturnMileage(Double returnMileage) {
        this.returnMileage = returnMileage;
    }

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }
}
