package controller;

import model.Driver;
import model.Insurence;

import java.sql.SQLException;

public interface DriverService {
    public boolean saveDriver(Driver i) throws SQLException, ClassNotFoundException;
    public Driver getDriver(String id) throws SQLException, ClassNotFoundException;
    public boolean modifyDriver(Driver i) throws SQLException, ClassNotFoundException;
    public boolean deleteDriver(String id) throws SQLException, ClassNotFoundException;
}
