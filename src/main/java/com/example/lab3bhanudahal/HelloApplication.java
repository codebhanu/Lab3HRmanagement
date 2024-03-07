package com.example.lab3bhanudahal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login_Page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 380, 500);
        stage.setTitle("HR Management!Bhanu Dahal ");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    public  double totalsalary(double monthlysalary){
        double totalyearsalary=12*monthlysalary;
        return totalyearsalary;
    }
}