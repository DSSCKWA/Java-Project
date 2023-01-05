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
    private Text tDoctorData;

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
            ///TODO: uncomment when added
            //doctors = Singleton.getClient().getDoctors();
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

            Button z = new Button("Remove from clinic");
            z.setPrefSize(vBox3.getPrefWidth(),40);
            z.setStyle("-fx-background-color: #F54465;"+"-fx-border-color: black;"+"-fx-border-width: 1;"+"-fx-cursor: hand;");
            vBox3.getChildren().add(z);

            y.setOnAction((ActionEvent)->{
                this.user=usr;
                tDoctorData.setText("Clinic Data");
                vBox1.getChildren().clear();
                vBox2.getChildren().clear();
                vBox3.getChildren().clear();
                ArrayList<Clinic> clinics = null;
                try {
                    clinics = Singleton.getClient().getClinics();
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                ArrayList<Clinic> clinics1 = user.getDoctorClinics();
                clinics.removeAll(clinics1);

                int g=0;
                for (Clinic cli : clinics ) {
                    Button x1 = new Button();
                    x1.setPrefSize(vBox1.getPrefWidth(), 40);
                    x1.setText(cli.present());
                    System.out.println(cli.present());
                    x1.setStyle("-fx-background-color: transparent;" + "-fx-border-color: black;" + "-fx-border-width: 1;");
                    vBox1.getChildren().add(x);

                    Button y1 = new Button("");
                    y1.setPrefSize(vBox2.getPrefWidth(), 40);
                    y1.setStyle("-fx-background-color: transparent;" + "-fx-border-color: black;" + "-fx-border-width: 1;");
                    vBox2.getChildren().add(y);

                    Button z1 = new Button("Add");
                    z1.setPrefSize(vBox3.getPrefWidth(), 40);
                    z1.setStyle("-fx-background-color: #F54465;" + "-fx-border-color: black;" + "-fx-border-width: 1;" + "-fx-cursor: hand;");
                    vBox3.getChildren().add(z);
                    g++;

                    z1.setOnAction((ActionEvent event1)->{
                        ///TODO: https request to add new Doctor-Clinic connection
                    });
                }
                anchorPane1.setPrefHeight(g*40);
                vBox1.setPrefHeight(g*40);
                vBox2.setPrefHeight(g*40);
                vBox3.setPrefHeight(g*40);
            });

            z.setOnAction((ActionEvent)->{
                this.user=usr;
                tDoctorData.setText("Clinic Data");
                vBox1.getChildren().clear();
                vBox2.getChildren().clear();
                vBox3.getChildren().clear();

                ArrayList<Clinic> clinics = user.getDoctorClinics();

                int g=0;
                for (Clinic cli : clinics ) {
                    Button x1 = new Button();
                    x1.setPrefSize(vBox1.getPrefWidth(), 40);
                    x1.setText(cli.present());
                    System.out.println(cli.present());
                    x1.setStyle("-fx-background-color: transparent;" + "-fx-border-color: black;" + "-fx-border-width: 1;");
                    vBox1.getChildren().add(x);

                    Button y1 = new Button("");
                    y1.setPrefSize(vBox2.getPrefWidth(), 40);
                    y1.setStyle("-fx-background-color: transparent;" + "-fx-border-color: black;" + "-fx-border-width: 1;");
                    vBox2.getChildren().add(y);

                    Button z1 = new Button("Remove");
                    z1.setPrefSize(vBox3.getPrefWidth(), 40);
                    z1.setStyle("-fx-background-color: #F54465;" + "-fx-border-color: black;" + "-fx-border-width: 1;" + "-fx-cursor: hand;");
                    vBox3.getChildren().add(z);

                    z1.setOnAction((ActionEvent event1)->{
                        ///TODO: https request to remove Doctor-Clinic connection (only if possble)
                    });

                    g++;
                }
                anchorPane1.setPrefHeight(g*40);
                vBox1.setPrefHeight(g*40);
                vBox2.setPrefHeight(g*40);
                vBox3.setPrefHeight(g*40);
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

