package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class AdminDashboardFormController {
    public AnchorPane manageDetailContext;
    public JFXButton btnManageVehicle;
    public JFXButton btnManageDriver;
    public JFXButton btnAdminReport;
    public JFXButton btnLogout;
    public AnchorPane mainContext;
    public JFXButton btnViewDetails;

    public void ManageVehicleOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageVehicleForm.fxml");
        Parent load = FXMLLoader.load(resource);
        manageDetailContext.getChildren().clear();
        manageDetailContext.getChildren().add(load);
    }

    public void ManageDriverOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageDriverForm.fxml");
        Parent load = FXMLLoader.load(resource);
        manageDetailContext.getChildren().clear();
        manageDetailContext.getChildren().add(load);
    }

    public void AdminReportOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminViewForm.fxml");
        Parent load = FXMLLoader.load(resource);
        manageDetailContext.getChildren().clear();
        manageDetailContext.getChildren().add(load);
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/loginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        mainContext.getChildren().clear();
        mainContext.getChildren().add(load);
    }

    public void viewDetailsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminViewDetailForm.fxml");
        Parent load = FXMLLoader.load(resource);
        manageDetailContext.getChildren().clear();
        manageDetailContext.getChildren().add(load);

    }
}
