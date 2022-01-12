package controller;

import db.DBConnection;
import model.License;
import model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LicenseController implements LicenseService {
    @Override
    public boolean saveLicense(License i) throws SQLException, ClassNotFoundException {
        Connection con= DBConnection.getInstance().getConnection();
        String query="INSERT INTO License VALUES(?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,i.getLicenseId());
        stm.setObject(2,i.getLicenseNo());
        stm.setObject(3,i.getExpireDate());
        return stm.executeUpdate()>0;
    }

    @Override
    public License getLicense(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM License WHERE licenseId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            return new License(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
        }else{
            return null;
        }
    }

    @Override
    public boolean modifyLicense(License i) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE License SET licenseNo=?, expireDate=? WHERE licenseId=?");
        stm.setObject(1,i.getLicenseNo());
        stm.setObject(2,i.getExpireDate());
        stm.setObject(3,i.getLicenseId());
        return stm.executeUpdate()>0;
    }

    @Override
    public boolean deleteLicense(String id) throws SQLException, ClassNotFoundException {
        if (DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM License WHERE licenseId='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<License> getAllLicense() {
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM License");
            ResultSet resultSet=statement.executeQuery();
            ArrayList<License> licenseList=new ArrayList<>();
            while(resultSet.next()){
                licenseList.add(new License(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3)
                        )
                );
            }
            return licenseList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
