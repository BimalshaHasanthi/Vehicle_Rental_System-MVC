package model;

public class License {
    private String licenseId;
    private String licenseNo;
    private String expireDate;

    public License() {
    }

    public License(String licenseId, String licenseNo, String expireDate) {
        this.licenseId = licenseId;
        this.licenseNo = licenseNo;
        this.expireDate = expireDate;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
}
