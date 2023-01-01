package src.ui.guest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.clinic.Clinic;
import src.ui.Singleton;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class GuestClinicsController  implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField tfName;

    @FXML
    private TextField tfCity;

    @FXML
    private Text textTitle;

    @FXML
    private VBox vBox1;

    @FXML
    private AnchorPane anchorPane1;

    @FXML
    public void initialize(URL location, ResourceBundle resources){

        ArrayList<Clinic> clinics = new ArrayList<Clinic>();
        try {
            clinics = Singleton.getClient().getClinics();
        }catch(Exception e){
            System.out.println("Error");
        }
        int j=0;
        for (Clinic cli : clinics ) {
            Button x = new Button();
            x.setPrefSize(vBox1.getPrefWidth(),40);
            x.setText(cli.present());
            x.setStyle("-fx-background-color: transparent;"+"-fx-border-color: black;"+"-fx-border-width: 1;");
            vBox1.getChildren().add(x);
            j++;
        }
        anchorPane1.setPrefHeight(j*40);
        vBox1.setPrefHeight(j*40);
    }

    @FXML
    void btnClinicsClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("guestClinics.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnDoctorsClicked(ActionEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("guestDoctors.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void btnLogInClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../login/login.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnStartClicked(ActionEvent event) throws Exception{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patient.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnSearchClicked(ActionEvent event) {
        vBox1.getChildren().clear();

        ///TODO: unlock after adding method httpClient.getClinics(String name,String city)
//        ArrayList<Clinic> clinics = new ArrayList<Clinic>();
//        try {
//            clinics = Singleton.getClient().getClinics(tfName.getText(),tfCity.getText());
//        }catch(Exception e){
//            System.out.println("Error");
//        }
//        int j=0;
//        for (Clinic cli : clinics ) {
//            Button x = new Button();
//            x.setPrefSize(vBox1.getPrefWidth(),40);
//            x.setText(cli.present());
//            x.setStyle("-fx-background-color: transparent;"+"-fx-border-color: black;"+"-fx-border-width: 1;");
//            vBox1.getChildren().add(x);
//            j++;
//        }
//        anchorPane1.setPrefHeight(j*40);
//        vBox1.setPrefHeight(j*40);
    }

}




