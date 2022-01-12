package controller;

import db.DBConnection;
import model.Client;
import model.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientController implements ClientService{
    @Override
    public boolean saveClient(Client i) throws SQLException, ClassNotFoundException {
        Connection con= DBConnection.getInstance().getConnection();
        String query="INSERT INTO Client VALUES(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,i.getClientId());
        stm.setObject(2,i.getClientName());
        stm.setObject(3,i.getClientAddress());
        stm.setObject(4,i.getClientNIC());
        stm.setObject(5,i.getClientContact());
        return stm.executeUpdate()>0;
    }

    @Override
    public Client getClient(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Client WHERE clientId=?");
        stm.setObject(1, id);
        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            return new Client(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }else{
            return null;
        }
    }

    @Override
    public boolean modifyClient(Client i) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Client SET clientName=?, clientAddress=?, clientNIC=? , clientContact=? WHERE clientId=?");
        stm.setObject(1,i.getClientName());
        stm.setObject(2,i.getClientAddress());
        stm.setObject(3,i.getClientNIC());
        stm.setObject(4,i.getClientContact());
        stm.setObject(5,i.getClientId());
        return stm.executeUpdate()>0;
    }

    @Override
    public boolean deleteClient(String id) throws SQLException, ClassNotFoundException {
        if (DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Client WHERE clientId='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<Client> getAllClient() {
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Client");
            ResultSet resultSet=statement.executeQuery();
            ArrayList<Client> clientList=new ArrayList<>();
            while(resultSet.next()){
                clientList.add(new Client(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5)
                        )
                );
            }
            return clientList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<String> getClientIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Client").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

}
