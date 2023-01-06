package src.ui.patient;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import src.ui.Singleton;
import src.users.Doctor;
import src.users.Patient;
import src.users.User;
import src.visit.Visit;
import src.visit.VisitStatus;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class PatientMyVisitsController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TableColumn<?, ?> tcDate;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TableColumn<VisitRow, Void> tcRate;

    @FXML
    private TableColumn<VisitRow, String> tcPick;

    @FXML
    private TableColumn<?, ?> tcRating;

    @FXML
    private TableColumn<?, ?> tcStatus;

    @FXML
    private TableColumn<?, ?> tcSurname;

    @FXML
    private TableColumn<?, ?> tcTime;

    @FXML
    private TableView<VisitRow> tvTable;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tcTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tcRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        try {
            final ArrayList<Visit> visits = Singleton.getClient().getVisitsByClientId(Singleton.getUser().getId());
            ArrayList<VisitRow> visitRows = new ArrayList<>();

            for (Visit visit : visits) {
                Doctor doctor = Singleton.getClient().getDoctorById(visit.getDoctor().getId());
                visitRows.add(new VisitRow(visit, doctor, Singleton.getUser()));
            }
            tvTable.getItems().addAll(visitRows);

            tcPick.setCellFactory(tableColumn -> new TableCell<>() {
                private final ComboBox<String> rateBox = new ComboBox<>();

                {
                    rateBox.getItems().addAll("1", "2", "3", "4", "5");
                    rateBox.setValue("5");
                }

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        if (getTableView().getItems().get(getIndex()).status.equals(VisitStatus.COMPLETED.toString().toLowerCase())) {
                            setGraphic(rateBox);
                        } else {
                            setGraphic(null);
                        }
                    }
                }
            });

            tcRate.setCellFactory(tableColumn -> new TableCell<>() {
                private final Button rateButton = new Button("Rate");

                {
                    rateButton.setOnAction((ActionEvent event) -> {
                        try {
                            VisitRow visitRow = getTableView().getItems().get(getIndex());
                            Visit visit = visitRow.getVisit();

                            visit.setRating(Integer.parseInt("0")); // TODO get value from combobox
                            
                            Singleton.getClient().updateVisit(visit);
                            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patientMyVisits.fxml")));
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
                        if (getTableView().getItems().get(getIndex()).status.equals(VisitStatus.COMPLETED.toString().toLowerCase())) {
                            setGraphic(rateButton);
                        } else {
                            setGraphic(null);
                        }
                    }
                }
            });

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    @FXML
    void btnMyVisitsClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patientMyVisits.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnNewVisitClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patientNewVisit.fxml")));
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
    void btnStartClicked(ActionEvent event) throws Exception {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patient.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static class VisitRow {
        private final String name;
        private final String surname;
        private final String date;
        private final String time;
        private final String status;
        private final String rating;
        private final Doctor doctor;
        private final Patient patient;
        private final Visit visit;

        public VisitRow(Visit visit, Doctor doctor, User patient) {

            this.name = doctor.getName();
            this.surname = doctor.getSurname();
            this.date = visit.getDate().toString();
            this.time = visit.getTime().toString() + "-" + visit.getTime().plusMinutes(visit.getDuration()).toString();

            switch (visit.getVisitStatus()) {
                case CANCELED_WAITING_APPROVAL, PENDING_WAITING_APPROVAL -> status = "awaiting approval";
                default -> status = visit.getStatus().toString().toLowerCase();
            }
            this.rating = Integer.toString(visit.getRating());
            this.doctor = doctor;
            this.patient = new Patient(patient);
            this.visit = visit;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String getStatus() {
            return status;
        }

        public String getRating() {
            return rating;
        }

        public Doctor getDoctor() {
            return doctor;
        }

        public Patient getPatient() {
            return patient;
        }

        public Visit getVisit() {
            return visit;
        }
    }
}
