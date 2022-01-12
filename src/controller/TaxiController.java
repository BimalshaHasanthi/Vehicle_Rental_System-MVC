package controller;

import db.DBConnection;
import model.Driver;
import model.Hire;
import model.Taxi;

import java.sql.*;
import java.util.ArrayList;

public class TaxiController {

    public String getTaxiId() throws SQLException, ClassNotFoundException {
        try {
            PreparedStatement statement= DBConnection.getInstance().getConnection().prepareStatement("SELECT taxiId FROM Taxi ORDER BY taxiId DESC LIMIT 1");
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()){
                int index=Integer.parseInt(resultSet.getString(1).split("-")[1]);
                if(index<9){
                    return "T-00000"+ ++index;
                }else if(index<99){
                    return "T-0000"+ ++index;
                }else if(index<999){
                    return "T-000"+ ++index;
                }else if(index<9999){
                    return "T-00"+ ++index;
                }else if(index<99999){
                    return "T-0"+ ++index;
                }else{
                    return "T-"+ ++index;
                }
            }else{
                return "T-000001";
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    public boolean makeTaxi(Taxi taxis) throws SQLException, ClassNotFoundException {
        Connection con=null;
        try {
            con= DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm =con.prepareStatement("INSERT INTO Taxi VALUES(?,?,?)");
            stm.setObject(1,taxis.getTaxiId());
            stm.setObject(2,taxis.getDriverId());
            stm.setObject(3,taxis.getVehicleId());

            if (stm.executeUpdate() > 0){
                if (updateVehicleStatus(taxis.getVehicleId()) && updateDriverStatus(taxis.getDriverId())){
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
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Driver SET dStatus='On Hire' WHERE driverId='" + driverId + "'");
        return stm.executeUpdate()>0;
    }

    private boolean updateVehicleStatus(String vehicleId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Vehicle SET vStatus='On Hire' WHERE vehicleId='" + vehicleId + "'");
        return stm.executeUpdate()>0;
    }

    public ArrayList<Taxi> getAllTaxi() {
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Taxi");
            ResultSet resultSet=statement.executeQuery();
            ArrayList<Taxi> taxiList=new ArrayList<>();
            while(resultSet.next()){
                taxiList.add(new Taxi(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3)
                        )
                );
            }
            return taxiList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Taxi getTaxi(String taxiId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Taxi WHERE taxiId=?");
        stm.setObject(1, taxiId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            return new Taxi(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
        }else{
            return null;
        }
    }

    public boolean modifyTaxi(Taxi l) throws SQLException, ClassNotFoundException {

        Connection con=null;
        try {
            con= DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm =con.prepareStatement("UPDATE Taxi SET driverId=?, vehicleId=? WHERE taxiId=?");

            stm.setObject(1,l.getDriverId());
            stm.setObject(2,l.getVehicleId());
            stm.setObject(3,l.getTaxiId());

            if (stm.executeUpdate() > 0){
                if (updateNewVehicleStatus(l.getVehicleId())){
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

    private boolean updateNewVehicleStatus(String vehicleId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Vehicle SET vStatus='On Hire' WHERE vehicleId='" + vehicleId + "'");
        return stm.executeUpdate()>0;
    }

    public boolean deleteTaxi(String taxiId, String driverId, String vehicleId) throws SQLException, ClassNotFoundException {
        if (updateTaxi(taxiId) && updateDriverStatusRemove(driverId) && updateVehicleStatusRemove(vehicleId)){
            return true;
        }else{
            return false;
        }
    }

    private boolean updateVehicleStatusRemove(String vehicleId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Vehicle SET vStatus='Available' WHERE vehicleId='" + vehicleId + "'");
        return stm.executeUpdate()>0;
    }

    private boolean updateTaxi(String taxiId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Taxi WHERE taxiId='"+taxiId+"'");
        return stm.executeUpdate()>0;
    }

    private boolean updateDriverStatusRemove(String driverId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Driver SET dStatus='Available' WHERE driverId='" + driverId + "'");
        return stm.executeUpdate()>0;
    }

}
