package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import model.*;
import model.tableModel.DriverTM;
import model.tableModel.RentTM;
import util.Validation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageRentDetailFormController {
    public JFXButton btnModifyRent;
    public JFXButton btnRemoveRent;
    public TextField txtDate;
    public TextField txtTime;
    public TextField txtAdvance;
    public TextField txtNoOfDays;
    public TextField txtVehicleId;
    public TextField txtClientId;
    public JFXComboBox<String> cmbRentId;
    public TextField txtRentedDate;

    public TableView<RentTM> tblRent;
    public TableColumn<RentTM,String> colRentId;
    public TableColumn<RentTM,String> colClientId;
    public TableColumn<RentTM,String> colVehicleId;
    public TableColumn<RentTM,String> colDate;
    public TableColumn<RentTM,String> colTime;
    public TableColumn<RentTM,Integer> colNoOfDays;
    public TableColumn<RentTM,Double> colAdvance;
    public TextField txtRentId;


    ObservableList<RentTM> obList= FXCollections.observableArrayList();

    Pattern rentIdPattern = Pattern.compile("^(R)[-][0-9]{6}$");
    Pattern clientIdPattern = Pattern.compile("^(C)[0-9]{3}$");
    Pattern vehicleIdPattern = Pattern.compile("^(V)[0-9]{3}$");
    Pattern datePattern=Pattern.compile("^[0-9]{4}([-][0-9]{2}){2}$");
    Pattern timePattern=Pattern.compile("^[0-9]{2}([:][0-9]{2}){2}[ ](AM|PM)$");
    Pattern noOfDaysPattern = Pattern.compile("^[0-9]*$");
    Pattern advancePattern = Pattern.compile("^[0-9]*[.][0-9]*$");

    LinkedHashMap<TextField,Pattern> validationList = new LinkedHashMap<>();

    public void initialize() {

        viewRent();
        showValidation();

        colRentId.setCellValueFactory(new PropertyValueFactory<>("rentId"));
        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colNoOfDays.setCellValueFactory(new PropertyValueFactory<>("NoOfDays"));
        colAdvance.setCellValueFactory(new PropertyValueFactory<>("advancePayment"));

        tblRent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> {
                    setTableRent(newValue);
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
        validationList.put(txtRentId,rentIdPattern);
        validationList.put(txtNoOfDays,noOfDaysPattern);
        validationList.put(txtVehicleId,vehicleIdPattern);
        validationList.put(txtDate,datePattern);
        validationList.put(txtTime,timePattern);
        validationList.put(txtAdvance,advancePattern);
    }

    private void setTableRent(RentTM r) {
        if(r!=null) {
            txtRentId.setText(r.getRentId());
            txtVehicleId.setText(r.getVehicleId());
            txtClientId.setText(r.getClientId());
            txtDate.setText(r.getDate());
            txtTime.setText(r.getTime());
            txtNoOfDays.setText(String.valueOf(r.getNoOfDays()));
            txtAdvance.setText(String.valueOf(r.getAdvancePayment()));
        }
    }

    private void viewRent() {
        obList.clear();
        ArrayList<Rent> rent=new RentController().getAllRents();
        ArrayList<RentTM> rentList=new ArrayList<>();
        for(int i=0; i<rent.size(); i++) {
            Rent r=rent.get(i);
            rentList.add(new RentTM(
                        r.getRentId(),
                        r.getClientId(),
                        r.getVehicleId(),
                        r.getDate(),
                        r.getTime(),
                        r.getNoOfDays(),
                        r.getAdvancePayment(),
                        r.getRentDate()
                    )
            );
        }
        obList.addAll(rentList);
        tblRent.setItems(obList);
    }

    private void setRentData(Rent r1) throws SQLException, ClassNotFoundException {
            txtVehicleId.setText(r1.getVehicleId());
            txtClientId.setText(r1.getClientId());
            txtDate.setText(r1.getDate());
            txtTime.setText(r1.getTime());
            txtNoOfDays.setText(String.valueOf(r1.getNoOfDays()));
            txtAdvance.setText(String.valueOf(r1.getAdvancePayment()));
    }

    public void modifyRentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (Validation.isAllValidated(validationList)){
            Rent i = new Rent(txtRentId.getText(),txtClientId.getText(),txtVehicleId.getText(),txtDate.getText(),txtTime.getText(),Integer.parseInt(txtNoOfDays.getText()),Double.parseDouble(txtAdvance.getText()),txtDate.getText());
            if (new RentController().modifyRent(i)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                viewRent();
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }
    }

    public void removeRentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (Validation.isAllValidated(validationList)){
            if (new RentController().deleteRent(txtRentId.getText(),txtVehicleId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
                viewRent();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }
    }

    public void searchRentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String rentId = txtRentId.getText();
        Rent r1= new RentController().getRentId(rentId);
        if (r1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setRentData(r1);
        }
    }
}
