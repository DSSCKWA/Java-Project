package src.ui.sign_up;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.ui.Session;
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
    private PasswordField pfPassword;

    @FXML
    private PasswordField pfPasswordConfirm;

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
        tWarning.setText("Something went wrong, please try again!");
        tWarning.setVisible(true);
        if (Objects.equals(pfPassword.getText(), pfPasswordConfirm.getText())) {
            if (!Objects.equals(pfPassword.getText(), "") && !(Objects.equals(tfName.getText(), "")) && !(Objects.equals(tfSurname.getText(), "")) && !(Objects.equals(tfEmail.getText(), "")) && !(Objects.equals(tfCity.getText(), "")) && !(Objects.equals(tfAddress.getText(), "")) && !(Objects.equals(tfPhoneNumber.getText(), ""))) {
                try {
                    Session.getClient().addUser(new User(tfName.getText(), tfSurname.getText(), tfEmail.getText(), pfPassword.getText(), tfAddress.getText(), tfCity.getText(), Integer.parseInt(tfPhoneNumber.getText()), Permissions.PATIENT));

                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../login/login.fxml")));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setResizable(false);
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                    Stage popup = new Stage();
                    popup.getIcons().add(new Image("file:src/ui/logo/logo.png"));
                    popup.initModality(Modality.APPLICATION_MODAL);
                    popup.setTitle("Account created!");
                    Label popupLabel = new Label("Your account has been created successfully.");
                    Button popupButton = new Button("OK");
                    popupButton.setOnAction(e -> popup.close());
                    VBox layout = new VBox(10);
                    layout.getChildren().addAll(popupLabel, popupButton);
                    layout.setAlignment(Pos.CENTER);
                    Scene pop = new Scene(layout, 300, 150);
                    pop.getStylesheets().add(getClass().getResource("../css/popupStyling.css").toExternalForm());
                    popup.setScene(pop);
                    popup.showAndWait();


                } catch (Exception ignored) {
                }

            }

        } else {
            tWarning.setText("Passwords do not match!");
        }

    }


    @FXML
    void btnCancelClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../login/login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
