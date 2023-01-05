package src.ui.patient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class PatientNewVisitController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableColumn<?, ?> tcCity;

    @FXML
    private TableColumn<?, ?> tcExpertise;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TableColumn<?, ?> tcSchedule;

    @FXML
    private TableColumn<?, ?> tcSurname;

    @FXML
    private TableColumn<?, ?> tcTime;
    @FXML
    private TableView<?> tvTable;
    @FXML
    private TextField tfExpertise;

    @FXML
    private TextField tfCity;

    @FXML
    private Text textTitle;
    @FXML
    private DatePicker tfDate;
    @FXML
    private VBox vBox1;

    @FXML
    private VBox vBox3;

    @FXML
    private AnchorPane anchorPane1;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {


    }

    @FXML
    void btnMyVisitsClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patientMyVisits.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnNewVisitClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patientNewVisit.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnLogOutClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../login/login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnStartClicked(ActionEvent event) throws Exception {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patient.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnSearchClicked(ActionEvent event) {
        vBox1.getChildren().clear();
        vBox3.getChildren().clear();

///TODO: search server query + modifying results
    }

}



