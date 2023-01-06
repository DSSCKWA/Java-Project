package src.ui.moderator;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.clinic.Clinic;
import src.ui.Singleton;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModeratorClinicsController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private AnchorPane anchorPane2;

    @FXML
    private Button btnCreate;

    @FXML
    private Text tFaliure;

    @FXML
    private Text textAddress;

    @FXML
    private Text textCity;

    @FXML
    private Text textName;

    @FXML
    private TableColumn<?, ?> tcAddress;

    @FXML
    private TableColumn<?, ?> tcCity;

    @FXML
    private TableColumn<Clinic, Void> tcDelete;

    @FXML
    private TableColumn<Clinic, Void> tcEdit;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TableView<Clinic> tvTable;

    @FXML
    private Text textTitle;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfCity;

    @FXML
    private TextField tfName;

    public void initialize(URL location, ResourceBundle resources) {

        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        ArrayList<Clinic> clinics = new ArrayList<Clinic>();
        try {
            clinics = Singleton.getClient().getClinics();
        } catch (Exception e) {
            System.out.println("Error");
        }

        tvTable.getItems().addAll(clinics);

        FilteredList<Clinic> filteredClinics = new FilteredList<>(tvTable.getItems(), b -> true);

        tcEdit.setCellFactory(tableColumn -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction((ActionEvent event) -> {
                    Clinic clinic = getTableView().getItems().get(getIndex());

                    textTitle.setText("Clinic edition tool");
                    btnCreate.setText("Edit");

                    filteredClinics.setPredicate(clinic2 -> clinic2.equals(clinic));
                    tvTable.setItems(filteredClinics);


                    ///TODO: take this into outside actionListener
                    btnCreate.setOnAction((ActionEvent event1) -> {
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
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }

        });


        tcDelete.setCellFactory(tableColumn -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                tFaliure.setVisible(false);

                deleteButton.setOnAction((ActionEvent event) -> {
                    Clinic clinic = getTableView().getItems().get(getIndex());

                    textTitle.setText("Confirm Delete operation");
                    textAddress.setVisible(false);
                    textCity.setVisible(false);
                    textName.setVisible(false);
                    tfName.setVisible(false);
                    tfCity.setVisible(false);
                    tfAddress.setVisible(false);
                    btnCreate.setText("Confirm");

                    filteredClinics.setPredicate(clinic2 -> clinic2.equals(clinic));
                    tvTable.setItems(filteredClinics);

                    btnCreate.setOnAction((ActionEvent event1) -> {
                        tFaliure.setVisible(true);
                        if (!Objects.equals(tfName.getText(), "") && !Objects.equals(tfAddress.getText(), "") && !Objects.equals(tfCity.getText(), "")) {
                            try {
                                Singleton.getClient().deleteClinic(clinic.getClinicId());
                                System.out.println(clinic);
                                System.out.println(clinic.present());
                                tFaliure.setText("Success");
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
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }

        });
    }

    @FXML
    void btnClinicsClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("moderatorClinics.fxml")));
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
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("moderatorClinics.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setResizable(false);
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception ignored) {
            }
        }

    }

    @FXML
    void btnDoctorsClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("moderatorDoctors.fxml")));
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
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("moderator.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnExpertiseClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("moderatorExpertise.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
