package com.example.lab3bhanudahal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Dashboard_Controller  implements Initializable {
    public Label showusernameanddate;
    String usertodiplay;

    public void setUsername(String usertodiplay) {

        this.usertodiplay = usertodiplay;
    }
    String data= String.valueOf(LocalDate.now());
    public void displayUsername() {
        showusernameanddate.setText("welcome "+usertodiplay+data);
    }

    public void gosalary(ActionEvent actionEvent) {
        try {
            // Loading the second view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Salarypage.fxml"));
            Parent root = loader.load();


            Stage stage = (Stage) showusernameanddate.getScene().getWindow();
            //sending username to another controller
            SalaryController secondController  = loader.getController();

            secondController.setUsername(usertodiplay);
            secondController.displayUsername();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();


        }

    }

    public void goemployee(ActionEvent actionEvent) {
        try {
            // Loading the second view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Employee.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) showusernameanddate.getScene().getWindow();
            //sending username to another controller
            Employee_Controller secondController  = loader.getController();
            secondController.setUsername(usertodiplay);
            secondController.displayUsername();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();


        }


    }

    public void logout(ActionEvent actionEvent) {
        try {
            // Loading the second view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login_Page.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) showusernameanddate.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }




    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();

    }
}