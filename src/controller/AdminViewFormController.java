package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminViewFormController {
    public AnchorPane manageDetailContext;
    public JFXButton btnPrintReport;

    public Label lblRented;
    public Label lblHired;
    public Label lblAvailable;
    public Label lblRentIncome;
    public Label lblHireIncome;
    public Label lblTotalIncome;
    public JFXButton btnHireIncomeReport;
    public JFXButton btnRentIncomeReport;

    public void initialize()  {
        try {
            loadAvailableVehicles();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        try {
            loadRentedVehicles();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        try {
            loadHiredVehicles();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        try {
            loadRentIncome();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        try {
            loadHireIncome();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        loadTotalIncome();

    }



    private void loadHiredVehicles() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(*) FROM Vehicle WHERE vStatus='On Hire'");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            lblHired.setText(rst.getString(1));
        }

    }

    private void loadRentedVehicles() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(*) FROM Vehicle WHERE vStatus='On Rent'");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            lblRented.setText(rst.getString(1));
        }
    }

    private void loadAvailableVehicles() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(*) FROM Vehicle WHERE vStatus='Available'");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            lblAvailable.setText(rst.getString(1));
        }
        
    }


    private void loadHireIncome() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT SUM(total) FROM HirePayment ");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            Double d = Double.valueOf(rst.getString(1));
            lblHireIncome.setText(String.format("%.2f",d));
        }
    }

    private void loadRentIncome() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT SUM(total) FROM RentPayment ");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            Double d = Double.valueOf(rst.getString(1));
            lblRentIncome.setText(String.format("%.2f",d));
        }

    }
    private void loadTotalIncome() {
        Double netTotal = Double.parseDouble(lblHireIncome.getText())+Double.parseDouble(lblRentIncome.getText());
        lblTotalIncome.setText(String.format("%.2f",netTotal));
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void printReportOnAction(ActionEvent actionEvent) {

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void hireIncomeReportOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/HireIncome.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void rentIncomeReportOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/RentIncome.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void printVehicleOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/VehicleDetails.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void printDriverOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/DriverDetail.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
