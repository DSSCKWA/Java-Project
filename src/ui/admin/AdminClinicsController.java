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

        import java.io.IOException;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.Objects;
        import java.util.ResourceBundle;

public class AdminClinicsController  implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfCity;

    @FXML
    private Button btnCreate;

    @FXML
    private Text textTitle;

    @FXML
    private VBox vBox1;

    @FXML
    private VBox vBox2;

    @FXML
    private VBox vBox3;

    @FXML
    private AnchorPane anchorPane1;

    @FXML
    public void initialize(URL location, ResourceBundle resources){

        ///TODO: client.getAllClinics()
        int j=0;
        for(int i=0;i<20;i++)
        {
            Button x = new Button();
            x.setPrefSize(vBox1.getPrefWidth(),40);
            x.setText("Button"+(i+1));
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

            y.setOnAction((ActionEvent)->{
                vBox1.getChildren().clear();
                vBox2.getChildren().clear();
                vBox3.getChildren().clear();
                textTitle.setText("Clinic edition tool");
                btnCreate.setText("Edit");
                ///TODO: action event clinic data edition
//                @FXML
//                void btnCreateClicked(ActionEvent event) {
//
//                }

            });
            z.setOnAction((ActionEvent)->{
                vBox1.getChildren().clear();
                vBox2.getChildren().clear();
                vBox3.getChildren().clear();

                Button xDel = new Button();
                xDel.setPrefSize(vBox1.getPrefWidth(),40);
                xDel.setText("Button");
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
                    ///TODO: delete Clinic by server request
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
    void btnDoctorsClicked(ActionEvent event) {

    }
    @FXML
    void btnLogOutClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../login/login.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnStartClicked(ActionEvent event) throws Exception{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnUsersClicked(ActionEvent event) {

    }

    @FXML
    void btnCreateClicked(ActionEvent event) {

    }

}


