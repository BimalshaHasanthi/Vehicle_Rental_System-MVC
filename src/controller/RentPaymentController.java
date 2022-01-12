package controller;

import db.DBConnection;
import model.RentPayment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RentPaymentController {

    public String getRentInvoiceNo() {
        try {
            PreparedStatement statement= DBConnection.getInstance().getConnection().prepareStatement("SELECT rentInvoiceNo FROM RentPayment ORDER BY rentInvoiceNo DESC LIMIT 1");
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()){
                int index=Integer.parseInt(resultSet.getString(1).split("-")[1]);
                if(index<9){
                    return "RP-00000"+ ++index;
                }else if(index<99){
                    return "RP-0000"+ ++index;
                }else if(index<999){
                    return "RP-000"+ ++index;
                }else if(index<9999){
                    return "RP-00"+ ++index;
                }else if(index<99999){
                    return "RP-0"+ ++index;
                }else{
                    return "RP-"+ ++index;
                }
            }else{
                return "RP-000001";
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    public boolean makeRentPayment(RentPayment rentPayment1) {
        Connection con=null;
        try {
            con= DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm =con.prepareStatement("INSERT INTO RentPayment VALUES(?,?,?,?,?,?,?,?,?)");

            stm.setObject(1, rentPayment1.getRentInvoiceNo());
            stm.setObject(2, rentPayment1.getRentId());
            stm.setObject(3, rentPayment1.getClientId());
            stm.setObject(4, rentPayment1.getVehicleId());
            stm.setObject(5, rentPayment1.getReturnedDate());
            stm.setObject(6, rentPayment1.getDamageDetail());
            stm.setObject(7, rentPayment1.getDamageCost());
            stm.setObject(8, rentPayment1.getDiscount());
            stm.setObject(9, rentPayment1.getTotal());

            if (stm.executeUpdate() > 0){
                if (updateVehicleStatus(rentPayment1.getVehicleId())){
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

    private boolean updateVehicleStatus(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Vehicle SET vStatus='Available' WHERE vehicleId='" + id + "'");
        return stm.executeUpdate()>0;
    }

    public ArrayList<RentPayment> getAllRent() {
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM RentPayment");
            ResultSet resultSet=statement.executeQuery();
            ArrayList<RentPayment> rentList=new ArrayList<>();
            while(resultSet.next()){
                rentList.add(new RentPayment(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getDouble(7),
                                resultSet.getDouble(8),
                                resultSet.getDouble(9)
                        )
                );
            }
            return rentList;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }
}
