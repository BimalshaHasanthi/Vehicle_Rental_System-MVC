package model;

public class Client {
    private String clientId;
    private String clientName;
    private String clientAddress;
    private String clientNIC;
    private String clientContact;

    public Client() {
    }

    public Client(String clientId, String clientName, String clientAddress, String clientNIC, String clientContact) {
        this.setClientId(clientId);
        this.setClientName(clientName);
        this.setClientAddress(clientAddress);
        this.setClientNIC(clientNIC);
        this.setClientContact(clientContact);
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientNIC() {
        return clientNIC;
    }

    public void setClientNIC(String clientNIC) {
        this.clientNIC = clientNIC;
    }

    public String getClientContact() {
        return clientContact;
    }

    public void setClientContact(String clientContact) {
        this.clientContact = clientContact;
    }
}
