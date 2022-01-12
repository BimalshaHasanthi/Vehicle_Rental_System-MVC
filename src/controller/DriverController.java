package controller;

import db.DBConnection;
import model.Driver;
import model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverController implements DriverService{
    @Override
    public boolean saveDriver(Driver i) throws SQLException, ClassNotFoundException {
        Connection con= DBConnection.getInstance().getConnection();
        String query="INSERT INTO Driver VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,i.getDriverId());
        stm.setObject(2,i.getDriverName());
        stm.setObject(3,i.getDriverAddress());
        stm.setObject(4,i.getDriverContact());
        stm.setObject(5,i.getDriverNIC());
        stm.setObject(6,i.getLicenseId());
        stm.setObject(7,i.getStatus());
        return stm.executeUpdate()>0;
    }

    @Override
    public Driver getDriver(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Driver WHERE driverId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            return new Driver(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(7),
                    rst.getString(6)
            );
        }else{
            return null;
        }
    }

    @Override
    public boolean modifyDriver(Driver i) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Driver SET driverName=?, driverAddress=?, driverContact=? , driverNIC=? , licenseId=? , dStatus=? WHERE driverId=?");
        stm.setObject(1,i.getDriverName());
        stm.setObject(2,i.getDriverAddress());
        stm.setObject(3,i.getDriverContact());
        stm.setObject(4,i.getDriverNIC());
        stm.setObject(5,i.getLicenseId());
        stm.setObject(6,i.getStatus());
        stm.setObject(7,i.getDriverId());
        return stm.executeUpdate()>0;
    }

    @Override
    public boolean deleteDriver(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    public ArrayList<Driver> getAllDriver() {
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Driver");
            ResultSet resultSet=statement.executeQuery();
            ArrayList<Driver> driverList=new ArrayList<>();
            while(resultSet.next()){
                driverList.add(new Driver(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(7),
                                resultSet.getString(6)
                        )
                );
            }
            return driverList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<String> getDriverIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Driver WHERE dStatus='Available'").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }
}
