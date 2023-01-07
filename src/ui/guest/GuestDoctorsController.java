package src.ui.guest;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.clinic.Clinic;
import src.expertise.Expertise;
import src.schedule.Schedule;
import src.ui.Singleton;
import src.ui.doctor.DoctorMyVisitsController;
import src.users.Doctor;
import src.users.Patient;
import src.users.Permissions;
import src.users.User;
import src.visit.Visit;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class GuestDoctorsController implements Initializable {
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
    private VBox vBox1;
    @FXML
    private AnchorPane anchorPane1;

    @FXML
    private TableColumn<?, ?> tcCity;

    @FXML
    private TableColumn<?, ?> tcDay;

    @FXML
    private TableColumn<?, ?> tcExpertise;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TableColumn<?, ?> tcSurname;

    @FXML
    private TableColumn<?, ?> tcWH;

    @FXML
    private TableView<DoctorRow> tvTable;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tcWH.setCellValueFactory(new PropertyValueFactory<>("workingHours"));
        tcDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        tcSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tcExpertise.setCellValueFactory(new PropertyValueFactory<>("expertise"));


        ArrayList<DoctorRow> doctorRows = new ArrayList<DoctorRow>();
        try {
            ArrayList<Doctor> doctorS = Singleton.getClient().getDoctors();
            for (Doctor doc : doctorS) {
                for (Schedule sch : doc.getDoctorSchedules()) {
                    for (Expertise ex : doc.getDoctorExpertise()) {
                        doctorRows.add(new DoctorRow(sch, doc, Singleton.getClient().getClinic(sch.getClinicId()), ex.getExpertise()));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
        tvTable.getItems().addAll(doctorRows);

        FilteredList<DoctorRow> filteredDoctorRows = new FilteredList<>(tvTable.getItems(), b -> true);


        tfExpertise.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDoctorRows.setPredicate(doctorRow -> {
                if (newValue.isEmpty() || newValue.isBlank()) return doctorRow.getCity().contains(tfCity.getText());
                return doctorRow.getExpertise().contains(newValue) && doctorRow.getCity().contains(tfCity.getText());
            });
            SortedList<DoctorRow> sortedDoctors = new SortedList<>(filteredDoctorRows);
            sortedDoctors.comparatorProperty().bind(tvTable.comparatorProperty());
            tvTable.setItems(sortedDoctors);

        });


        tfCity.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDoctorRows.setPredicate(doctorRow -> {
                if (newValue.isEmpty() || newValue.isBlank())
                    return doctorRow.getExpertise().contains(tfExpertise.getText());
                return doctorRow.getCity().contains(newValue) && doctorRow.getExpertise().contains(tfExpertise.getText());
            });
            SortedList<DoctorRow> sortedDoctors = new SortedList<>(filteredDoctorRows);
            sortedDoctors.comparatorProperty().bind(tvTable.comparatorProperty());
            tvTable.setItems(sortedDoctors);
        });

    }

    @FXML
    void btnClinicsClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("guestClinics.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnDoctorsClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("guestDoctors.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnLogInClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../login/login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnStartClicked(ActionEvent event) throws Exception {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("guest.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static class DoctorRow {
        private final String name;
        private final String surname;
        private final String workingHours;
        private final String day;
        private final String city;
        private final Doctor doctor;
        private final Clinic clinic;
        private final Schedule schedule;

        private final String expertise;


        public DoctorRow(Schedule schedule, Doctor doctor, Clinic clinic, String expertise) {

            this.name = doctor.getName();
            this.surname = doctor.getSurname();
            this.day = schedule.getDay().toString();
            this.workingHours = (schedule.getStartTime().toString() + " - " + schedule.getEndTime().toString());
            this.city = clinic.getCity();
            this.doctor = doctor;
            this.clinic = clinic;
            this.schedule = schedule;
            this.expertise = expertise;

        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public String getWorkingHours() {
            return workingHours;
        }

        public String getDay() {
            return day;
        }

        public String getCity() {
            return city;
        }

        public Doctor getDoctor() {
            return doctor;
        }

        public Clinic getClinic() {
            return clinic;
        }

        public Schedule getSchedule() {
            return schedule;
        }

        public String getExpertise() {
            return expertise;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DoctorRow doctorRow = (DoctorRow) o;
            return Objects.equals(name, doctorRow.name) && Objects.equals(surname, doctorRow.surname) && Objects.equals(workingHours, doctorRow.workingHours) && Objects.equals(day, doctorRow.day) && Objects.equals(city, doctorRow.city) && Objects.equals(doctor, doctorRow.doctor) && Objects.equals(clinic, doctorRow.clinic) && Objects.equals(schedule, doctorRow.schedule) && Objects.equals(expertise, doctorRow.expertise);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, surname, workingHours, day, city, doctor, clinic, schedule, expertise);
        }
    }

}



