package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Driver;
import model.Insurence;
import model.License;
import model.Vehicle;
import model.tableModel.DriverTM;
import model.tableModel.VehicleTM;
import util.Validation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageDriverFormController {
    public JFXButton btnAddDriver;
    public JFXButton btnModifyDriver;
    public JFXButton btnRemoveDriver;
    public AnchorPane driverDetailContext;
    public TextField txtDriverId;
    public TextField txtDriverName;
    public TextField txtNIC;
    public TextField txtAddress;
    public TextField txtDriverContact;
    public TextField txtDriverStatus;
    public TextField txtLicenseId;
    public TextField txtLicenseNumber;
    public TextField txtVehicleType;
    public TextField txtExpireDate;

    public TableColumn<DriverTM,String> colDriverId;
    public TableColumn<DriverTM,String> colDriverName;
    public TableColumn<DriverTM,String> colNIC;
    public TableColumn<DriverTM,String> colAddress;
    public TableColumn<DriverTM,String> colContact;
    public TableColumn<DriverTM,String> colLicenseId;
    public TableColumn<DriverTM,String> colLicenseNumber;
    public TableColumn<DriverTM,String> colExpireDate;
    public TableColumn<DriverTM,String> colStatus;

    public TableView<DriverTM> tblDriver;


    ObservableList<DriverTM> obList= FXCollections.observableArrayList();


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
        
        viewAllDriver();
        showValidation();
        listenFieldChange(validationList);

        colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        colDriverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colLicenseId.setCellValueFactory(new PropertyValueFactory<>("licenseId"));
        colLicenseNumber.setCellValueFactory(new PropertyValueFactory<>("licenseNo"));
        colExpireDate.setCellValueFactory(new PropertyValueFactory<>("expireDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

        tblDriver.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> {
                    setDriverData(newValue);
                }
        );

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
        validationList.put(txtNIC,nicPattern);
        validationList.put(txtAddress,addressPattern);
        validationList.put(txtDriverContact,contactPattern);
        validationList.put(txtDriverStatus,statusPattern);
        validationList.put(txtLicenseNumber,licenseNopattern);
        validationList.put(txtLicenseId,licenseIdPattern);
        validationList.put(txtExpireDate,expireDatePattern);
    }


    private void setDriverData(DriverTM d) {
        if(d!=null) {
            txtDriverId.setText(d.getDriverId());
            txtDriverName.setText(d.getDriverName());
            txtNIC.setText(d.getNic());
            txtAddress.setText(d.getAddress());
            txtDriverContact.setText(d.getContact());
            txtLicenseId.setText(d.getLicenseId());
            txtLicenseNumber.setText(d.getLicenseNo());
            txtExpireDate.setText(d.getExpireDate());
            txtDriverStatus.setText(d.getStatus());
        }
    }

    public void AddDriverOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (Validation.isAllValidated(validationList)){
            Driver driver1 = new Driver(txtDriverId.getText(),txtDriverName.getText(),txtAddress.getText(),txtDriverContact.getText(),txtNIC.getText(),txtDriverStatus.getText(),txtLicenseId.getText());
            License license1 = new License(txtLicenseId.getText(),txtLicenseNumber.getText(),txtExpireDate.getText());

            if(new LicenseController().saveLicense(license1) && new DriverController().saveDriver(driver1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                viewAllDriver();
            } else{
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }
    }

    private void viewAllDriver() {
        obList.clear();
        ArrayList<Driver> driver=new DriverController().getAllDriver();
        ArrayList<License> license=new LicenseController().getAllLicense();
        ArrayList<DriverTM> driverList=new ArrayList<>();
        for(int i=0; i<driver.size(); i++) {
            Driver d=driver.get(i);
            License l=license.get(i);
            driverList.add(new DriverTM(
                            d.getDriverId(),
                            d.getDriverName(),
                            d.getDriverNIC(),
                            d.getDriverAddress(),
                            d.getDriverContact(),
                            d.getLicenseId(),
                            l.getLicenseNo(),
                            l.getExpireDate(),
                            d.getStatus()
                    )
            );
        }
        obList.addAll(driverList);
        tblDriver.setItems(obList);
    }

    public void ModifyDriverOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DriverModifyForm.fxml");
        Parent load = FXMLLoader.load(resource);
        driverDetailContext.getChildren().clear();
        driverDetailContext.getChildren().add(load);
    }

    public void RemoveDriverOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DriverRemoveForm.fxml");
        Parent load = FXMLLoader.load(resource);
        driverDetailContext.getChildren().clear();
        driverDetailContext.getChildren().add(load);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void textFieldsRealeased(KeyEvent keyEvent) {
    }


    public void backToHomeOnClick(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminDashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        driverDetailContext.getChildren().clear();
        driverDetailContext.getChildren().add(load);
    }

    public void driverAddress_KeyPress(KeyEvent keyEvent) {
    }
}
