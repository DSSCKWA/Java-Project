package src.ui.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.clinic.Clinic;
import src.ui.Singleton;
import src.users.Permissions;
import src.users.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminClinicsController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Clinic clinic;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfCity;
    @FXML
    private TextField tfName;
    @FXML
    private Button btnCreate;

    @FXML
    private Text textTitle;

    @FXML
    private Text tFaliure;

    @FXML
    private VBox vBox1;

    @FXML
    private VBox vBox2;

    @FXML
    private VBox vBox3;

    @FXML
    private AnchorPane anchorPane1;
    @FXML
    private AnchorPane anchorPane2;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        tFaliure.setVisible(false);
        ArrayList<Clinic> clinics = new ArrayList<Clinic>();
        try {
            clinics = Singleton.getClient().getClinics();
        } catch (Exception e) {
            System.out.println("Error");
        }
        int j = 0;
        for (Clinic cli : clinics) {
            Button x = new Button();
            x.setPrefSize(vBox1.getPrefWidth(), 40);
            x.setText(cli.present());
            System.out.println(cli.present());
            x.setStyle("-fx-background-color: transparent;" + "-fx-border-color: black;" + "-fx-border-width: 1;");
            vBox1.getChildren().add(x);

            Button y = new Button("Edit");
            y.setPrefSize(vBox2.getPrefWidth(), 40);
            y.setStyle("-fx-background-color: #A7E6EC;" + "-fx-border-color: black;" + "-fx-border-width: 1;" + "-fx-cursor: hand;");
            vBox2.getChildren().add(y);

            Button z = new Button("Delete");
            z.setPrefSize(vBox3.getPrefWidth(), 40);
            z.setStyle("-fx-background-color: #F54465;" + "-fx-border-color: black;" + "-fx-border-width: 1;" + "-fx-cursor: hand;");
            vBox3.getChildren().add(z);

            y.setOnAction((ActionEvent) -> {
                this.clinic = cli;
                vBox1.getChildren().clear();
                vBox2.getChildren().clear();
                vBox3.getChildren().clear();
                textTitle.setText("Clinic edition tool");

                Button xDel = new Button();
                xDel.setPrefSize(vBox1.getPrefWidth(), 40);
                xDel.setText(clinic.present());
                xDel.setStyle("-fx-background-color: transparent;" + "-fx-border-color: black;" + "-fx-border-width: 1;");
                vBox1.getChildren().add(xDel);

                btnCreate.setText("Edit");
                btnCreate.setOnAction((ActionEvent event) -> {
                    tFaliure.setVisible(true);
                    if (!Objects.equals(tfName.getText(), "") && !Objects.equals(tfAddress.getText(), "") && !Objects.equals(tfCity.getText(), "")) {
                        try {
                            clinic.setName(tfName.getText());
                            clinic.setCity(tfCity.getText());
                            clinic.setAddress(tfAddress.getText());
                            Singleton.getClient().updateClinic(clinic);
                            System.out.println(clinic);
                            System.out.println(clinic.present());
                            tFaliure.setVisible(false);
                            Text tSuccess = new Text(600, 114, "Success");
                            anchorPane2.getChildren().add(tSuccess);
                            wait(800);
                            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminClinics.fxml")));
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setResizable(false);
                            scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        } catch (Exception e) {
                            System.out.println("Error");
                        }
                    }
                });
            });
            z.setOnAction((ActionEvent) -> {
                this.clinic = cli;
                vBox1.getChildren().clear();
                vBox2.getChildren().clear();
                vBox3.getChildren().clear();

                Button xDel = new Button();
                xDel.setPrefSize(vBox1.getPrefWidth(), 40);
                xDel.setText(clinic.present());
                xDel.setStyle("-fx-background-color: transparent;" + "-fx-border-color: black;" + "-fx-border-width: 1;");
                vBox1.getChildren().add(xDel);

                Button yDel = new Button("Cancel");
                yDel.setPrefSize(vBox2.getPrefWidth(), 40);
                yDel.setStyle("-fx-background-color: #A7E6EC;" + "-fx-border-color: black;" + "-fx-border-width: 1;" + "-fx-cursor: hand;");
                vBox2.getChildren().add(yDel);

                Button zDel = new Button("Delete");
                zDel.setPrefSize(vBox3.getPrefWidth(), 40);
                zDel.setStyle("-fx-background-color: #F54465;" + "-fx-border-color: black;" + "-fx-border-width: 1;" + "-fx-cursor: hand;");
                vBox3.getChildren().add(zDel);

                anchorPane1.setPrefHeight(40);
                vBox1.setPrefHeight(40);
                vBox2.setPrefHeight(40);
                vBox3.setPrefHeight(40);

                yDel.setOnAction((ActionEvent event) -> {
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminClinics.fxml")));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setResizable(false);
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                });

                zDel.setOnAction((ActionEvent event) -> {
                    try {
                        Singleton.getClient().deleteClinic(clinic.getClinicId());
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminClinics.fxml")));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

        anchorPane1.setPrefHeight(j * 40);
        vBox1.setPrefHeight(j * 40);
        vBox2.setPrefHeight(j * 40);
        vBox3.setPrefHeight(j * 40);
    }

    @FXML
    void btnClinicsClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminClinics.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnDoctorsClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminDoctors.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnLogOutClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../login/login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnStartClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnUsersClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminUsers.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnCreateClicked(ActionEvent event) {

        tFaliure.setVisible(true);
        if (!Objects.equals(tfName.getText(), "") && !Objects.equals(tfAddress.getText(), "") && !Objects.equals(tfCity.getText(), "")) {
            try {
                Singleton.getClient().addClinic(new Clinic(tfName.getText(), tfAddress.getText(), tfCity.getText()));
                tFaliure.setVisible(false);
                Text tSuccess = new Text(600, 114, "Success");
                anchorPane2.getChildren().add(tSuccess);
                wait(800);
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminClinics.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setResizable(false);
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
            }
        }

    }

}


