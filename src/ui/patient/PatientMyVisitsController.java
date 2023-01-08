package src.ui.patient;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.ui.Session;
import src.users.Doctor;
import src.users.Patient;
import src.users.User;
import src.visit.Visit;
import src.visit.VisitStatus;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class PatientMyVisitsController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private LocalDate pickedDate;
    private VisitRow selectedVisit;
    @FXML
    private Button btCancel;

    @FXML
    private Button btConfirm;

    @FXML
    private TableColumn<?, ?> tcCancel;

    @FXML
    private TableColumn<VisitRow, Void> tcEdit;

    @FXML
    private TableColumn<?, ?> tcDate;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TableColumn<VisitRow, Void> tcAction;

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
    private Text txPickDate;

    @FXML
    private DatePicker dpDate;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        btCancel.setVisible(false);
        btConfirm.setVisible(false);
        txPickDate.setVisible(false);
        dpDate.setVisible(false);

        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tcTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tcRating.setCellValueFactory(new PropertyValueFactory<>("rating"));

        try {
            final ArrayList<Visit> visits = Session.getClient().getVisitsByClientId(Session.getUser().getId());
            ArrayList<VisitRow> visitRows = new ArrayList<>();

            for (Visit visit : visits) {
                Doctor doctor = Session.getClient().getDoctorById(visit.getDoctor().getId());
                visitRows.add(new VisitRow(visit, doctor, Session.getUser()));
            }
            tvTable.getItems().addAll(visitRows);
            FilteredList<VisitRow> filteredVisits = new FilteredList<>(tvTable.getItems(), b -> true);
            tcPick.setCellFactory(tableColumn -> new TableCell<>() {
                private final ComboBox<String> rateBox = new ComboBox<>();

                {
                    rateBox.getItems().addAll("1", "2", "3", "4", "5");
                    rateBox.setValue("5");
                    rateBox.valueProperty().addListener((observableValue, oldValue, newValue) -> getTableView().getItems().get(getIndex()).setDesiredRating(newValue));
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

            tcAction.setCellFactory(tableColumn -> new TableCell<>() {
                private final Button rateButton = new Button("Rate");

                {
                    rateButton.setOnAction((ActionEvent event) -> {
                        try {
                            VisitRow visitRow = getTableView().getItems().get(getIndex());
                            Visit visit = visitRow.getVisit();
                            visit.setRating(Integer.parseInt(visitRow.getDesiredRating()));
                            Session.getClient().updateVisit(visit);
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

                private final Button cancelButton = new Button("Cancel");

                {
                    cancelButton.setOnAction((ActionEvent event) -> {
                        try {
                            VisitRow visitRow = getTableView().getItems().get(getIndex());
                            Visit visit = visitRow.getVisit();
                            visit.setVisitStatus(VisitStatus.CANCELED_WAITING_APPROVAL);
                            Session.getClient().updateVisit(visit);
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
                        VisitRow visit = getTableView().getItems().get(getIndex());
                        if (visit.getVisit().getVisitStatus().equals(VisitStatus.COMPLETED)) {
                            setGraphic(rateButton);
                        } else if (!visit.getVisit().getVisitStatus().equals(VisitStatus.CANCELED)
                                && !visit.getVisit().getVisitStatus().equals(VisitStatus.CANCELED_WAITING_APPROVAL)
                        ) {
                            setGraphic(cancelButton);
                        } else {
                            setGraphic(null);
                        }
                    }
                }
            });

            tcEdit.setCellFactory(tableColumn -> new TableCell<>() {
                private final Button editButton = new Button("Edit");

                {
                    editButton.setOnAction((ActionEvent event) -> {
                        selectedVisit = getTableView().getItems().get(getIndex());
                        btCancel.setVisible(true);
                        btConfirm.setVisible(true);
                        txPickDate.setVisible(true);
                        dpDate.setVisible(true);
                        btConfirm.setDisable(true);
                        filteredVisits.setPredicate(visit -> visit.equals(selectedVisit));
                        tvTable.setItems(filteredVisits);

                        dpDate.setDayCellFactory(picker -> new DateCell() {
                            public void updateItem(LocalDate date, boolean empty) {
                                super.updateItem(date, empty);
                                setDisable(empty || date.compareTo(selectedVisit.getVisit().getDate()) < 1);
                            }
                        });
                        dpDate.valueProperty().addListener(new ChangeListener<>() {
                            @Override
                            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate newDate) {
                                btConfirm.setDisable(newDate == null);
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
                        if (!getTableView().getItems().get(getIndex()).getVisit().getVisitStatus().equals(VisitStatus.COMPLETED)
                                && !getTableView().getItems().get(getIndex()).getVisit().getVisitStatus().equals(VisitStatus.CANCELED)
                                && !getTableView().getItems().get(getIndex()).getVisit().getVisitStatus().equals(VisitStatus.CANCELED_WAITING_APPROVAL)
                        ) {
                            setGraphic(editButton);
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


    @FXML
    void btnCancel(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patientMyVisits.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnConfirm(ActionEvent event) throws IOException, InterruptedException {
        Visit visit = selectedVisit.getVisit();
        visit.setDate(dpDate.getValue());
        visit.setVisitStatus(VisitStatus.PENDING_WAITING_APPROVAL);
        Session.getClient().updateVisit(visit);
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patientMyVisits.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
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
        private String desiredRating = "5";

        public VisitRow(Visit visit, Doctor doctor, User patient) {

            this.name = doctor.getName();
            this.surname = doctor.getSurname();
            this.date = visit.getDate().toString();
            this.time = visit.getTime().toString() + "-" + visit.getTime().plusMinutes(visit.getDuration()).toString();

            switch (visit.getVisitStatus()) {
                case PENDING_WAITING_APPROVAL -> status = "awaiting approval";
                case CANCELED_WAITING_APPROVAL -> status = "awaiting cancellation";
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

        public String getDesiredRating() {
            return desiredRating;
        }

        public void setDesiredRating(String desiredRating) {
            this.desiredRating = desiredRating;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            VisitRow visitRow = (VisitRow) o;
            return Objects.equals(name, visitRow.name) && Objects.equals(surname, visitRow.surname) && Objects.equals(date, visitRow.date) && Objects.equals(time, visitRow.time) && Objects.equals(status, visitRow.status) && Objects.equals(rating, visitRow.rating) && Objects.equals(doctor, visitRow.doctor) && Objects.equals(patient, visitRow.patient) && Objects.equals(visit, visitRow.visit) && Objects.equals(desiredRating, visitRow.desiredRating);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, surname, date, time, status, rating, doctor, patient, visit, desiredRating);
        }
    }
}
