package controller;

import model.Insurence;
import model.Vehicle;

import java.sql.SQLException;

public interface InsurenceService {
    public boolean saveInsurence(Insurence i) throws SQLException, ClassNotFoundException;
    public Insurence getInsurence(String id) throws SQLException, ClassNotFoundException;
    public boolean modifyInsurence(Insurence i) throws SQLException, ClassNotFoundException;
    public boolean deleteInsurence(String id) throws SQLException, ClassNotFoundException;
}
