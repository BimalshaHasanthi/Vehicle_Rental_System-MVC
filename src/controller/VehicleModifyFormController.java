package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Insurence;
import model.Vehicle;
import util.Validation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class VehicleModifyFormController {
    public JFXButton btnModify;
    public JFXButton btnCancel;
    public AnchorPane loadDetailContext;

    public TextField txtVehicleId;
    public TextField txtVehicleNo;
    public TextField txtType;
    public TextField txtBrand;
    public TextField txtColour;
    public TextField txtRentCost;
    public TextField txtHireCost;
    public TextField txtDiscount;
    public TextField txtStatus;
    public TextField txtInsurence;
    public TextField txtCompany;
    public TextField txtContact;
    public TextField txtDetail;




    Pattern vehicleIdPattern = Pattern.compile("^(V)[0-9]{3}$");
    Pattern vehicleNoPattern = Pattern.compile("^[0-9A-z,-/ ]*$");
    Pattern vehicleTypePattern = Pattern.compile("^[A-Z][a-z]*([ ][A-Z][a-z]*)*$");
    Pattern brandPattern = Pattern.compile("^[A-Z]*[a-z]*([ ][A-Z]*[a-z]*)*$");
    Pattern colourPatter = Pattern.compile("^[A-Z][a-z]*([ ][A-Z][a-z]*)*$");
    Pattern rentCostPattern = Pattern.compile("^[0-9]*[.][0-9]*$");
    Pattern hireCostPattern = Pattern.compile("^[0-9]*[.][0-9]*$");
    Pattern insurenceIdPattern = Pattern.compile("^(I)[0-9]{3}$");
    Pattern companyPattern = Pattern.compile("^[A-Z]*[a-z]*([ ][A-Z][a-z]*)*$");
    Pattern contactPattern = Pattern.compile("^[0-9]{10}$");
    Pattern detailPattern = Pattern.compile("^[0-9A-z,-/ ]*$");
    Pattern statusPattern = Pattern.compile("^[A-Z][a-z]*([ ][A-Z][a-z]*)*$");
    Pattern discountPattern = Pattern.compile("^[0-9]*[.][0-9]*$");

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
        validationList.put(txtVehicleId,vehicleIdPattern);
        validationList.put(txtBrand,brandPattern);
        validationList.put(txtColour,colourPatter);
        validationList.put(txtDiscount,discountPattern);
        validationList.put(txtHireCost,hireCostPattern);
        validationList.put(txtRentCost,rentCostPattern);
        validationList.put(txtVehicleNo,vehicleNoPattern);
        validationList.put(txtType,vehicleTypePattern);
        validationList.put(txtInsurence,insurenceIdPattern);
        validationList.put(txtCompany,companyPattern);
        validationList.put(txtContact,contactPattern);
        validationList.put(txtDetail,detailPattern);
        validationList.put(txtStatus,statusPattern);
    }




    public void ModifyVehicleOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (Validation.isAllValidated(validationList)){
            Vehicle v1 = new Vehicle(txtVehicleId.getText(),txtVehicleNo.getText(),txtType.getText(),txtBrand.getText(),txtColour.getText(),Double.parseDouble(txtRentCost.getText()),Double.parseDouble(txtHireCost.getText()),Double.parseDouble(txtDiscount.getText()),txtStatus.getText(),txtInsurence.getText());
            Insurence i = new Insurence(txtInsurence.getText(),txtCompany.getText(),txtContact.getText(),txtDetail.getText());
            if (new VehicleController().modifyVehicle(v1) && new InsurenceController().modifyInsurence(i))
                new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            else
                new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void BackToVehicleDetailsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageVehicleForm.fxml");
        Parent load = FXMLLoader.load(resource);
        loadDetailContext.getChildren().clear();
        loadDetailContext.getChildren().add(load);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void searchIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String vehicleId = txtVehicleId.getText();
        Vehicle v1= new VehicleController().getVehicle(vehicleId);
        if (v1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            String insurenceId = v1.getInsurenceId();
            Insurence i=new InsurenceController().getInsurence(insurenceId);
            if (i==null) {
                new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
            } else {
                setData(v1,i);
            }
        }
    }

    private void setData(Vehicle v1, Insurence i) {
        txtVehicleId.setText(v1.getVehicleId());
        txtVehicleNo.setText(v1.getVehicleNo());
        txtType.setText(v1.getVehicleType());
        txtBrand.setText(v1.getBrand());
        txtColour.setText(v1.getColour());
        txtRentCost.setText(String.valueOf(v1.getRentalCost()));
        txtHireCost.setText(String.valueOf(v1.getHireCost()));
        txtDiscount.setText(String.valueOf(v1.getDiscount()));
        txtStatus.setText(v1.getvStatus());
        txtInsurence.setText(i.getInsurenceId());
        txtCompany.setText(i.getCompanyName());
        txtContact.setText(i.getContact());
        txtDetail.setText(i.getDetail());
    }
}
