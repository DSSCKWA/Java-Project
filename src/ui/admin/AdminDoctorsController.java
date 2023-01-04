package src.ui.admin;

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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.clinic.Clinic;
import src.ui.Singleton;
import src.users.Doctor;
import src.users.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminDoctorsController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Doctor user;
    @FXML
    private AnchorPane anchorPane1;

    @FXML
    private Button btnFind;

    @FXML
    private Text textTitle;

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
        ///TODO:unlock after adding method httpClient.getDoctors() or httpClient.getUsersByPermission(Permission permission)
        ///TODO: but first finnish this, cuz it aint even halfway there bruv
        ArrayList<Doctor> doctors = new ArrayList<Doctor>();
        try {
            doctors = Singleton.getClient().getDoctors();
        }catch(Exception e){
            System.out.println("Error");
        }
        int j=0;
        for (Doctor usr : doctors ) {
            Button x = new Button();
            x.setPrefSize(vBox1.getPrefWidth(),40);
            x.setText(usr.present());
            System.out.println(usr.present());
            x.setStyle("-fx-background-color: transparent;"+"-fx-border-color: black;"+"-fx-border-width: 1;");
            vBox1.getChildren().add(x);

            Button y = new Button("Add to clinic");
            y.setPrefSize(vBox2.getPrefWidth(),40);
            y.setStyle("-fx-background-color: #A7E6EC;"+"-fx-border-color: black;"+"-fx-border-width: 1;"+"-fx-cursor: hand;");
            vBox2.getChildren().add(y);

            Button z = new Button("Remove form clinic");
            z.setPrefSize(vBox3.getPrefWidth(),40);
            z.setStyle("-fx-background-color: #F54465;"+"-fx-border-color: black;"+"-fx-border-width: 1;"+"-fx-cursor: hand;");
            vBox3.getChildren().add(z);

            y.setOnAction((ActionEvent)->{
                this.user=usr;
                vBox1.getChildren().clear();
                vBox2.getChildren().clear();
                vBox3.getChildren().clear();
                ArrayList<Clinic> clinics = user.getDoctorClinics();
                int g=0;
                for (Clinic cli : clinics ) {
                    Button x = new Button();
                    x.setPrefSize(vBox1.getPrefWidth(),40);
                    x.setText(cli.present());
                    System.out.println(cli.present());
                    x.setStyle("-fx-background-color: transparent;"+"-fx-border-color: black;"+"-fx-border-width: 1;");
                    vBox1.getChildren().add(x);

                    Button y = new Button("Edit");
                    y.setPrefSize(vBox2.getPrefWidth(),40);
                    y.setStyle("-fx-background-color: #A7E6EC;"+"-fx-border-color: black;"+"-fx-border-width: 1;"+"-fx-cursor: hand;");
                    vBox2.getChildren().add(y);

                    Button z = new Button("Delete");
                    z.setPrefSize(vBox3.getPrefWidth(),40);
                    z.setStyle("-fx-background-color: #F54465;"+"-fx-border-color: black;"+"-fx-border-width: 1;"+"-fx-cursor: hand;");
                    vBox3.getChildren().add(z);
                });
            });
            z.setOnAction((ActionEvent)->{
                this.user=user;
                vBox1.getChildren().clear();
                vBox2.getChildren().clear();
                vBox3.getChildren().clear();

                Button xDel = new Button();
                xDel.setPrefSize(vBox1.getPrefWidth(),40);
                xDel.setText(user.present());
                xDel.setStyle("-fx-background-color: transparent;"+"-fx-border-color: black;"+"-fx-border-width: 1;");
                vBox1.getChildren().add(xDel);

                Button yDel = new Button("Cancel");
                yDel.setPrefSize(vBox2.getPrefWidth(),40);
                yDel.setStyle("-fx-background-color: #A7E6EC;"+"-fx-border-color: black;"+"-fx-border-width: 1;"+"-fx-cursor: hand;");
                vBox2.getChildren().add(yDel);

                Button zDel = new Button("Delete");
                zDel.setPrefSize(vBox3.getPrefWidth(),40);
                zDel.setStyle("-fx-background-color: #F54465;"+"-fx-border-color: black;"+"-fx-border-width: 1;"+"-fx-cursor: hand;");
                vBox3.getChildren().add(zDel);

                anchorPane1.setPrefHeight(40);
                vBox1.setPrefHeight(40);
                vBox2.setPrefHeight(40);
                vBox3.setPrefHeight(40);

                yDel.setOnAction((ActionEvent event)->{
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminClinics.fxml")));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    stage.setResizable(false);
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                });

                zDel.setOnAction((ActionEvent event)->{
                    try {
                        ///TODO:Deleting a doctor with his clinic connections, visits and stuff
                        Singleton.getClient().deleteDoctor(clinic.getClinicId());
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminClinics.fxml")));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        stage.setResizable(false);
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e) {
                        ///TODO: maybe something
                    }
                });

            });
            j++;
        }

        anchorPane1.setPrefHeight(j*40);
        vBox1.setPrefHeight(j*40);
        vBox2.setPrefHeight(j*40);
        vBox3.setPrefHeight(j*40);

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

