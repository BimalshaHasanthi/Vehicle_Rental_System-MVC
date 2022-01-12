package controller;

import db.DBConnection;
import model.Driver;
import model.Hire;
import model.Rent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HireController {
    public String getHireId() throws SQLException, ClassNotFoundException {
        try {
            PreparedStatement statement= DBConnection.getInstance().getConnection().prepareStatement("SELECT hireId FROM Hire ORDER BY hireId DESC LIMIT 1");
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()){
                int index=Integer.parseInt(resultSet.getString(1).split("-")[1]);
                if(index<9){
                    return "H-00000"+ ++index;
                }else if(index<99){
                    return "H-0000"+ ++index;
                }else if(index<999){
                    return "H-000"+ ++index;
                }else if(index<9999){
                    return "H-00"+ ++index;
                }else if(index<99999){
                    return "H-0"+ ++index;
                }else{
                    return "H-"+ ++index;
                }
            }else{
                return "H-000001";
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    public boolean makeHire(Hire hires) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Hire VALUES(?,?,?,?,?,?)");
        stm.setObject(1,hires.getHireId());
        stm.setObject(2,hires.getClientId());
        stm.setObject(3,hires.getTaxiId());
        stm.setObject(4,hires.getDate());
        stm.setObject(5,hires.getTime());
        stm.setObject(6,hires.getInitialMilage());
        return stm.executeUpdate()>0;

    }

    public ArrayList<Hire> getAllHires() {
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Hire");
            ResultSet resultSet=statement.executeQuery();
            ArrayList<Hire> hireList=new ArrayList<>();
            while(resultSet.next()){
                hireList.add(new Hire(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getDouble(6)
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

    public Hire getHire(String hireId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Hire WHERE hireId=?");
        stm.setObject(1, hireId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            return new Hire(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6)
            );
        }else{
            return null;
        }
    }

    public boolean modifyHire(Hire i) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Hire SET clientId=?, taxiId=?, date=? , time=? , initialMileage=? WHERE hireId=?");
        stm.setObject(1,i.getClientId());
        stm.setObject(2,i.getTaxiId());
        stm.setObject(3,i.getDate());
        stm.setObject(4,i.getTime());
        stm.setObject(5,i.getInitialMilage());
        stm.setObject(6,i.getHireId());
        return stm.executeUpdate()>0;
    }

    public List<String> getHireIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT hireId FROM Hire WHERE hireId NOT IN (SELECT hireId FROM HirePayment)").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }
}
