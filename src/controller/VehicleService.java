package controller;

import model.Vehicle;

import java.sql.SQLException;

public interface VehicleService {
    public boolean saveVehicle(Vehicle v) throws SQLException, ClassNotFoundException;
    public Vehicle getVehicle(String id) throws SQLException, ClassNotFoundException;
    public boolean modifyVehicle(Vehicle v) throws SQLException, ClassNotFoundException;
    public boolean deleteVehicle(String id) throws SQLException, ClassNotFoundException;
}
