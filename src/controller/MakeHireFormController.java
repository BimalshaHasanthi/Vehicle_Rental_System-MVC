package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.*;
import util.Validation;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class MakeHireFormController {
    public JFXButton btnMakeTaxi;
    public JFXButton btnMakeHire;
    public JFXComboBox<String> cmbClientId;
    public TextField txtClientName;
    public TextField txtClientNIC;
    public TextField txtClientAddress;
    public JFXComboBox<String> cmbVehicleId;
    public TextField txtVehicleNo;
    public TextField txtType;
    public TextField txtBrand;
    public TextField txtInsurenceId;
    public TextField txtColour;
    public TextField txtHireCost;
   // public TextField txtDiscount;
    public JFXComboBox<String> cmbDriverId;
    public TextField txtDriverName;
    public TextField txtNIC;
    public TextField txtAddress;
    public TextField txtLicenseId;
    //public TextField txtExpireDate;
    public TextField txtContact;
    public TextField txtClientContact;
    public TextField txtTaxiIdForHire;
    public Label lblDate;
    public Label lblTime;
    public Label lblHireId;
    public Label lblTaxiId;
    public TextField txtInitialMilage;


    Pattern mileagePattern = Pattern.compile("^[0-9]*[.][0-9]*$");

    LinkedHashMap<TextField,Pattern> validationList = new LinkedHashMap<>();


    public void initialize() {

        loadDateAndTime();
        setTaxiId();
        setHireId();
        showValidation();

        try {

            loadVehicleId();
            loadDriverId();
            loadClientId();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        cmbVehicleId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setVehicleData(newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });

        cmbDriverId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setDriverData(newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });

        cmbClientId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setClientData(newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });

        listenFieldChange(validationList);
    }

    private void listenFieldChange(LinkedHashMap<TextField, Pattern> list) {
        for(TextField field: list.keySet()) {
            field.textProperty().addListener((observable, oldValue, newValue) ->{
                Validation.validate(field,list.get(field));
            });
        }
    }

    private void showValidation() {
        validationList.put(txtInitialMilage,mileagePattern);
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setVehicleData(String vehicleId) throws SQLException, ClassNotFoundException {
        Vehicle v1 = new VehicleController().getVehicle(vehicleId);
        if (v1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtVehicleNo.setText(v1.getVehicleNo());
            txtType.setText(v1.getVehicleType());
            txtBrand.setText(v1.getBrand());
            txtColour.setText(v1.getColour());
            txtHireCost.setText(String.valueOf(v1.getHireCost()));
            txtInsurenceId.setText(v1.getInsurenceId());
        }
    }

    private void setDriverData(String driverId) throws SQLException, ClassNotFoundException {
        Driver d1 = new DriverController().getDriver(driverId);
        if (d1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtDriverName.setText(d1.getDriverName());
            txtAddress.setText(d1.getDriverAddress());
            txtContact.setText(d1.getDriverContact());
            txtNIC.setText(d1.getDriverNIC());
            txtLicenseId.setText(d1.getLicenseId());
        }
    }

    private void setClientData(String clientId) throws SQLException, ClassNotFoundException {
        Client c1 = new ClientController().getClient(clientId);
        if (c1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtClientName.setText(c1.getClientName());
            txtClientAddress.setText(c1.getClientAddress());
            txtClientNIC.setText(c1.getClientNIC());
            txtClientContact.setText(c1.getClientContact());
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void loadVehicleId() throws SQLException, ClassNotFoundException {
        List<String> vehicleIds = new VehicleController().getVehicleIds();
        cmbVehicleId.getItems().addAll(vehicleIds);
    }

    private void loadDriverId() throws SQLException, ClassNotFoundException {
        List<String> driverIds = new DriverController().getDriverIds();
        cmbDriverId.getItems().addAll(driverIds);
    }

    private void loadClientId() throws SQLException, ClassNotFoundException {
        List<String> clientIds = new ClientController().getClientIds();
        cmbClientId.getItems().addAll(clientIds);
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.format(DateTimeFormatter.ofPattern("hh:mm:ss a"))
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void setTaxiId(){
        try {
            lblTaxiId.setText(new TaxiController().getTaxiId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void initialMilage_KeyPress(KeyEvent keyEvent) {
    }

    public void textFieldsRealeased(KeyEvent keyEvent) {
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void makeTaxiOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        txtTaxiIdForHire.setText(lblTaxiId.getText());
        new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
        setTaxiId();
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void makeHireOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (Validation.isAllValidated(validationList)){
            Taxi taxis = new Taxi(lblTaxiId.getText(),cmbDriverId.getValue(),cmbVehicleId.getValue());
            Hire hires = new Hire(lblHireId.getText(),cmbClientId.getValue(),txtTaxiIdForHire.getText(),lblDate.getText(),lblTime.getText(),Double.parseDouble(txtInitialMilage.getText()));
            if (new TaxiController().makeTaxi(taxis) && new HireController().makeHire(hires)){
                new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
                txtTaxiIdForHire.clear();
                setHireId();
                cmbVehicleId.getItems().clear();
                cmbDriverId.getItems().clear();
                loadDriverId();
                loadVehicleId();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }
    }

    private void setHireId() {
        try {
            lblHireId.setText(new HireController().getHireId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
