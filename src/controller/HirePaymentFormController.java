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

public class HirePaymentFormController {
    public JFXButton btnMakeHirePayment;
    public TextField txtReturnMilage;
    public TextField txtClientName;
    public TextField txtNIC;
    public TextField txtAddress;
    public Label lblHireInvoiceNo;
    public Label lblDate;
    public Label lblTime;
    public TextField txtVehicleId;
    public TextField txtClientId;
    public Label lblTotal;
    public JFXComboBox<String> cmbHireId;
    public TextField txtDriverId;
    public TextField txtTaxiId;
    public TextField txtContact;
    public TextField txtHireCost;
    public TextField txtInitialMileage;


    Pattern mileagePattern = Pattern.compile("^[0-9]*[.][0-9]*$");

    LinkedHashMap<TextField,Pattern> validationList = new LinkedHashMap<>();

    public void initialize() {

        loadDateAndTime();
        setHireInvoiceId();
        showValidation();

        try {
            loadHireId();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        cmbHireId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setHireData(newValue);
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
        validationList.put(txtReturnMilage,mileagePattern);
    }


    private void setHireData(String hireId) throws SQLException, ClassNotFoundException {
        Hire h1 = new HireController().getHire(hireId);
        if (h1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            cmbHireId.setValue(h1.getHireId());
            txtClientId.setText(h1.getClientId());
            txtTaxiId.setText(h1.getTaxiId());
            lblDate.setText(h1.getDate());
            lblTime.setText(h1.getTime());
            txtInitialMileage.setText(String.valueOf(h1.getInitialMilage()));

            String tId = txtTaxiId.getText();
            Taxi t1 = new TaxiController().getTaxi(tId);

            txtDriverId.setText(t1.getDriverId());
            txtVehicleId.setText(t1.getVehicleId());

            String vId = txtVehicleId.getText();
            Vehicle v1 = new VehicleController().getVehicleIdForPayment(vId);

            txtHireCost.setText(String.valueOf(v1.getHireCost()));

            String cId = txtClientId.getText();
            Client c1 = new ClientController().getClient(cId);

            txtClientName.setText(c1.getClientName());
            txtContact.setText(c1.getClientContact());
            txtAddress.setText(c1.getClientAddress());
            txtNIC.setText(c1.getClientNIC());

        }
    }

    private void loadHireId() throws SQLException, ClassNotFoundException {
        List<String> hireIds = new HireController().getHireIds();
        cmbHireId.getItems().addAll(hireIds);
    }

    private void setHireInvoiceId() {
        lblHireInvoiceNo.setText(new HirePaymentController().getHireInvoiceNo());
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void makeHirePaymentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (Validation.isAllValidated(validationList)){
            if(Double.parseDouble(txtReturnMilage.getText()) >= Double.parseDouble(txtInitialMileage.getText())){
                calculateTotal();
                HirePayment hirePayment1 = new HirePayment(lblHireInvoiceNo.getText(),cmbHireId.getValue(), txtClientId.getText(),txtVehicleId.getText(),txtDriverId.getText(),lblTime.getText(),Double.parseDouble(txtReturnMilage.getText()),Double.parseDouble(lblTotal.getText()));
                if (new HirePaymentController().makeHirePayment(hirePayment1)){
                    new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
                    cmbHireId.getItems().clear();
                    setHireInvoiceId();
                    loadHireId();
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again").show();
                }
            }else{
                new Alert(Alert.AlertType.WARNING, "Wrong Initial Mileage").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }
    }

    private void calculateTotal() {
            Double total = (Double.parseDouble(txtReturnMilage.getText()) - Double.parseDouble(txtInitialMileage.getText())) * Double.parseDouble(txtHireCost.getText());
            lblTotal.setText(String.format("%.2f", total));
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void returnMilage_KeyPress(KeyEvent keyEvent) {
    }

    public void textFieldsRealeased(KeyEvent keyEvent) {
    }
}
