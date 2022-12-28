package src.ui.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Text email;

    private static final double BUTTONS_PER_LINE = 8;
    private static final double NUM_BUTTON_LINES = 8;
    private static final double BUTTON_PADDING   = 5;

    @FXML
    private Text permission;

    @FXML
    void btnClinicsClicked(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("adminClinics.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnDoctorsClicked(ActionEvent event) {

    }

    @FXML
    void btnLogOutClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnStartClicked(ActionEvent event) {

    }

    @FXML
    void btnUsersClicked(ActionEvent event) {

    }

}

