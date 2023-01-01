package src.ui.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import src.ui.Singleton;
import src.users.Permissions;
import src.users.User;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable{
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField pfPassword;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Singleton.getInstance(null);
    }

    @FXML
    public void btnConfirmClicked(ActionEvent event) throws IOException {
        Stage mainWindow = (Stage) tfEmail.getScene().getWindow();
        String email = tfEmail.getText();

        mainWindow = (Stage) pfPassword.getScene().getWindow();
        String password = pfPassword.getText();

        System.out.println(email+":"+password);

//        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../patient/patient.fxml")));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();

        User user = new User(0,null,null,null,null,null,null,0,null);
        try {
            user = Singleton.getClient().getUserByEmail(email);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            if (Objects.equals(user.getPassword(), password)) {
                Singleton.newUser(user);
                Permissions permission = user.getPermissions();
                System.out.println(user);
                switch (permission) {
                    case ADMIN:
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../admin/admin.fxml")));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setResizable(false);
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        break;
                    case DOCTOR:
                        break;
                    case MODERATOR:
                        break;
                    case GUEST:
                        break;
                    case PATIENT:
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../patient/patient.fxml")));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setResizable(false);
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        break;
                    default:
                        break;
                }

            } else {

                ///TODO: Error info
            }
        }

    }

    @FXML
    void btnSignUpClicked(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../sign_up/signUp.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnClickHereClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../guest/guest.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}

