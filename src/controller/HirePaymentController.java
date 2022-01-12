package controller;

import db.DBConnection;
import model.HirePayment;
import model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HirePaymentController {

    public String getHireInvoiceNo() {
        try {
            PreparedStatement statement= DBConnection.getInstance().getConnection().prepareStatement("SELECT hireInvoiceNo FROM HirePayment ORDER BY hireInvoiceNo DESC LIMIT 1");
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()){
                int index=Integer.parseInt(resultSet.getString(1).split("-")[1]);
                if(index<9){
                    return "HP-00000"+ ++index;
                }else if(index<99){
                    return "HP-0000"+ ++index;
                }else if(index<999){
                    return "HP-000"+ ++index;
                }else if(index<9999){
                    return "HP-00"+ ++index;
                }else if(index<99999){
                    return "HP-0"+ ++index;
                }else{
                    return "HP-"+ ++index;
                }
            }else{
                return "HP-000001";
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    public boolean makeHirePayment(HirePayment hirePayment1) {
        Connection con=null;
        try {
            con= DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm =con.prepareStatement("INSERT INTO HirePayment VALUES(?,?,?,?,?,?,?,?)");

            stm.setObject(1, hirePayment1.getHireInvoiceNo());
            stm.setObject(2, hirePayment1.getHireId());
            stm.setObject(3, hirePayment1.getClientId());
            stm.setObject(4, hirePayment1.getVehicleId());
            stm.setObject(5, hirePayment1.getDriverId());
            stm.setObject(6, hirePayment1.getArrivalTime());
            stm.setObject(7, hirePayment1.getReturnMileage());
            stm.setObject(8, hirePayment1.getTotalPayment());


            if (stm.executeUpdate() > 0){
                if (updateVehicleStatus(hirePayment1.getVehicleId()) && updateDriverStatus(hirePayment1.getDriverId())){
                    con.commit();
                    return true;
                }else{
                    con.rollback();
                    return false;
                }
            }else{
                con.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    private boolean updateDriverStatus(String driverId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Driver SET dStatus='Available' WHERE driverId='" + driverId + "'");
        return stm.executeUpdate()>0;
    }

    private boolean updateVehicleStatus(String vehicleId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Vehicle SET vStatus='Available' WHERE vehicleId='" + vehicleId + "'");
        return stm.executeUpdate()>0;
    }

    public ArrayList<HirePayment> getAllHires() {
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM HirePayment");
            ResultSet resultSet=statement.executeQuery();
            ArrayList<HirePayment> hireList=new ArrayList<>();
            while(resultSet.next()){
                hireList.add(new HirePayment(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getDouble(7),
                                resultSet.getDouble(8)
                        )
                );
            }
            return hireList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
