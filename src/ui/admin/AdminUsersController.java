package src.ui.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.clinic.Clinic;
import src.ui.Singleton;
import src.users.Permissions;
import src.users.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminUsersController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private User user;
    @FXML
    private AnchorPane anchorPane1;

    @FXML
    private Button btnFind;

    @FXML
    private Text textTitle;

    @FXML
    private Text tFaliure;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfSurname;

    @FXML
    private VBox vBox1;

    @FXML
    private VBox vBox2;

    @FXML
    private VBox vBox3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tFaliure.setVisible(false);
        ArrayList<User> users = new ArrayList<User>();
        try {
            users = Singleton.getClient().getUsers();
        }catch(Exception e){
            System.out.println("Error");
        }
        int j=0;
        for (User usr : users ) {
            Button x = new Button();
            x.setPrefSize(vBox1.getPrefWidth(),40);
            x.setText(usr.present());
            System.out.println(usr.present());
            x.setStyle("-fx-background-color: transparent;"+"-fx-border-color: black;"+"-fx-border-width: 1;");
            vBox1.getChildren().add(x);

            Button y = new Button("Change Permission");
            y.setPrefSize(vBox2.getPrefWidth(),40);
            y.setStyle("-fx-background-color: #A7E6EC;"+"-fx-border-color: black;"+"-fx-border-width: 1;"+"-fx-cursor: hand;");
            vBox2.getChildren().add(y);

            y.setOnAction((ActionEvent)->{
                this.user=usr;
                vBox1.getChildren().clear();
                vBox2.getChildren().clear();

                Button xDel = new Button();
                xDel.setPrefSize(vBox1.getPrefWidth(),40);
                xDel.setText(user.present());
                xDel.setStyle("-fx-background-color: transparent;"+"-fx-border-color: black;"+"-fx-border-width: 1;");
                vBox1.getChildren().add(xDel);

                ObservableList<String> options =
                        FXCollections.observableArrayList(
                                "Doctor",
                                "Moderator",
                                "Patient"
                        );
                ComboBox comboBox = new ComboBox(options);
                comboBox.setPrefSize(vBox2.getPrefWidth(),40);
                comboBox.setStyle("-fx-background-color: transparent;"+"-fx-border-color: black;"+"-fx-border-width: 1;");
                vBox2.getChildren().add(comboBox);

                Button newDel = new Button();
                newDel.setPrefSize(vBox2.getPrefWidth(),40);
                newDel.setText("Confirm");
                newDel.setStyle("-fx-background-color: transparent;"+"-fx-border-color: black;"+"-fx-cursor: hand;"+"-fx-border-width: 1;");
                vBox2.getChildren().add(newDel);

                newDel.setOnAction((ActionEvent event)-> {
                    tFaliure.setVisible(true);
                        try {
                            user.setPermissions(Permissions.valueOf(comboBox.getValue().toString().toUpperCase(Locale.ROOT)));
                            System.out.println(user.getPermissions());
                            Singleton.getClient().updateUser(user);
                            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminUsers.fxml")));
                            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                            stage.setResizable(false);
                            scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        } catch (Exception e) {
                            System.out.println("Error");
                        }
                });
            });
            j++;
        }
    }

    @FXML
    void btnClinicsClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminClinics.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnDoctorsClicked(ActionEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminDoctors.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnLogOutClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../login/login.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnStartClicked(ActionEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnUsersClicked(ActionEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminUsers.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnFindClicked(ActionEvent event) {

    }


}

