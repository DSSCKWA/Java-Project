package src.ui.patient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import src.ui.Singleton;
import src.visit.Visit;

import java.io.IOException;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.Objects;
        import java.util.ResourceBundle;

public class PatientMyVisitsController  implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField tfExpertise;

    @FXML
    private TextField tfCity;

    @FXML
    private Text textTitle;
    @FXML
    private DatePicker tfDate;
    @FXML
    private VBox vBox1;

    private VBox vBox2;

    @FXML
    private VBox vBox3;

    @FXML
    private AnchorPane anchorPane1;

    @FXML
    public void initialize(URL location, ResourceBundle resources){

        ///TODO: client.getAllClinics()
        ArrayList<Visit> visits = new ArrayList<>();
        try {
            visits = Singleton.getClient().getVisits();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        int j=0;
        for(Visit vis : visits)
        {
            Button x = new Button();
            x.setPrefSize(vBox1.getPrefWidth(),40);
            x.setText(vis.present());
            x.setStyle("-fx-background-color: transparent;"+"-fx-border-color: black;"+"-fx-border-width: 1;");
            vBox1.getChildren().add(x);

            Button y = new Button(vis.getRating()+"/5");
            y.setPrefSize(vBox2.getPrefWidth(),40);
            y.setStyle("-fx-background-color: transparent;"+"-fx-border-color: black;"+"-fx-border-width: 1;");
            vBox2.getChildren().add(y);

            Button z = new Button("Rate");
            z.setPrefSize(vBox3.getPrefWidth(),40);
            z.setStyle("-fx-background-color: #F54465;"+"-fx-border-color: black;"+"-fx-border-width: 1;"+"-fx-cursor: hand;");
            vBox3.getChildren().add(z);

            z.setOnAction((ActionEvent)->{
                vBox1.getChildren().clear();
                vBox2.getChildren().clear();
                vBox3.getChildren().clear();

                Button xDel = new Button();
                xDel.setPrefSize(vBox1.getPrefWidth(),40);
                xDel.setText(vis.present());
                xDel.setStyle("-fx-background-color: transparent;"+"-fx-border-color: black;"+"-fx-border-width: 1;");
                vBox1.getChildren().add(xDel);

                ObservableList<String> options =
                        FXCollections.observableArrayList(
                                "1","2","3","4","5"
                        );
                ComboBox yDel = new ComboBox(options);
                yDel.setPrefSize(vBox2.getPrefWidth(),40);
                yDel.setStyle("-fx-background-color: #F54465;"+"-fx-border-color: black;"+"-fx-border-width: 1;"+"-fx-cursor: hand;");
                vBox2.getChildren().add(yDel);

                Button zDel = new Button("Confirm");
                zDel.setPrefSize(vBox3.getPrefWidth(),40);
                zDel.setStyle("-fx-background-color: #F54465;"+"-fx-border-color: black;"+"-fx-border-width: 1;"+"-fx-cursor: hand;");
                vBox3.getChildren().add(zDel);

                anchorPane1.setPrefHeight(40);
                vBox1.setPrefHeight(40);
                vBox2.setPrefHeight(40);
                vBox3.setPrefHeight(40);

                zDel.setOnAction((javafx.event.ActionEvent event)->{

                    vis.setRating((int) yDel.getValue());

                    try {
                        Singleton.getClient().updateVisit(vis);
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patientMyVisits.fxml")));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        stage.setResizable(false);
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

            });
            j++;
        }
        anchorPane1.setPrefHeight(j*40);
        vBox1.setPrefHeight(j*40);
        vBox1.setPrefHeight(j*40);
        vBox3.setPrefHeight(j*40);
    }

    @FXML
    void btnMyVisitsClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patientMyVisits.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnNewVisitClicked(ActionEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patientNewVisit.fxml")));
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
    void btnStartClicked(ActionEvent event) throws Exception{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patient.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}



