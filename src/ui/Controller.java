package src.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfPassword;

    @FXML
    void btnConfirmClicked(ActionEvent event) {
        Stage mainWindow = (Stage) tfEmail.getScene().getWindow();
        String email = tfEmail.getText();

        mainWindow = (Stage) tfPassword.getScene().getWindow();
        String password = tfPassword.getText();

        System.out.println(email+":"+password);
    }

}

