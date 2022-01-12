package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class ManagerDashboardFormController {
    public AnchorPane loadContext;
    public JFXButton btnManageClient;
    public JFXButton btnMakeHire;
    public JFXButton btnMakeRent;
    public JFXButton btnRentPayment;
    public JFXButton btnHirePayment;
    public JFXButton btnManageRent;
    public JFXButton btnManageHire;
    public JFXButton btnLogout;
    public AnchorPane mainContext;
    public JFXButton btnAvailability;

    public void manageClientOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AddClientForm.fxml");
        Parent load = FXMLLoader.load(resource);
        loadContext.getChildren().clear();
        loadContext.getChildren().add(load);
    }

    public void makeHireOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MakeHireForm.fxml");
        Parent load = FXMLLoader.load(resource);
        loadContext.getChildren().clear();
        loadContext.getChildren().add(load);
    }

    public void makeRentOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/RentVehicleForm.fxml");
        Parent load = FXMLLoader.load(resource);
        loadContext.getChildren().clear();
        loadContext.getChildren().add(load);
    }

    public void rentPaymentOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/RentPaymentForm.fxml");
        Parent load = FXMLLoader.load(resource);
        loadContext.getChildren().clear();
        loadContext.getChildren().add(load);
    }

    public void hirePaymentOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/HirePaymentForm.fxml");
        Parent load = FXMLLoader.load(resource);
        loadContext.getChildren().clear();
        loadContext.getChildren().add(load);
    }

    public void manageRentDetailsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageRentDetailForm.fxml");
        Parent load = FXMLLoader.load(resource);
        loadContext.getChildren().clear();
        loadContext.getChildren().add(load);
    }

    public void manageHireOnAtion(ActionEvent actionEvent) throws IOException {
       URL resource = getClass().getResource("../view/ManageHireDetailForm.fxml");
        Parent load = FXMLLoader.load(resource);
        loadContext.getChildren().clear();
        loadContext.getChildren().add(load);
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/loginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        mainContext.getChildren().clear();
        mainContext.getChildren().add(load);
    }

    public void viewVehicleOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/VehicleAvailabilityForm.fxml");
        Parent load = FXMLLoader.load(resource);
        loadContext.getChildren().clear();
        loadContext.getChildren().add(load);
    }
}
