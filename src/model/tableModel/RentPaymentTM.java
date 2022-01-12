package model.tableModel;

public class RentPaymentTM {
    private String rentInvoiceNo;
    private String rentId;
    private String clientId;
    private String vehicleId;
    private Double total;

    public RentPaymentTM() {
    }

    public RentPaymentTM(String rentInvoiceNo, String rentId, String clientId, String vehicleId, Double total) {
        this.setRentInvoiceNo(rentInvoiceNo);
        this.setRentId(rentId);
        this.setClientId(clientId);
        this.setVehicleId(vehicleId);
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
