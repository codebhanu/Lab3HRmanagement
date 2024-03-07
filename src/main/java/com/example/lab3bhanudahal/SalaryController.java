package com.example.lab3bhanudahal;
import com.example.lab3bhanudahal.Model.Database;
import com.example.lab3bhanudahal.Model.SalaryDetails;
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

public class SalaryController implements Initializable {
    @FXML
    private TableColumn<SalaryDetails, Integer> ID_Col;

    @FXML
    private TableColumn<SalaryDetails, String> Monthcol;

    @FXML
    private Button addbut;

    @FXML
    private Button backtodashboard;

    @FXML
    private Button deletebut;

    @FXML
    private TableColumn<SalaryDetails, Integer> eidcol;

    @FXML
    private TextField eidinput;

    @FXML
    private TableColumn<SalaryDetails, String> emailcol;

    @FXML
    private Label error;

    @FXML
    private TextField monthinput;

    @FXML
    private TableColumn<SalaryDetails, String> nameCol;

    @FXML
    private TableColumn<SalaryDetails, String> phonecol;

    @FXML
    private TableColumn<SalaryDetails, Integer> salarycol;

    @FXML
    private TextField salaryinput;

    @FXML
    private Label showuser;

    @FXML
    private TextField sidinput;

    @FXML
    private Button updatebut;



    @FXML
    private TableView<SalaryDetails> tablefordata;

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
        String query = "INSERT INTO Salary (Eid, Month, Salary) VALUES (?, ?, ?)";
        executeQuery(query, List.of(eidinput.getText(), monthinput.getText(), salaryinput.getText()));
        makeEmpty();

    }


    @FXML
    void deleteData(ActionEvent event) {
        validateID(); // this function validate the id if it is valid id or not
        if (error.getText().isEmpty()){
            String query = "DELETE FROM Salary WHERE sID=?";
            executeQuery(query, List.of(sidinput.getText()));
            makeEmpty();}
    }

    @FXML
    void updateData(ActionEvent event) {
        validateID();
        if (error.getText().isEmpty()){
            String query = "UPDATE Salary SET Salary=?, Month=? WHERE sID=?";
            executeQuery(query, List.of(salaryinput.getText(), monthinput.getText(),  sidinput.getText()));
            makeEmpty();} // this function makes the input filed clear after the action is finished
    }

    @FXML
    void getData(ActionEvent event) {
        error.setText("");
        showSalaryDetails(); //This displays the data in the table
    }




    @FXML
    public void viewData(ActionEvent actionEvent) {
        validateID();
        if (error.getText().isEmpty()){
            String query = "SELECT sID, Eid, Month, Salary FROM Salary WHERE sID=?";
            int ID = Integer.parseInt(sidinput.getText());

            try (Connection conn = Database.getConnection();
                 PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setInt(1, ID);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    error.setText(""); // this remove the error when user input the id that exist in the database
                    sidinput.setText(String.valueOf(rs.getInt("sID")));
                    eidinput.setText(String.valueOf(rs.getInt("Eid")));
                    monthinput.setText(rs.getString("Month"));
                    salaryinput.setText(String.valueOf(rs.getInt("Salary")));
                } else {
                    error.setText("No Salary found with ID" + ID);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();

        showSalaryDetails();
    }
    private ObservableList<SalaryDetails> getSalaryDetailsList() {
        ObservableList<SalaryDetails> studentgradesList = FXCollections.observableArrayList();
        String query = "SELECT s.sID, u.Name, u.Email, u.Phone, s.Salary, s.Month, u.Eid \n" +
                "FROM userTable u\n" +
                "JOIN Salary s ON u.Eid = s.eID\n" +
                "ORDER BY s.sID, u.Eid;";
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                SalaryDetails salary = new SalaryDetails(rs.getInt("sID"), rs.getString("Name"), rs.getString("Email"), rs.getString("Phone"),rs.getInt("Salary"),rs.getString("Month"),rs.getInt("Eid"));
                studentgradesList.add(salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentgradesList;
        //in the above code  i used a function executeQuery which is defined  below
    }

    private void showSalaryDetails() {
        ObservableList<SalaryDetails> list = getSalaryDetailsList();
        ID_Col.setCellValueFactory(new PropertyValueFactory<>("Sid"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("Email"));
        phonecol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        salarycol.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        Monthcol.setCellValueFactory(new PropertyValueFactory<>("Month"));
        eidcol.setCellValueFactory(new PropertyValueFactory<>("Eid"));
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
            showSalaryDetails();
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
        String query = "SELECT COUNT(*) FROM Salary WHERE sID = ?";
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
        if (sidinput.getText().isEmpty()){
            error.setText("Enter an ID");}
        else{
            if (!isInteger(sidinput.getText())) {
                error.setText("Error: ID must be an integer");
            } else {
                if (!doesIdExist(sidinput.getText())) {
                    error.setText("Error:ID does Not exist");

                } else {
                    error.setText("");
                }
            }
        }}

    // this is the function that we use to make the input filed empty after each action.
    private void makeEmpty() {
        sidinput.setText("");
        eidinput.setText("");
        monthinput.setText("");
        salaryinput.setText("");

    }

    public void gotodashborad(ActionEvent actionEvent) {
        try {
            // Loading the second view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent root = loader.load();


            Stage stage = (Stage) error.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            //sending username to another controller
            Dashboard_Controller secondController  = loader.getController();

            secondController.setUsername(usertodiplay);
            secondController.displayUsername();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();


        }
    }

    public void logoutfromsalary(ActionEvent actionEvent) {
            try {
                // Loading the second view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Login_Page.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) sidinput.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }
}