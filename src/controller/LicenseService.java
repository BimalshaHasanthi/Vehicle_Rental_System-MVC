package controller;

import model.Driver;
import model.License;

import java.sql.SQLException;

public interface LicenseService {
    public boolean saveLicense(License i) throws SQLException, ClassNotFoundException;
    public License getLicense(String id) throws SQLException, ClassNotFoundException;
    public boolean modifyLicense(License i) throws SQLException, ClassNotFoundException;
    public boolean deleteLicense(String id) throws SQLException, ClassNotFoundException;
}
