package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Driver;
import model.Insurence;
import model.License;
import model.Vehicle;
import util.Validation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class DriverRemoveFormController {
    public JFXButton btnCancel;
    public JFXButton btnRemoveDriver;
    public AnchorPane loadDetailContext;
    public TextField txtDriverName;
    public TextField txtDriverAddress;
    public TextField txtDriverNIC;
    public TextField txtContact;
    public TextField txtStatus;
    public TextField txtLicenseId;
    public TextField txtExpireDate;
    public TextField txtLicenseNo;
    public TextField txtDriverId;


    Pattern idPattern = Pattern.compile("^(D)[0-9]{3}$");
    Pattern namePattern = Pattern.compile("^[A-Z][a-z]*([ ][A-Z][a-z]*)*$");
    Pattern nicPattern = Pattern.compile("^([0-9]{9}[x|X|v|V]|[0-9]{12})$");
    Pattern addressPattern = Pattern.compile("^[0-9A-z,-/ ]*$");
    Pattern contactPattern = Pattern.compile("^[0-9]{10}$");
    Pattern statusPattern = Pattern.compile("^[A-Z][a-z]*([ ][A-Z][a-z]*)*$");
    Pattern licenseIdPattern = Pattern.compile("^(L)[0-9]{3}$");
    Pattern licenseNopattern = Pattern.compile("^[A-Z]*[a-z]*[0-9]*$");
    Pattern expireDatePattern = Pattern.compile("^[0-9A-z,-/ ]*$");

    LinkedHashMap<TextField,Pattern> validationList = new LinkedHashMap<>();

    public void initialize(){

        showValidation();
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
        validationList.put(txtDriverId,idPattern);
        validationList.put(txtDriverName,namePattern);
        validationList.put(txtDriverNIC,nicPattern);
        validationList.put(txtDriverAddress,addressPattern);
        validationList.put(txtContact,contactPattern);
        validationList.put(txtStatus,statusPattern);
        validationList.put(txtLicenseNo,licenseNopattern);
        validationList.put(txtLicenseId,licenseIdPattern);
        validationList.put(txtExpireDate,expireDatePattern);
    }



    public void BackToDriverDetailsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageDriverForm.fxml");
        Parent load = FXMLLoader.load(resource);
        loadDetailContext.getChildren().clear();
        loadDetailContext.getChildren().add(load);
    }

    public void DriverRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (Validation.isAllValidated(validationList)){
            if ( new LicenseController().deleteLicense(txtLicenseId.getText()) ) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }
    }

    public void searchDriverOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String driverId = txtDriverId.getText();
        Driver d1= new DriverController().getDriver(driverId);
        if (d1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            String licenseId = d1.getLicenseId();
            License i=new LicenseController().getLicense(licenseId);
            if (i==null) {
                new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
            } else {
                setData(d1,i);
            }
        }
    }

    private void setData(Driver d1, License i) {
        txtDriverId.setText(d1.getDriverId());
        txtDriverName.setText(d1.getDriverName());
        txtDriverNIC.setText(d1.getDriverNIC());
        txtDriverAddress.setText(d1.getDriverAddress());
        txtStatus.setText(d1.getStatus());
        txtContact.setText(d1.getDriverContact());
        txtLicenseId.setText(d1.getLicenseId());
        txtLicenseNo.setText(i.getLicenseId());
        txtExpireDate.setText(i.getExpireDate());
    }
}
