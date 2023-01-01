package src.ui.sign_up;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.ui.Singleton;
import src.users.Patient;
import src.users.Permissions;
import src.users.User;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private AnchorPane AnchorPane1;
    @FXML
    private Text tWarning;
    @FXML
    private Button btnSubmit;
    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfCity;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfPasswordConfirm;

    @FXML
    private TextField tfPhoneNumber;

    @FXML
    private TextField tfSurname;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tWarning.setVisible(false);
    }
    @FXML
    void btnSubmitClicked(ActionEvent event) {
        tWarning.setVisible(true);
        if(Objects.equals(tfPassword.getText(), tfPasswordConfirm.getText()) && !Objects.equals(tfPassword.getText(), ""))
        {
            try{
                Singleton.getClient().addUser(new User(tfName.getText(),tfSurname.getText(),tfEmail.getText(),tfPassword.getText(),tfAddress.getText(),tfCity.getText(),Integer.parseInt(tfPhoneNumber.getText()), Permissions.PATIENT));
                AnchorPane1.getChildren().clear();
                Text tSuccess = new Text(50,180,"Success");
                AnchorPane1.getChildren().add(tSuccess);
                btnSubmit.setText("Confirm");
                Button btnConfirm = btnSubmit;
                AnchorPane1.getChildren().add(btnConfirm);

                btnConfirm.setOnAction((ActionEvent event1)->{
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../login/login.fxml")));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage = (Stage)((Node)event1.getSource()).getScene().getWindow();
                    stage.setResizable(false);
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                });
            }catch(Exception e)
            {
                ///TODO: dunno what, but maybe something
            }

        }

    }


}
