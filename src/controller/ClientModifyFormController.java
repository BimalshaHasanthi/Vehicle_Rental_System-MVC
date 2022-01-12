package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Client;
import model.Insurence;
import model.Vehicle;
import util.Validation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ClientModifyFormController {
    public TextField txtClientId;
    public TextField txtClientName;
    public TextField txtAddress;
    public TextField txtNIC;
    public TextField txtContact;
    public JFXButton btnModify;
    public JFXButton btnCancel;
    public AnchorPane loadContext;

    Pattern idPattern = Pattern.compile("^(C)[0-9]{3}$");
    Pattern namePattern = Pattern.compile("^[A-Z][a-z]*([ ][A-Z][a-z]*)*$");
    Pattern nicPattern = Pattern.compile("^([0-9]{9}[x|X|v|V]|[0-9]{12})$");
    Pattern addressPattern = Pattern.compile("^[0-9A-z,-/ ]*$");
    Pattern contactPattern = Pattern.compile("^[0-9]{10}$");

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
        validationList.put(txtClientId,idPattern);
        validationList.put(txtClientName,namePattern);
        validationList.put(txtNIC,nicPattern);
        validationList.put(txtAddress,addressPattern);
        validationList.put(txtContact,contactPattern);
    }

    public void modifyClientOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (Validation.isAllValidated(validationList)){
            Client i = new Client(txtClientId.getText(),txtClientName.getText(),txtAddress.getText(),txtNIC.getText(),txtContact.getText());
            if (new ClientController().modifyClient(i))
                new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            else
                new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }
    }

    public void backToClientDetailsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AddClientForm.fxml");
        Parent load = FXMLLoader.load(resource);
        loadContext.getChildren().clear();
        loadContext.getChildren().add(load);
    }

    public void searchIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String clientId = txtClientId.getText();
        Client c1= new ClientController().getClient(clientId);
        if (c1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(c1);
        }
    }

    private void setData(Client c1) {
        txtClientId.setText(c1.getClientId());
        txtClientName.setText(c1.getClientName());
        txtAddress.setText(c1.getClientAddress());
        txtNIC.setText(c1.getClientNIC());
        txtContact.setText(c1.getClientContact());
    }
}

