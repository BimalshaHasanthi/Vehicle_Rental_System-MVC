package controller;

import model.Client;
import model.Driver;

import java.sql.SQLException;

public interface ClientService {
    public boolean saveClient(Client i) throws SQLException, ClassNotFoundException;
    public Client getClient(String id) throws SQLException, ClassNotFoundException;
    public boolean modifyClient(Client i) throws SQLException, ClassNotFoundException;
    public boolean deleteClient(String id) throws SQLException, ClassNotFoundException;
}
