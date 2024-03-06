package com.example.lab3bhanudahal;
import com.example.lab3bhanudahal.Model.SalaryDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class SalaryController implements Initializable {


    public Label showuser;
    @FXML
    private TableColumn<SalaryDetails, String> nameCol;

    @FXML
    private TableColumn<SalaryDetails, String> subjectcol;

    @FXML
    private TableColumn<SalaryDetails, Integer> ID_Col;

    @FXML
    private TextField nameInput, subjectinput, idInput, scoreinput;

    @FXML
    private Label error;

    @FXML
    private TableColumn<SalaryDetails, Integer> scorecol;

    @FXML
    private TableView<SalaryDetails> tablefordata;

    String usertodiplay;

    public void setUsername(String usertodiplay) {

        this.usertodiplay = usertodiplay;
    }
    public void displayUsername() {
        showuser.setText("welcome "+usertodiplay);
    }


    @FXML     // This adds the data in the table we only provide name,subject and score
    void addData(ActionEvent event) {
        String query = "INSERT INTO Student_grades (student_name, subject, score) VALUES (?, ?, ?)";
        executeQuery(query, List.of(nameInput.getText(), subjectinput.getText(), scoreinput.getText()));
        makeEmpty();

    }


    @FXML
    void deleteData(ActionEvent event) {
        validateID(); // this function validate the id if it is valid id or not
        if (error.getText().isEmpty()){
            String query = "DELETE FROM Student_grades WHERE grade_id=?";
            executeQuery(query, List.of(idInput.getText()));
            makeEmpty();}
    }

    @FXML
    void updateData(ActionEvent event) {
        validateID();
        if (error.getText().isEmpty()){
            String query = "UPDATE Student_grades SET student_name=?, subject=?, score=? WHERE grade_id=?";
            executeQuery(query, List.of(nameInput.getText(), subjectinput.getText(), scoreinput.getText(), idInput.getText()));
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
            String query = "SELECT grade_id, student_name, subject, score FROM Student_grades WHERE grade_id=?";
            int ID = Integer.parseInt(idInput.getText());

            try (Connection conn = Database.getConnection();
                 PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setInt(1, ID);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    error.setText(""); // this remove the error when user input the id that exist in the database
                    idInput.setText(String.valueOf(rs.getInt("grade_id")));
                    nameInput.setText(rs.getString("student_name"));
                    subjectinput.setText(rs.getString("subject"));
                    scoreinput.setText(String.valueOf(rs.getInt("score")));
                } else {
                    error.setText("No student found with ID" + ID);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    private ObservableList<SalaryDetails> getSalaryDetailsList() {
        ObservableList<SalaryDetails> studentgradesList = FXCollections.observableArrayList();
        String query = "SELECT * FROM Student_grades";
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                SalaryDetails student = new SalaryDetails(rs.getInt("grade_id"), rs.getString("student_name"), rs.getString("subject"), rs.getInt("score"));
                studentgradesList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentgradesList;
        //in the above code  i used a function executeQuery which is defined  below
    }

    private void showSalaryDetails() {
        ObservableList<SalaryDetails> list = getSalaryDetailsList();   // this is the function we defined in line no 75
        ID_Col.setCellValueFactory(new PropertyValueFactory<>("grade_id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("student_name"));
        scorecol.setCellValueFactory(new PropertyValueFactory<>("score"));
        subjectcol.setCellValueFactory(new PropertyValueFactory<>("subject"));
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
        String query = "SELECT COUNT(*) FROM Student_grades WHERE grade_id = ?";
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
        nameInput.setText("");
        subjectinput.setText("");
        idInput.setText("");
        scoreinput.setText("");

    }
}