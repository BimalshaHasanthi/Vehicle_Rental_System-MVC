package model.tableModel;

public class DriverTM {
    private String driverId;
    private String driverName;
    private String nic;
    private String address;
    private String contact;
    private String licenseId;
    private String licenseNo;
    private String expireDate;
    private String Status;

    public DriverTM() {
    }

    public DriverTM(String driverId, String driverName, String nic, String address, String contact, String licenseId, String licenseNo, String expireDate, String status) {
        this.setDriverId(driverId);
        this.setDriverName(driverName);
        this.setNic(nic);
        this.setAddress(address);
        this.setContact(contact);
        this.setLicenseId(licenseId);
        this.setLicenseNo(licenseNo);
        this.setExpireDate(expireDate);
        setStatus(status);
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
