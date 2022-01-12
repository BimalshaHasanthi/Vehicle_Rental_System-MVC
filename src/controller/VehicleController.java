package controller;

import db.DBConnection;
import model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleController implements VehicleService {
    @Override
    public boolean saveVehicle(Vehicle v) throws SQLException, ClassNotFoundException {
        Connection con= DBConnection.getInstance().getConnection();
        String query="INSERT INTO Vehicle VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,v.getVehicleId());
        stm.setObject(2,v.getVehicleNo());
        stm.setObject(3,v.getVehicleType());
        stm.setObject(4,v.getBrand());
        stm.setObject(5,v.getColour());
        stm.setObject(6,v.getInsurenceId());
        stm.setObject(7,v.getRentalCost());
        stm.setObject(8,v.getHireCost());
        stm.setObject(9,v.getvStatus());
        stm.setObject(10,v.getDiscount());
        return stm.executeUpdate()>0;
    }

    @Override
    public Vehicle getVehicle(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Vehicle WHERE vehicleId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            return new Vehicle(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(7),
                    rst.getDouble(8),
                    rst.getDouble(10),
                    rst.getString(9),
                    rst.getString(6)
            );
        }else{
            return null;
        }
    }

    @Override
    public boolean modifyVehicle(Vehicle v) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Vehicle SET vehicleNumber=?, vehicleType=?, vehicleBrand=? , vehicleColour=? , insurenceId=? , rentalCost=? , taxiCost=? , vStatus=? , discount=? WHERE vehicleId=?");
        stm.setObject(1,v.getVehicleNo());
        stm.setObject(2,v.getVehicleType());
        stm.setObject(3,v.getBrand());
        stm.setObject(4,v.getColour());
        stm.setObject(5,v.getInsurenceId());
        stm.setObject(6,v.getRentalCost());
        stm.setObject(7,v.getHireCost());
        stm.setObject(8,v.getvStatus());
        stm.setObject(9,v.getDiscount());
        stm.setObject(10,v.getVehicleId());
        return stm.executeUpdate()>0;
    }

    @Override
    public boolean deleteVehicle(String id) throws SQLException, ClassNotFoundException {
        if (DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Vehicle WHERE vehicleId='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<Vehicle> getAllVehicle(){

        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Vehicle");
            ResultSet resultSet=statement.executeQuery();
            ArrayList<Vehicle> vehicleList=new ArrayList<>();
            while(resultSet.next()){
                vehicleList.add(new Vehicle(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getDouble(7),
                                resultSet.getDouble(8),
                                resultSet.getDouble(10),
                                resultSet.getString(9),
                                resultSet.getString(6)
                        )
                );
            }
            return vehicleList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public String getVehicleId() {
        PreparedStatement statement= null;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement("SELECT vehicleId FROM Vehicle WHERE vStatus='Available' ORDER BY vehicleId DESC LIMIT 1");
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()){
                int index=Integer.parseInt(resultSet.getString(1));
                return String.valueOf(index);
            }else{
                return "";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public List<String> getVehicleIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Vehicle WHERE vStatus='Available'").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    public Vehicle getVehicleIdForPayment(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Vehicle WHERE vehicleId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            return new Vehicle(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(7),
                    rst.getDouble(8),
                    rst.getDouble(10),
                    rst.getString(9),
                    rst.getString(6)
            );
        }else{
            return null;
        }
    }
}
