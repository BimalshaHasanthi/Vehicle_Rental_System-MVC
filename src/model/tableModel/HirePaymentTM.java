package model.tableModel;

public class HirePaymentTM {
    private String hireInvoiceNo;
    private String hireId;
    private String clientId;
    private Double totalPayment;

    public HirePaymentTM() {
    }

    public HirePaymentTM(String hireInvoiceNo, String hireId, String clientId, Double totalPayment) {
        this.setHireInvoiceNo(hireInvoiceNo);
        this.setHireId(hireId);
        this.setClientId(clientId);
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

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }
}
