package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.layout.AnchorPane;
import model.Client;
import model.Driver;
import model.License;
import model.tableModel.ClientTM;
import model.tableModel.DriverTM;
import util.Validation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddClientFormController {
    public TextField txtClientId;
    public JFXButton btnAddClient;
    public JFXButton btnModifyClient;
    public JFXButton btnRemoveClient;
    public TextField txtClientName;
    public TextField txtAddress;
    public TextField txtNIC;
    public TextField txtContact;
    public AnchorPane loadContext;
    public JFXTextField txtSearchtId;

    public TableView<ClientTM> tblClient;

    public TableColumn<ClientTM,String> colClientId;
    public TableColumn<ClientTM,String> colClientName;
    public TableColumn<ClientTM,String> colNIC;
    public TableColumn<ClientTM,String> colAddress;
    public TableColumn<ClientTM,String> colContact;

    Pattern idPattern = Pattern.compile("^(C)[0-9]{3}$");
    Pattern namePattern = Pattern.compile("^[A-Z][a-z]*([ ][A-Z][a-z]*)*$");
    Pattern nicPattern = Pattern.compile("^([0-9]{9}[x|X|v|V]|[0-9]{12})$");
    Pattern addressPattern = Pattern.compile("^[0-9A-z,-/ ]*$");
    Pattern contactPattern = Pattern.compile("^[0-9]{10}$");

    ObservableList<ClientTM> obList= FXCollections.observableArrayList();
    LinkedHashMap<TextField,Pattern> validationList = new LinkedHashMap<>();

    public void initialize(){
        showValidation();
        viewAllClient();

        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colClientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("clientAddress"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("clientContact"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("clientNIC"));

        tblClient.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> {
                    setClientData(newValue);
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
        validationList.put(txtClientId,idPattern);
        validationList.put(txtClientName,namePattern);
        validationList.put(txtNIC,nicPattern);
        validationList.put(txtAddress,addressPattern);
        validationList.put(txtContact,contactPattern);
    }

    private void setClientData(ClientTM c) {
        if(c!=null) {
            txtClientId.setText(c.getClientId());
            txtClientName.setText(c.getClientName());
            txtNIC.setText(c.getClientNIC());
            txtAddress.setText(c.getClientAddress());
            txtContact.setText(c.getClientContact());
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void textFieldsRealeased(KeyEvent keyEvent) {
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void AddClientOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (Validation.isAllValidated(validationList)) {
            Client client1 = new Client(txtClientId.getText(), txtClientName.getText(), txtAddress.getText(), txtNIC.getText(), txtContact.getText());
            if (new ClientController().saveClient(client1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                viewAllClient();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }
    }

    private void viewAllClient() {
        obList.clear();
        ArrayList<Client> client=new ClientController().getAllClient();
        ArrayList<ClientTM> clientList=new ArrayList<>();
        for(int i=0; i<client.size(); i++) {
            Client c=client.get(i);
            clientList.add(new ClientTM(
                            c.getClientId(),
                            c.getClientName(),
                            c.getClientAddress(),
                            c.getClientNIC(),
                            c.getClientContact()
                    )
            );
        }
        obList.addAll(clientList);
        tblClient.setItems(obList);
    }

    public void ModifyClientOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ClientModifyForm.fxml");
        Parent load = FXMLLoader.load(resource);
        loadContext.getChildren().clear();
        loadContext.getChildren().add(load);
    }

    public void RemoveClientOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ClientRemoveForm.fxml");
        Parent load = FXMLLoader.load(resource);
        loadContext.getChildren().clear();
        loadContext.getChildren().add(load);
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void clientId_KeyPress(KeyEvent keyEvent) {
    }

    public void clientName_KeyPress(KeyEvent keyEvent) {
    }

    public void clientAddress_KeyPress(KeyEvent keyEvent) {
    }

    public void clientNIC_KeyPress(KeyEvent keyEvent) {
    }

    public void contact_KeyPress(KeyEvent keyEvent) {
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void searchClientOnAction(ActionEvent actionEvent) {
    }
}


