package src.ui.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import src.http.service.UserService;
import src.ui.MainUI;
import src.users.User;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfPassword;

    @FXML
    public void btnConfirmClicked(ActionEvent event) throws IOException {
        Stage mainWindow = (Stage) tfEmail.getScene().getWindow();
        String email = tfEmail.getText();

        mainWindow = (Stage) tfPassword.getScene().getWindow();
        String password = tfPassword.getText();

        System.out.println(email+":"+password);

        Parent root;
        Stage stage;
        Scene scene;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../patient/patient.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


        UserService userService=new UserService(); ///TODO: delete


        if(userService.getUserByEmail(email)!=null){ ///TODO: client.getUserByEmail(email)
            User user = userService.getUserByEmail(email);  ///TODO: client.getUserByEmail(email)
            if(Objects.equals(user.getPassword(), password)) {
                switch (user.getPermissions()) {
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
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        break;
                    default:
                        break;
                }
            }
        }else{

            ///TODO: Error info
        }

    }

    @FXML
    void btnSignUpClicked(ActionEvent event) {

    }

}

