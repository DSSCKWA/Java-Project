package src.ui.doctor;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.clinic.Clinic;
import src.expertise.Expertise;
import src.schedule.Schedule;
import src.ui.Session;
import src.users.Doctor;
import src.users.Patient;
import src.users.User;
import src.visit.Visit;
import src.visit.VisitStatus;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class DoctorNewVisitController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableColumn<?, ?> tcCity;

    @FXML
    private TableColumn<?, ?> tcExpertise;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TableColumn<NewVisit, Void> tcSchedule;

    @FXML
    private TableColumn<?, ?> tcSurname;

    @FXML
    private TableColumn<?, ?> tcTime;
    @FXML
    private TableView<NewVisit> tvTable;
    @FXML
    private TextField tfExpertise;

    @FXML
    private TextField tfCity;

    @FXML
    private DatePicker tfDate;

    @FXML
    private Text txPickDate;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tcCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tcExpertise.setCellValueFactory(new PropertyValueFactory<>("expertise"));
        tcTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        tfDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        try {
            final ArrayList<Doctor> doctors = Session.getClient().getDoctors();
            doctors.removeIf(doctor -> Session.getUser().getEmail().equals(doctor.getEmail()));
            tfDate.valueProperty().addListener((observableValue, localDate, pickedDate) -> {
                txPickDate.setVisible(pickedDate == null);
                if (pickedDate != null) {
                    updateVisitList(doctors, pickedDate);
                } else {
                    if (!tvTable.getItems().isEmpty()) {
                        tvTable.getItems().clear();
                    }
                }
            });

            tcSchedule.setCellFactory(tableColumn -> new TableCell<>() {
                private final Button scheduleButton = new Button("Schedule");

                {
                    scheduleButton.setOnAction((ActionEvent event) -> {
                        NewVisit newVisit = getTableView().getItems().get(getIndex());
                        User user = Session.getUser();
                        try {
                            Session.getClient().addVisit(new Visit(
                                    tfDate.getValue(),
                                    newVisit.getStartTime(),
                                    (int) Duration.between(newVisit.getStartTime(), newVisit.getEndTime()).toMinutes(),
                                    newVisit.getDoctor(),
                                    new Patient(user),
                                    0,
                                    VisitStatus.PENDING_WAITING_APPROVAL
                            ));
                            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("doctorNewVisit.fxml")));
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setResizable(false);
                            scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        Stage popup = new Stage();
                        popup.getIcons().add(new Image("file:src/ui/logo/logo.png"));
                        popup.initModality(Modality.APPLICATION_MODAL);
                        popup.setTitle("Visit scheduled successfully!");
                        Label popupLabel = new Label("Your visit has been scheduled successfully.");
                        Button popupButton = new Button("OK");
                        popupButton.setOnAction(e -> popup.close());
                        VBox layout = new VBox(10);
                        layout.getChildren().addAll(popupLabel, popupButton);
                        layout.setAlignment(Pos.CENTER);
                        popup.setScene(new Scene(layout, 300, 250));
                        popup.showAndWait();
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(scheduleButton);
                    }
                }
            });


            FilteredList<NewVisit> filteredVisits = new FilteredList<>(tvTable.getItems(), b -> true);

            tfCity.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredVisits.setPredicate(visit -> {
                    if (newValue.isEmpty() || newValue.isBlank())
                        return visit.getExpertise().contains(tfExpertise.getText());
                    return visit.getCity().contains(newValue) && visit.getExpertise().contains(tfExpertise.getText());
                });
                SortedList<NewVisit> sortedVisits = new SortedList<>(filteredVisits);
                sortedVisits.comparatorProperty().bind(tvTable.comparatorProperty());
                tvTable.setItems(sortedVisits);

            });

            tfExpertise.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredVisits.setPredicate(visit -> {
                    if (newValue.isEmpty() || newValue.isBlank()) return visit.getCity().contains(tfCity.getText());
                    return visit.getExpertise().contains(newValue) && visit.getCity().contains(tfCity.getText());
                });
                SortedList<NewVisit> sortedVisits = new SortedList<>(filteredVisits);
                sortedVisits.comparatorProperty().bind(tvTable.comparatorProperty());
                tvTable.setItems(sortedVisits);
            });

        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    @FXML
    void btnEquipmentClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("doctorEquipment.fxml")));
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
    void btnMyCareerClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("doctorMyCareer.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnMyVisitsClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("doctorMyVisits.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnNewVisitClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("doctorNewVisit.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnStartClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("doctor.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static class NewVisit {
        private final String name;
        private final String surname;
        private final String city;
        private final String time;
        private final String expertise;

        private final Doctor doctor;
        private final LocalTime startTime;
        private final LocalTime endTime;

        public NewVisit(String name, String surname, String city, String expertise, Doctor doctor, LocalTime startTime, LocalTime endTime) {
            this.name = name;
            this.surname = surname;
            this.city = city;
            this.time = startTime.toString() + "-" + endTime.toString();
            this.expertise = expertise;
            this.doctor = doctor;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public String getCity() {
            return city;
        }

        public String getTime() {
            return time;
        }

        public String getExpertise() {
            return expertise;
        }

        public Doctor getDoctor() {
            return doctor;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }
    }

    private boolean visitExists(ArrayList<Visit> visits, LocalDate date, LocalTime startTime, LocalTime endTime, int doctorId) {
        for (Visit visit : visits) {
            LocalTime start = visit.getTime();
            LocalTime end = start.plusMinutes(visit.getDuration());
            if (visit.getDate().equals(date) &&
                    start.equals(startTime) &&
                    end.equals(endTime) &&
                    visit.getDoctor().getId() == doctorId &&
                    !visit.getVisitStatus().equals(VisitStatus.CANCELED)
            ) {
                return true;
            }
        }
        return false;
    }

    private void updateVisitList(ArrayList<Doctor> doctors, LocalDate pickedDate) {
        try {
            ArrayList<Visit> visits = Session.getClient().getVisits();
            ArrayList<NewVisit> newVisits = new ArrayList<>();
            for (Doctor doctor : doctors) {
                for (Schedule schedule : doctor.getDoctorSchedules()) {
                    if (!schedule.getDay().equals(pickedDate.getDayOfWeek())) {
                        continue;
                    }
                    Clinic clinic = Session.getClient().getClinic(schedule.getClinicId());

                    LocalTime start = schedule.getStartTime();
                    LocalTime next = start.plusMinutes(30);

                    while (Duration.between(next, schedule.getEndTime()).toMinutes() >= 0) {
                        if (!visitExists(visits, pickedDate, start, next, doctor.getId())) {
                            for (Expertise expertise : doctor.getDoctorExpertise()) {
                                newVisits.add(new NewVisit(
                                        doctor.getName(),
                                        doctor.getSurname(),
                                        clinic.getCity(),
                                        expertise.getExpertise(),
                                        doctor,
                                        start,
                                        next
                                ));
                            }
                        }
                        start = next;
                        next = start.plusMinutes(30);
                    }
                }
            }
            tvTable.getItems().clear();
            tvTable.getItems().addAll(newVisits);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
