package src.ui.moderator;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.expertise.Expertise;
import src.ui.Session;
import src.users.Doctor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModeratorExpertiseController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Doctor doc;

    private Expertise exp;

    @FXML
    private TableColumn<ExpertiseRow, Void> tcAdd;

    @FXML
    private TableColumn<?, ?> tcEmail;

    @FXML
    private TableColumn<?, ?> tcExpertise;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TableColumn<ExpertiseRow, Void> tcRemove;

    @FXML
    private TableColumn<?, ?> tcSurname;

    @FXML
    private TableView<ExpertiseRow> tvExpertise;

    @FXML
    private Text textExpertise;

    @FXML
    private TextField tfExpertise;
    @FXML
    private TextField tfName;

    @FXML
    private TextField tfSurname;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    @FXML
    private Text textAdd;
    @FXML
    private Text textName;

    @FXML
    private Text textSearch;

    @FXML
    private Text textSurname;
    @FXML
    private Text email;

    private static final double BUTTONS_PER_LINE = 8;
    private static final double NUM_BUTTON_LINES = 8;
    private static final double BUTTON_PADDING = 5;

    @FXML
    private Text permission;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tfExpertise.setVisible(false);
        textExpertise.setVisible(false);
        btnAdd.setVisible(false);
        btnCancel.setVisible(false);
        textAdd.setVisible(false);

        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcExpertise.setCellValueFactory(new PropertyValueFactory<>("areaOfExpertise"));

        try {
            final ArrayList<Doctor> doctors = Session.getClient().getDoctors();
            ArrayList<ModeratorExpertiseController.ExpertiseRow> expertiseRows = new ArrayList<>();

            for (Doctor doctorTmp : doctors) {
                expertiseRows.add(new ModeratorExpertiseController.ExpertiseRow(doctorTmp, new Expertise(doctorTmp.getId(), "")));
                for (Expertise expertiseTmp : doctorTmp.getDoctorExpertise()) {
                    expertiseRows.add(new ModeratorExpertiseController.ExpertiseRow(doctorTmp, expertiseTmp));
                }
            }

            tvExpertise.getItems().clear();
            tvExpertise.getItems().addAll(expertiseRows);
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }


        FilteredList<ExpertiseRow> filteredExpertise = new FilteredList<>(tvExpertise.getItems(), b -> true);

        tfSurname.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredExpertise.setPredicate(doctor -> {
                if (newValue.isEmpty() || newValue.isBlank()) return doctor.getName().contains(tfName.getText());
                return doctor.getSurname().contains(newValue) && doctor.getName().contains(tfName.getText());
            });
            SortedList<ExpertiseRow> sortedExpertise = new SortedList<>(filteredExpertise);
            sortedExpertise.comparatorProperty().bind(tvExpertise.comparatorProperty());
            tvExpertise.setItems(sortedExpertise);

        });


        tfName.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredExpertise.setPredicate(doctor -> {
                if (newValue.isEmpty() || newValue.isBlank()) return doctor.getSurname().contains(tfSurname.getText());
                return doctor.getName().contains(newValue) && doctor.getSurname().contains(tfSurname.getText());
            });
            SortedList<ExpertiseRow> sortedExpertise = new SortedList<>(filteredExpertise);
            sortedExpertise.comparatorProperty().bind(tvExpertise.comparatorProperty());
            tvExpertise.setItems(sortedExpertise);
        });


        tvExpertise.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                tfExpertise.setVisible(true);
                textExpertise.setVisible(true);
                btnAdd.setVisible(true);
                btnCancel.setVisible(true);
                textAdd.setVisible(true);

                tfName.setVisible(false);
                textSearch.setVisible(false);
                tfSurname.setVisible(false);
                textSurname.setVisible(false);
                textName.setVisible(false);

                doc = newSelection.getDoctor();
                exp = newSelection.getExpertise();
            }
        });


        tcRemove.setCellFactory(tableColumn -> new TableCell<>() {
            private final Button removeButton = new Button("Delete");

            {
                removeButton.setOnAction((ActionEvent event) -> {

                    ExpertiseRow expertiseRow = getTableView().getItems().get(getIndex());

                    doc = expertiseRow.getDoctor();
                    exp = expertiseRow.getExpertise();
                    try {
                        Session.getClient().removeExpertise(doc.getId(), exp.getExpertise());
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("moderatorExpertise.fxml")));
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
                    setGraphic(removeButton);
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

    @FXML
    void btnAddClicked(ActionEvent event) throws IOException, InterruptedException {
        exp = new Expertise(doc.getId(), tfExpertise.getText());
        Session.getClient().addExpertise(doc.getId(), exp.getExpertise());
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("moderatorExpertise.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnCancelClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("moderatorExpertise.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static class ExpertiseRow {
        private final String name;
        private final String surname;
        private final String email;
        private final Doctor doctor;
        private final Expertise expertise;
        private final String areaOfExpertise;


        public ExpertiseRow(Doctor doctor, Expertise expertise) {

            this.name = doctor.getName();
            this.surname = doctor.getSurname();
            this.doctor = doctor;
            this.expertise = expertise;
            this.areaOfExpertise = expertise.getExpertise();
            this.email = doctor.getEmail();

        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public Doctor getDoctor() {
            return doctor;
        }

        public Expertise getExpertise() {
            return expertise;
        }

        public String getAreaOfExpertise() {
            return areaOfExpertise;
        }

        public String getEmail() {
            return email;
        }
    }
}
