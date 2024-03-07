package com.example.lab3bhanudahal;

import com.example.lab3bhanudahal.Model.Database;
import com.example.lab3bhanudahal.Model.EmployeeDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class Employee_Controller implements Initializable {

    @FXML
    private TableColumn<EmployeeDetail, Integer> ID_Col;

    @FXML
    private Button addbut;

    @FXML
    private Button backbut;

    @FXML
    private Button deletebut;

    @FXML
    private TableColumn<EmployeeDetail, String> emailcol;

    @FXML
    private TextField emailinput;

    @FXML
    private Label error;

    @FXML
    private TextField idInput;

    @FXML
    private TableColumn<EmployeeDetail, String> nameCol;

    @FXML
    private TextField nameInput;

    @FXML
    private TableColumn<EmployeeDetail, String> phonecol;

    @FXML
    private TextField phoneinput;

    @FXML
    private Label showuser;

    @FXML
    private TableColumn<EmployeeDetail, String> typecol;

    @FXML
    private TextField typeinput;

    @FXML
    private Button updatebut;


    @FXML
    private TableView<EmployeeDetail> tablefordata;

    String usertodiplay;

    public void setUsername(String usertodiplay) {

        this.usertodiplay = usertodiplay;
    }
    String data= String.valueOf(LocalDate.now());
    public void displayUsername() {
        showuser.setText("welcome "+usertodiplay+data);
    }



    @FXML     // This adds the data in the table
    void addData(ActionEvent event) {
        String query = "INSERT INTO userTable (Name, Phone, Email,Type) VALUES (?, ?, ?,?)";
        executeQuery(query, List.of(nameInput.getText(), phoneinput.getText(), emailinput.getText(),typeinput.getText()));
        makeEmpty();

    }


    @FXML
    void deleteData(ActionEvent event) {
        validateID(); // this function validate the id if it is valid id or not
        if (error.getText().isEmpty()){
            String query = "DELETE FROM userTable WHERE Eid=?";
            executeQuery(query, List.of(idInput.getText()));
            makeEmpty();}
    }

    @FXML
    void updateData(ActionEvent event) {
        validateID();
        if (error.getText().isEmpty()){
            String query = "UPDATE userTable SET Name=?, Phone=?, Email=?,Type=? WHERE Eid=?";
            executeQuery(query, List.of(nameInput.getText(), phoneinput.getText(), emailinput.getText(),typeinput.getText(), idInput.getText()));
            makeEmpty();} // this function makes the input filed clear after the action is finished
    }

    @FXML
    void getData(ActionEvent event) {
        error.setText("");
        showEmployeeDetail(); //This displays the data in the table
    }




    @FXML
    public void viewData(ActionEvent actionEvent) {
        validateID();
        if (error.getText().isEmpty()){
            String query = "SELECT Eid, Name, Phone, Email,Type FROM userTable WHERE Eid=?";
            int ID = Integer.parseInt(idInput.getText());

            try (Connection conn = Database.getConnection();
                 PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setInt(1, ID);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    error.setText(""); // this remove the error when user input the id that exist in the database
                    idInput.setText(String.valueOf(rs.getInt("Eid")));
                    nameInput.setText(rs.getString("Name"));
                    phoneinput.setText(rs.getString("Phone"));
                    emailinput.setText(rs.getString("Email"));
                    typeinput.setText(rs.getString("Type"));

                } else {
                    error.setText("No Employee found with ID" + ID);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();

        showEmployeeDetail();
    }
    private ObservableList<EmployeeDetail> getEmployeeDetailList() {
        ObservableList<EmployeeDetail> employeelist = FXCollections.observableArrayList();
        String query = "SELECT Eid,Name,Phone,Email,Type FROM userTable WHERE Type='Employee'";
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                EmployeeDetail Empployee = new EmployeeDetail(rs.getInt("Eid"), rs.getString("Name"), rs.getString("Phone"), rs.getString("Email"),rs.getString("Type"));
                employeelist.add(Empployee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeelist;
        //in the above code  i used a function executeQuery which is defined  below
    }

    private void showEmployeeDetail() {
        ObservableList<EmployeeDetail> list = getEmployeeDetailList();
        ID_Col.setCellValueFactory(new PropertyValueFactory<>("Eid"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        phonecol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("Email"));
        typecol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        tablefordata.setItems(list);  //this sets the list in table view as like we have mapped the table in above code
    }


    //in this below code it takes one string query and list of string parameter and use for loop to set the placeholder value in string
    private void executeQuery(String query, List<String> parameters) {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            for (int i = 0; i < parameters.size(); i++) {
                ps.setString(i + 1, parameters.get(i));
            }
            ps.executeUpdate();
            showEmployeeDetail();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // in this code below we defined the function that takes a string input and parse into integer we use this function to validate the data
    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //in this code below we check if the id exist in the database or not to perform validation

    private boolean doesIdExist(String id) {
        String query = "SELECT COUNT(*) FROM userTable WHERE Eid = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Returns true if count is greater than 0, meaning ID exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // below code helps to validate data
    public void  validateID() {
        if (idInput.getText().isEmpty()){
            error.setText("Enter an ID");}
        else{
            if (!isInteger(idInput.getText())) {
                error.setText("Error: ID must be an integer");
            } else {
                if (!doesIdExist(idInput.getText())) {
                    error.setText("Error:ID does Not exist");

                } else {
                    error.setText("");
                }
            }
        }}

    // this is the function that we use to make the input filed empty after each action.
    private void makeEmpty() {
        idInput.setText("");
        nameInput.setText("");
        phoneinput.setText("");
        emailinput.setText("");
        typeinput.setText("");

    }

    public void backtothedashboard(ActionEvent actionEvent) {
        try {
            // Loading the second view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent root = loader.load();


            Stage stage = (Stage) error.getScene().getWindow();
            //sending username to another controller;
            //sending username to another controller
            Dashboard_Controller secondController  = loader.getController();

            secondController.setUsername(usertodiplay);
            secondController.displayUsername();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();


        }
    }

    public void logoutogtheemployee(ActionEvent actionEvent) {
        try {
            // Loading the second view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login_Page.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) error.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }


    }
}