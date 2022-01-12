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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.Client;
import model.Rent;
import model.Vehicle;
import util.Validation;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class RentVehicleFormController {
    public TextField txtNoOfDays;
    public TextField txtAdvancePayment;
   // public JFXButton btnrent;
    public TextField txtRentedDate;
    public JFXComboBox<String> cmbClientId;
    public TextField txtClientName;
    public TextField txtClientId;
    public TextField txtAddress;
    public JFXComboBox<String> cmbVehicleId;
    public TextField txtVehicleNo;
    public TextField txtVehicleType;
    public TextField txtBrand;
    public TextField txtInsurenceId;
    public TextField txtColour;
    public TextField txtRentCost;
    public Label lblRentId;
    public Label lblDate;
    public Label lblTime;
    public JFXButton btnRent;
    public TextField txtDiscount;
    public TextField txtContact;
    public TextField txtNIC;

    Pattern noOfDaysPattern = Pattern.compile("^[0-9]*$");
    Pattern advancePattern = Pattern.compile("^[0-9]*[.][0-9]*$");

    LinkedHashMap<TextField,Pattern> validationList = new LinkedHashMap<>();

    public void initialize() {

        loadDateAndTime();
        setRentId();
        showValidation();

        listenFieldChange(validationList);

        try {
            loadClientId();
            loadVehicleId();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        cmbClientId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setClientData(newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });

        cmbVehicleId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setVehicleData(newValue);
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
        validationList.put(txtNoOfDays,noOfDaysPattern);
        validationList.put(txtAdvancePayment,advancePattern);
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

    private void loadVehicleId() throws SQLException, ClassNotFoundException {
        List<String> vehicleIds = new VehicleController().getVehicleIds();
        cmbVehicleId.getItems().addAll(vehicleIds);
    }

    private void loadClientId() throws SQLException, ClassNotFoundException {
        List<String> clientIds = new ClientController().getClientIds();
        cmbClientId.getItems().addAll(clientIds);
    }

    private void setVehicleData(String vehicleId) throws SQLException, ClassNotFoundException {
        Vehicle v1 = new VehicleController().getVehicle(vehicleId);
        if (v1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtVehicleNo.setText(v1.getVehicleNo());
            txtVehicleType.setText(v1.getVehicleType());
            txtBrand.setText(v1.getBrand());
            txtColour.setText(v1.getColour());
            txtDiscount.setText(String.valueOf(v1.getDiscount()));
            txtRentCost.setText(String.valueOf(v1.getRentalCost()));
            txtInsurenceId.setText(v1.getInsurenceId());
        }
    }

    private void setClientData(String clientId) throws SQLException, ClassNotFoundException {
        Client c1 = new ClientController().getClient(clientId);
        if (c1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtClientName.setText(c1.getClientName());
            txtAddress.setText(c1.getClientAddress());
            txtContact.setText(c1.getClientContact());
            txtNIC.setText(c1.getClientNIC());
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setRentId() { lblRentId.setText(new RentController().getRentId()); }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void makeRentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (Validation.isAllValidated(validationList)){
            Rent rents = new Rent(lblRentId.getText(), cmbClientId.getValue(), cmbVehicleId.getValue(),lblDate.getText(),lblTime.getText(),Integer.parseInt(txtNoOfDays.getText()),Double.parseDouble(txtAdvancePayment.getText()),lblDate.getText());
            if (new RentController().makeRent(rents)){
                new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
                setRentId();
                cmbVehicleId.getItems().clear();
                loadVehicleId();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void rentedDate_KeyPress(KeyEvent keyEvent) {
    }

    public void noOfDays_KeyPress(KeyEvent keyEvent) {
    }

    public void advancePayment_KeyPress(KeyEvent keyEvent) {
    }

    public void textFieldsRealeased(KeyEvent keyEvent) {
    }
}
