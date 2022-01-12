package controller;

import db.DBConnection;
import model.Rent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentController {

    public String getRentId(){
        try {
            PreparedStatement statement= DBConnection.getInstance().getConnection().prepareStatement("SELECT rentId FROM Rent ORDER BY rentId DESC LIMIT 1");
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()){
                int index=Integer.parseInt(resultSet.getString(1).split("-")[1]);
                if(index<9){
                    return "R-00000"+ ++index;
                }else if(index<99){
                    return "R-0000"+ ++index;
                }else if(index<999){
                    return "R-000"+ ++index;
                }else if(index<9999){
                    return "R-00"+ ++index;
                }else if(index<99999){
                    return "R-0"+ ++index;
                }else{
                    return "R-"+ ++index;
                }
            }else{
                return "R-000001";
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    public boolean makeRent(Rent rents) {

        Connection con=null;
        try {
            con= DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm =con.prepareStatement("INSERT INTO Rent VALUES(?,?,?,?,?,?,?,?)");

            stm.setObject(1, rents.getRentId());
            stm.setObject(2, rents.getClientId());
            stm.setObject(3, rents.getVehicleId());
            stm.setObject(4, rents.getDate());
            stm.setObject(5, rents.getRentDate());
            stm.setObject(6, rents.getTime());
            stm.setObject(7, rents.getNoOfDays());
            stm.setObject(8, rents.getAdvancePayment());

            if (stm.executeUpdate() > 0){
                if (updateVehicleStatus(rents.getVehicleId(),rents.getRentDate(),rents.getDate())){
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

    private boolean updateVehicleStatus(String vehicleId, String rentDate, String date) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Vehicle SET vStatus='On Rent' WHERE vehicleId='" + vehicleId + "'");
        return stm.executeUpdate()>0;
    }

    public Rent getRentId(String rentId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Rent WHERE rentId=?");
        stm.setObject(1, rentId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            return new Rent(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(6),
                    rst.getInt(7),
                    rst.getDouble(8),
                    rst.getString(5)
            );
        }else{
            return null;
        }
    }

    public List<String> getRentIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT rentId FROM Rent WHERE rentId NOT IN (SELECT rentId FROM RentPayment)").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    public boolean modifyRent(Rent i) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Rent SET clientId=?, vehicleId=?, rentDate=? , rentedDate=? , rentTime=? , noOfDays=? , advance=? WHERE rentId=?");
        stm.setObject(1,i.getClientId());
        stm.setObject(2,i.getVehicleId());
        stm.setObject(3,i.getDate());
        stm.setObject(4,i.getDate());
        stm.setObject(5,i.getTime());
        stm.setObject(6,i.getNoOfDays());
        stm.setObject(7,i.getAdvancePayment());
        stm.setObject(8,i.getRentId());
        return stm.executeUpdate()>0;
    }

    public ArrayList<Rent> getAllRents() {
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Rent");
            ResultSet resultSet=statement.executeQuery();
            ArrayList<Rent> rentList=new ArrayList<>();
            while(resultSet.next()){
                rentList.add(new Rent(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(6),
                        resultSet.getInt(7),
                        resultSet.getDouble(8),
                        resultSet.getString(5)
                        )
                );
            }
            return rentList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();

    }

    public boolean deleteRent(String id, String vehicleId) throws SQLException, ClassNotFoundException {
        if (DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Rent WHERE rentId='" + id + "'").executeUpdate() > 0) {
            if (DBConnection.getInstance().getConnection().prepareStatement("UPDATE Vehicle SET vStatus='Available' WHERE vehicleId='" + vehicleId + "'").executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
