package model;

public class Insurence {
    private String insurenceId;
    private String companyName;
    private String contact;
    private String detail;

    public Insurence() {
    }

    public Insurence(String insurenceId, String companyName, String contact, String detail) {
        this.insurenceId = insurenceId;
        this.companyName = companyName;
        this.contact = contact;
        this.detail = detail;
    }

    public String getInsurenceId() {
        return insurenceId;
    }

    public void setInsurenceId(String insurenceId) {
        this.insurenceId = insurenceId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
