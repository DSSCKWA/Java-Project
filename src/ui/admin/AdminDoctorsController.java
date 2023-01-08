package src.ui.admin;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.clinic.Clinic;
import src.ui.Session;
import src.users.Doctor;

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
    private Button btnCancel;
    @FXML
    private TableView<Clinic> tvClinic;
    @FXML
    private TableView<Doctor> tvDoctor;

    @FXML
    private TableColumn<Clinic, Void> tcAR;

    @FXML
    private TableColumn<Doctor, Void> tcAdd;

    @FXML
    private TableColumn<?, ?> tcCAddress;

    @FXML
    private TableColumn<?, ?> tcCCity;

    @FXML
    private TableColumn<?, ?> tcCName;

    @FXML
    private TableColumn<?, ?> tcEmail;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TableColumn<?, ?> placeholder;

    @FXML
    private TableColumn<Doctor, Void> tcRemove;

    @FXML
    private TableColumn<?, ?> tcSurname;
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
        btnCancel.setVisible(false);
        placeholder.setVisible(false);
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        ArrayList<Doctor> doctors = new ArrayList<Doctor>();
        try {
            doctors = Session.getClient().getDoctors();
        } catch (Exception e) {
            System.out.println("Error");
        }
        int j = 0;
        tvDoctor.getItems().addAll(doctors);

        FilteredList<Doctor> filteredDoctors = new FilteredList<>(tvDoctor.getItems(), b -> true);

        tfSurname.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDoctors.setPredicate(doctor -> {
                if (newValue.isEmpty() || newValue.isBlank()) return doctor.getName().contains(tfName.getText());
                return doctor.getSurname().contains(newValue) && doctor.getName().contains(tfName.getText());
            });
            SortedList<Doctor> sortedDoctors = new SortedList<>(filteredDoctors);
            sortedDoctors.comparatorProperty().bind(tvDoctor.comparatorProperty());
            tvDoctor.setItems(sortedDoctors);

        });


        tfName.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDoctors.setPredicate(doctor -> {
                if (newValue.isEmpty() || newValue.isBlank()) return doctor.getSurname().contains(tfSurname.getText());
                return doctor.getName().contains(newValue) && doctor.getSurname().contains(tfSurname.getText());
            });
            SortedList<Doctor> sortedDoctors = new SortedList<>(filteredDoctors);
            sortedDoctors.comparatorProperty().bind(tvDoctor.comparatorProperty());
            tvDoctor.setItems(sortedDoctors);
        });

        tcAdd.setCellFactory(tableColumn -> new TableCell<>() {
            private final Button addButton = new Button("Add");

            {
                addButton.setOnAction((ActionEvent event) -> {
                    Doctor doctor = getTableView().getItems().get(getIndex());

                    placeholder.setVisible(true);
                    tcAdd.setVisible(false);
                    tcRemove.setVisible(false);
                    btnCancel.setVisible(true);

                    filteredDoctors.setPredicate(doctor2 -> doctor2.equals(doctor));
                    tvDoctor.setItems(filteredDoctors);


                    tcCAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
                    tcCCity.setCellValueFactory(new PropertyValueFactory<>("city"));
                    tcCName.setCellValueFactory(new PropertyValueFactory<>("name"));

                    ArrayList<Clinic> clinics = new ArrayList<Clinic>();
                    try {
                        ArrayList<Clinic> rnd = doctor.getDoctorClinics();
                        clinics = Session.getClient().getClinics();
                        clinics.removeAll(rnd);
                    } catch (Exception e) {
                        System.out.println("Error");
                    }
                    int j = 0;
                    tvClinic.getItems().addAll(clinics);

                    FilteredList<Clinic> filteredClinics = new FilteredList<>(tvClinic.getItems(), b -> true);
                    addButton.setVisible(false);

                    tcAR.setCellFactory(tableColumn -> new TableCell<>() {
                        private final Button addCButton = new Button("Add");

                        {
                            addCButton.setOnAction((ActionEvent event) -> {
                                Clinic clinic = getTableView().getItems().get(getIndex());

                                filteredClinics.setPredicate(clinic2 -> clinic2.equals(clinic));
                                tvClinic.setItems(filteredClinics);

                                try {
                                    Session.getClient().addDoctorToClinic(doctor.getId(), clinic.getClinicId());
                                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminDoctors.fxml")));
                                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    stage.setResizable(false);
                                    scene = new Scene(root);
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (IOException | InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }

                        @Override
                        public void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(addCButton);
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
                    setGraphic(addButton);
                }
            }

        });


        tcRemove.setCellFactory(tableColumn -> new TableCell<>() {
            private final Button removeButton = new Button("Remove");

            {
                removeButton.setOnAction((ActionEvent event) -> {
                    placeholder.setVisible(true);
                    removeButton.setVisible(false);
                    tcAdd.setVisible(false);
                    tcRemove.setVisible(false);
                    btnCancel.setVisible(true);
                    Doctor doctor = getTableView().getItems().get(getIndex());

                    filteredDoctors.setPredicate(doctor2 -> doctor2.equals(doctor));
                    tvDoctor.setItems(filteredDoctors);


                    tcCAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
                    tcCCity.setCellValueFactory(new PropertyValueFactory<>("city"));
                    tcCName.setCellValueFactory(new PropertyValueFactory<>("name"));

                    ArrayList<Clinic> clinics = new ArrayList<Clinic>();
                    try {
                        clinics = doctor.getDoctorClinics();
                    } catch (Exception e) {
                        System.out.println("Error");
                    }
                    int j = 0;
                    tvClinic.getItems().addAll(clinics);

                    FilteredList<Clinic> filteredClinics = new FilteredList<>(tvClinic.getItems(), b -> true);


                    tcAR.setCellFactory(tableColumn -> new TableCell<>() {
                        private final Button removeCButton = new Button("Remove");

                        {
                            removeCButton.setOnAction((ActionEvent event) -> {
                                Clinic clinic = getTableView().getItems().get(getIndex());

                                filteredClinics.setPredicate(clinic2 -> clinic2.equals(clinic));
                                tvClinic.setItems(filteredClinics);

                                try {
                                    Session.getClient().removeDoctorFromClinic(doctor.getId(), clinic.getClinicId());
                                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminDoctors.fxml")));
                                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    stage.setResizable(false);
                                    scene = new Scene(root);
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (IOException | InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }

                        @Override
                        public void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(removeCButton);
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
                    setGraphic(removeButton);
                }
            }

        });
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
    void btnCancelClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminDoctors.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
