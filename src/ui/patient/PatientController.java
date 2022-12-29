package src.ui.patient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PatientController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Text email;

    @FXML
    private Text permission;

    @FXML
    void btnLogOutClicked(ActionEvent event) {

    }

    @FXML
    void btnNewVisitClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patientNewVisit.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnStartClicked(ActionEvent event) {

    }

    @FXML
    void btnYourVisitsClicked(ActionEvent event) {

    }

}
