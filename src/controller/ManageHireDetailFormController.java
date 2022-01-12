package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import model.tableModel.HireTM;
import model.tableModel.RentTM;
import util.Validation;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageHireDetailFormController {
    public JFXButton btnModifyHire;
    public JFXButton btnRemoveHire;
    public JFXComboBox<String> cmbHireId;
    public TextField txtDate;
    public TextField txtTime;
    public TextField txtInitialMilage;
    public TableView<HireTM> tblHire;
    public TableColumn<HireTM,String> colHireId;
    public TableColumn<HireTM,String> colClientId;
    public TableColumn<HireTM,String> colTaxiId;
    public TableColumn<HireTM,String> colVehicleId;
    public TableColumn<HireTM,String> colDriverId;
    public TableColumn<HireTM,String> colDate;
    public TableColumn<HireTM,String> colTime;
    public TableColumn<HireTM,Double> colInitialMilage;
    public TextField txtClientId;
    public TextField txtTaxiId;
    public TextField txtDriverId;
    public TextField txtVehicleId;
    public TextField txtHireId;

    ObservableList<HireTM> obList= FXCollections.observableArrayList();

    Pattern hireIdPPattern = Pattern.compile("^(H)[-][0-9]{6}$");
    Pattern clientIdPattern = Pattern.compile("^(C)[0-9]{3}$");
    Pattern vehicleIdPattern = Pattern.compile("^(V)[0-9]{3}$");
    Pattern driverIdPattern = Pattern.compile("^(D)[0-9]{3}$");
    Pattern datePattern=Pattern.compile("^[0-9]{4}([-][0-9]{2}){2}$");
    Pattern timePattern=Pattern.compile("^[0-9]{2}([:][0-9]{2}){2}[ ](AM|PM)$");
    Pattern taxiIdPattern = Pattern.compile("^(T)[-][0-9]{6}$");
    Pattern mileagePattern = Pattern.compile("^[0-9]*[.][0-9]*$");


    LinkedHashMap<TextField,Pattern> validationList = new LinkedHashMap<>();



    public void initialize() {

        viewHire();
        showValidation();

        colHireId.setCellValueFactory(new PropertyValueFactory<>("hireId"));
        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colTaxiId.setCellValueFactory(new PropertyValueFactory<>("taxiId"));
        colInitialMilage.setCellValueFactory(new PropertyValueFactory<>("initialMilage"));

        tblHire.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    setTableHire(newValue);
                }
        );

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
        validationList.put(txtClientId,clientIdPattern);
        validationList.put(txtTaxiId,taxiIdPattern);
        validationList.put(txtHireId,hireIdPPattern);
        validationList.put(txtDriverId,driverIdPattern);
        validationList.put(txtVehicleId,vehicleIdPattern);
        validationList.put(txtDate,datePattern);
        validationList.put(txtTime,timePattern);
        validationList.put(txtInitialMilage,mileagePattern);
    }


    private void viewHire() {
        obList.clear();
        ArrayList<Hire> hire=new HireController().getAllHires();
        ArrayList<Taxi> taxi=new TaxiController().getAllTaxi();
        ArrayList<HireTM> hireList=new ArrayList<>();
        for(int i=0; i<hire.size(); i++) {
            Hire h=hire.get(i);
            Taxi t=taxi.get(i);
            hireList.add(new HireTM(
                        h.getHireId(),
                        h.getClientId(),
                        h.getTaxiId(),
                        t.getVehicleId(),
                        t.getDriverId(),
                        h.getDate(),
                        h.getTime(),
                        h.getInitialMilage()
                    )
            );
        }
        obList.addAll(hireList);
        tblHire.setItems(obList);
    }

    private void setTableHire(HireTM h) {
        if(h!=null) {
            txtHireId.setText(h.getHireId());
            txtTaxiId.setText(h.getTaxiId());
            txtVehicleId.setText(h.getVehicleId());
            txtDriverId.setText(h.getDriverId());
            txtClientId.setText(h.getClientId());
            txtDate.setText(h.getDate());
            txtTime.setText(h.getTime());
            txtInitialMilage.setText(String.valueOf(h.getInitialMilage()));
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void ModifyHireOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       // updateOldVehicleStatus(oldVehicleId);
        if (Validation.isAllValidated(validationList)){
            Hire i = new Hire(txtHireId.getText(),txtClientId.getText(),txtTaxiId.getText(),txtDate.getText(),txtTime.getText(),Double.parseDouble(txtInitialMilage.getText()));
            Taxi l = new Taxi(txtTaxiId.getText(),txtDriverId.getText(),txtVehicleId.getText());
            if (new HireController().modifyHire(i) /*&& new TaxiController().modifyTaxi(l)*/) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                viewHire();
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }
    }

    private void updateOldVehicleStatus(String oldVehicleId) throws SQLException, ClassNotFoundException {
        DBConnection.getInstance().getConnection().prepareStatement("UPDATE Vehicle SET vStatus='Available' WHERE vehicleId='" + oldVehicleId + "'");
    }

    public void RemoveHireOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (Validation.isAllValidated(validationList)){
            if ( new TaxiController().deleteTaxi(txtTaxiId.getText(),txtDriverId.getText(),txtVehicleId.getText()) ) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
                viewHire();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void searchHireOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String hireId = txtHireId.getText();
        Hire h1= new HireController().getHire(hireId);
        if (h1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            String taxiId = h1.getTaxiId();
            Taxi i = new TaxiController().getTaxi(taxiId);
            if (i == null) {
                new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
            } else {
                setData(h1, i);
            }
        }
    }

    private void setData(Hire h1, Taxi i) {
        txtHireId.setText(h1.getHireId());
        txtClientId.setText(h1.getClientId());
        txtTaxiId.setText(h1.getTaxiId());
        txtVehicleId.setText(i.getVehicleId());
        txtDriverId.setText(i.getDriverId());
        txtDate.setText(h1.getDate());
        txtTime.setText(h1.getTime());
        txtInitialMilage.setText(String.valueOf(h1.getInitialMilage()));
    }
}
