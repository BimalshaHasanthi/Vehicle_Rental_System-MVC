package model;

public class Hire {
    private String hireId;
    private String clientId;
    private String taxiId;
    private String date;
    private String time;
    private Double initialMilage;

    public Hire() {
    }

    public Hire(String hireId, String clientId, String taxiId, String date, String time, Double initialMilage) {
        this.setHireId(hireId);
        this.setClientId(clientId);
        this.setTaxiId(taxiId);
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
