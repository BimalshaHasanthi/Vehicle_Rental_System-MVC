package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class loginFormController {

    public AnchorPane loginFormContext;
    public AnchorPane loginFormContext2;
    public JFXTextField txtName;
    public JFXPasswordField txtpassword;
    public JFXButton btnLogin;

    public void loginDashboardOnAction(ActionEvent actionEvent) throws IOException {
        if (txtName.getText().equals("admin") && txtpassword.getText().equals("Admin1234")) {
            URL resource = getClass().getResource("../view/AdminDashboardForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) loginFormContext.getScene().getWindow();
            window.setScene(new Scene(load));
        }else if (txtName.getText().equals("manager") && txtpassword.getText().equals("Manager1234")){
            URL resource = getClass().getResource("../view/ManagerDashboardForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) loginFormContext.getScene().getWindow();
            window.setScene(new Scene(load));
        }else {
            URL resource = getClass().getResource("../view/LoginForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) loginFormContext.getScene().getWindow();
            window.setScene(new Scene(load));

        }
    }
}
