package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Client;
import util.Validation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ClientRemoveFormController {
    public TextField txtClientId;
    public TextField txtClientName;
    public TextField txtNIC;
    public TextField txtAddress;
    public TextField txtContact;
    public JFXButton btnCancel;
    public JFXButton btnRemoveClient;
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
        validationList.put(txtClientId, idPattern);
        validationList.put(txtClientName, namePattern);
        validationList.put(txtNIC, nicPattern);
        validationList.put(txtAddress, addressPattern);
        validationList.put(txtContact, contactPattern);

    }

    public void backToClientDetailsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AddClientForm.fxml");
        Parent load = FXMLLoader.load(resource);
        loadContext.getChildren().clear();
        loadContext.getChildren().add(load);
    }

    public void clientRemoveOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (Validation.isAllValidated(validationList)){
            if (new ClientController().deleteClient(txtClientId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }
    }

    public void searchIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String clientId = txtClientId.getText();

        Client client1= new ClientController().getClient(clientId);
        if (client1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(client1);
        }
    }

    private void setData(Client client1) {
        txtClientId.setText(client1.getClientId());
        txtClientName.setText(client1.getClientName());
        txtAddress.setText(client1.getClientAddress());
        txtNIC.setText(client1.getClientNIC());
        txtContact.setText(client1.getClientContact());
    }
}
