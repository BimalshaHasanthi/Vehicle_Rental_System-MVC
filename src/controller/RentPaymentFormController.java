package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.Rent;
import model.RentPayment;
import model.Vehicle;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;

public class RentPaymentFormController {
    //public TextField txtReturnedDate;
    public TextField txtDamageCost;
    public TextField txtDamageDetail;
    public TextField txtDiscount;
    public JFXButton btnMakeRentPayment;
    public TextField txtClientName;
    public TextField txtNIC;
    public TextField txtAddress;
    ////public TextField txtVehicleNo;
    //public TextField txtType;
    public TextField txtBrand;
    //public TextField txtInsurenceId;
    //public TextField txtRentCost;
    public Label lblDate;
    public Label lblTime;
    public TextField txtVehicleId;
    public TextField txtClientId;
    public TextField txtRentedDate;
    public TextField txtNoOfDays;
    public TextField txtAdvance;
    public Label lblTotal;
    public JFXComboBox<String> cmbRentId;
    public TextField txtContact;
    public TextField txtColour;
    public TextField txtRentCost;

    public Label lblInvoiceNo;
    public Label lblNote;



    Pattern damageCostPattern = Pattern.compile("^[0-9]*[.][0-9]*$");
    Pattern damageDetailPattern = Pattern.compile("^[0-9A-z,-/ ]*$");

    LinkedHashMap<TextField,Pattern> validationList = new LinkedHashMap<>();

    public void initialize() {

        loadDateAndTime();
        setRentInvoiceId();
        lblNote.setText("");

        showValidation();
        listenFieldChange(validationList);


        try {
            loadRentId();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        cmbRentId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setRentData(newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });

    }

    private void listenFieldChange(LinkedHashMap<TextField, Pattern> list) {
        for(TextField field: list.keySet()) {
            field.textProperty().addListener((observable, oldValue, newValue) ->{
                Validation.validate(field,list.get(field));
            });
        }
    }

    private void showValidation() {
        validationList.put(txtDamageCost, damageCostPattern);
        validationList.put(txtDamageDetail, damageDetailPattern);
    }


    private void setRentData(String rentId) throws SQLException, ClassNotFoundException {
        Rent r1 = new RentController().getRentId(rentId);
        if (r1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtClientId.setText(r1.getClientId());
            txtVehicleId.setText(r1.getVehicleId());
            txtRentedDate.setText(r1.getRentDate());
            txtAdvance.setText(String.valueOf(r1.getAdvancePayment()));
            txtNoOfDays.setText(String.valueOf(r1.getNoOfDays()));

            String id = txtVehicleId.getText();
            Vehicle v1 = new VehicleController().getVehicleIdForPayment(id);

            txtRentCost.setText(String.valueOf(v1.getRentalCost()));
            txtDiscount.setText(String.valueOf(v1.getDiscount()));

        }

    }

    private void loadRentId() throws SQLException, ClassNotFoundException {
        List<String> rentIds = new RentController().getRentIds();
        cmbRentId.getItems().addAll(rentIds);
    }

    private void setRentInvoiceId() {
        lblInvoiceNo.setText(new RentPaymentController().getRentInvoiceNo());
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void makeRentPaymentOnAction(ActionEvent actionEvent) throws ParseException, SQLException, ClassNotFoundException {

        calculateTotal();
        if (Validation.isAllValidated(validationList)){
            RentPayment rentPayment1 = new RentPayment(lblInvoiceNo.getText(), cmbRentId.getValue(), txtClientId.getText(),txtVehicleId.getText(),txtRentedDate.getText(),txtDamageDetail.getText(),Double.parseDouble(txtDamageCost.getText()),Double.parseDouble(txtDiscount.getText()),Double.parseDouble(lblTotal.getText()));
            if (new RentPaymentController().makeRentPayment(rentPayment1)){
                new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
                cmbRentId.getItems().clear();
                setRentInvoiceId();
                loadRentId();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }

    }

    private void calculateTotal() throws ParseException {

        int usedDays= vehicleUsedDays()+1;
        double usedDaysForTotal = (double) usedDays;

        int noOfDaysOrdered= Integer.parseInt(txtNoOfDays.getText());
       /* double rentPerDay = Double.parseDouble(txtRentCost.getText());
        double damageCost =  Double.parseDouble(txtDamageCost.getText());
        double advance=  Double.parseDouble(txtAdvance.getText());
        */

       /* System.out.println(usedDays);
        System.out.println(Double.parseDouble(txtRentCost.getText()));
        System.out.println(Double.parseDouble(txtDamageCost.getText()));
        System.out.println(Double.parseDouble(txtAdvance.getText()));
        */

        if(usedDays==noOfDaysOrdered){
            double total= (noOfDaysOrdered* Double.parseDouble(txtRentCost.getText()))+Double.parseDouble(txtDamageCost.getText())-Double.parseDouble(txtAdvance.getText());
            Double netTotal=total-total*Double.parseDouble(txtDiscount.getText())/100;
            lblTotal.setText(String.format("%.2f",netTotal));
        }else{
            int delayDays=usedDays-noOfDaysOrdered;
            if(delayDays>0) {
                if (delayDays == 1) {
                    lblNote.setText("Note  : Vehicle Delayed By " + delayDays + " Day");
                } else {
                    lblNote.setText("Note  : Vehicle Delayed By " + delayDays + " Days");
                }
                double total = ((noOfDaysOrdered + delayDays) * Double.parseDouble(txtRentCost.getText())) + Double.parseDouble(txtDamageCost.getText()) - Double.parseDouble(txtAdvance.getText());
                Double netTotal=total-total*Double.parseDouble(txtDiscount.getText())/100;
                lblTotal.setText(String.format("%.2f",netTotal));
            }else {
                double total = (noOfDaysOrdered * Double.parseDouble(txtRentCost.getText())) + Double.parseDouble(txtDamageCost.getText()) - Double.parseDouble(txtAdvance.getText());
                Double netTotal=total-total*Double.parseDouble(txtDiscount.getText())/100;
                lblTotal.setText(String.format("%.2f",netTotal));
            }
        }
    }



    private int vehicleUsedDays() throws ParseException {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date rentedDate = myFormat.parse(txtRentedDate.getText());
        Date returnedDate = myFormat.parse(lblDate.getText());
        int diffInDays = (int) ((returnedDate.getTime() - rentedDate.getTime()) / (1000 * 60 * 60 * 24));
        return diffInDays;
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void discount_KeyPress(KeyEvent keyEvent) {
    }

    public void damageDetail_KeyPress(KeyEvent keyEvent) {
    }

    public void damageCost_KeyPress(KeyEvent keyEvent) {
    }

    public void returnedDate_KeyPress(KeyEvent keyEvent) {
    }

    public void textFieldsRealeased(KeyEvent keyEvent) {
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void PrintOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = null;
            try {
                design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/Report/RentBill.jrxml"));
            } catch (JRException e) {
                e.printStackTrace();
            }
            JasperReport compileReport = JasperCompileManager.compileReport(design);

            /*Setting values for parameters*/
            /*Get the customer id input field value*/
           // String memID =txtMemberId.getText();

           String rentId= cmbRentId.getSelectionModel().getSelectedItem();

           // String rentId= lblInvoiceNo.getText();


            /*Setting parameter values for the report*/
            HashMap map = new HashMap();
            map.put("SearchID", rentId);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

            /*JasperPrintManager.printReport(jasperPrint,false);*/

        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
