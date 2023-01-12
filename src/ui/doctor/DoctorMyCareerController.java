package src.ui.doctor;

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
import src.clinic.Clinic;
import src.schedule.Schedule;
import src.ui.Session;
import src.users.Doctor;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class DoctorMyCareerController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Schedule sched;
    private Clinic clin;
    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCreate;

    @FXML
    private ComboBox<String> cboxDay;

    @FXML
    private TableColumn<Clinic, Void> tcAddSchedule;

    @FXML
    private TableColumn<?, ?> tcAddress;

    @FXML
    private TableColumn<?, ?> tcCity;

    @FXML
    private TableColumn<?, ?> tcDay;

    @FXML
    private TableColumn<ScheduleRow, Void> tcEdit;

    @FXML
    private TableColumn<?, ?> tcEnd;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TableColumn<ScheduleRow, Void> tcRemove;

    @FXML
    private TableColumn<?, ?> tcStart;

    @FXML
    private Text textError;


    @FXML
    private TextField tfEndM;

    @FXML
    private TextField tfStartM;

    @FXML
    private Text textDay;

    @FXML
    private Text textEnd;

    @FXML
    private Text textStart;

    @FXML
    private Text textTitle;

    @FXML
    private TextField tfEnd;

    @FXML
    private TextField tfStart;

    @FXML
    private TableView<Clinic> tvClinic;

    @FXML
    private TableView<ScheduleRow> tvSchedule;


    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tcDay.setCellValueFactory(new PropertyValueFactory<>("prettyDayOfWeek"));
        tcStart.setCellValueFactory(new PropertyValueFactory<>("startHour"));
        tcEnd.setCellValueFactory(new PropertyValueFactory<>("endHour"));

        textError.setVisible(false);
        cboxDay.setVisible(false);
        textDay.setVisible(false);
        textEnd.setVisible(false);
        textStart.setVisible(false);
        textTitle.setVisible(false);
        btnCancel.setVisible(false);
        btnCreate.setVisible(false);
        tfEnd.setVisible(false);
        tfStart.setVisible(false);
        tfEndM.setVisible(false);
        tfStartM.setVisible(false);


        ArrayList<Clinic> clinics = new ArrayList<Clinic>();
        try {

            clinics = Session.getClient().getDoctorById(Session.getUser().getId()).getDoctorClinics();
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
        tvClinic.getItems().addAll(clinics);

        FilteredList<Clinic> filteredClinics = new FilteredList<>(tvClinic.getItems(), b -> true);

        tvClinic.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                clin = newSelection;
                try {
                    final ArrayList<Schedule> schedules2 = Session.getClient().getDoctorScheduleInClinic(Session.getUser().getId(), newSelection.getClinicId());
                    ArrayList<ScheduleRow> schedulesRow = new ArrayList<>();
                    Doctor d = Session.getClient().getDoctorById(Session.getUser().getId());
                    for (Schedule s : schedules2) {
                        schedulesRow.add(new ScheduleRow(d, s));
                    }
                    tvSchedule.getItems().clear();
                    tvSchedule.getItems().addAll(schedulesRow);
                } catch (Exception e) {
                    System.out.println("Error" + e.getMessage());
                }


                FilteredList<ScheduleRow> filteredSchedules = new FilteredList<>(tvSchedule.getItems(), b -> true);

                tcEdit.setCellFactory(tableColumn -> new TableCell<>() {
                    private final Button editButton = new Button("Edit");

                    {
                        editButton.setOnAction((ActionEvent event) -> {
                            ScheduleRow scheduleRow = getTableView().getItems().get(getIndex());

                            filteredSchedules.setPredicate(schedule2 -> schedule2.equals(scheduleRow));
                            tvSchedule.setItems(filteredSchedules);

                            tcEdit.setVisible(false);
                            tcRemove.setVisible(false);

                            tcAddSchedule.setVisible(false);
                            textEnd.setVisible(true);
                            textStart.setVisible(true);
                            textTitle.setVisible(true);
                            btnCancel.setVisible(true);
                            btnCreate.setVisible(true);
                            tfEnd.setVisible(true);
                            tfStart.setVisible(true);
                            tfEndM.setVisible(true);
                            tfStartM.setVisible(true);

                            String startH = scheduleRow.getStartHour().substring(0, scheduleRow.getStartHour().indexOf(":"));
                            String startM = scheduleRow.getStartHour().substring(scheduleRow.getStartHour().indexOf(":") + 1);
                            String endH = scheduleRow.getEndHour().substring(0, scheduleRow.getEndHour().indexOf(":"));
                            String endM = scheduleRow.getEndHour().substring(scheduleRow.getEndHour().indexOf(":") + 1);

                            tfStart.setText(startH);
                            tfStartM.setText(startM);
                            tfEnd.setText(endH);
                            tfEndM.setText(endM);

                            btnCreate.setText("Edit");
                            textTitle.setText("Editor Tool");


                            sched = scheduleRow.getSchedule();
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

                tcRemove.setCellFactory(tableColumn -> new TableCell<>() {
                    private final Button removeButton = new Button("Delete");

                    {
                        removeButton.setOnAction((ActionEvent event) -> {
                            ScheduleRow scheduleRow = getTableView().getItems().get(getIndex());

                            filteredSchedules.setPredicate(schedule2 -> schedule2.equals(scheduleRow));
                            tvSchedule.setItems(filteredSchedules);

                            sched = scheduleRow.getSchedule();
                            try {
                                Session.getClient().removeSchedule(sched.getDoctorId(), sched.getClinicId(), sched.getDay());
                                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("doctorMyCareer.fxml")));
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
        });

        tcAddSchedule.setCellFactory(tableColumn -> new TableCell<>() {
            private final Button addButton = new Button("Add");

            {
                addButton.setOnAction((ActionEvent event) -> {
                    Clinic clinic = getTableView().getItems().get(getIndex());

                    clin = clinic;
                    filteredClinics.setPredicate(clinic2 -> clinic2.equals(clinic));
                    tvClinic.setItems(filteredClinics);

                    cboxDay.setVisible(true);

                    ArrayList<String> usedDays = new ArrayList<>();

                    cboxDay.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

                    ArrayList<Schedule> tmp = Session.getClient().getDoctorScheduleInClinic(Session.getUser().getId(), clin.getClinicId());
                    for (Schedule s : tmp) {
                        usedDays.add(getPrettyDay(s.getDay()));
                    }
                    cboxDay.getItems().removeAll(usedDays);
                    if (!cboxDay.getItems().isEmpty()) {
                        cboxDay.setValue(cboxDay.getItems().get(0));
                    }
                    textDay.setVisible(true);
                    tcAddSchedule.setVisible(false);
                    textEnd.setVisible(true);
                    textStart.setVisible(true);
                    textTitle.setVisible(true);
                    btnCancel.setVisible(true);
                    btnCreate.setVisible(true);
                    tfEnd.setVisible(true);
                    tfStart.setVisible(true);
                    tfEndM.setVisible(true);
                    tfStartM.setVisible(true);

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
    }

    @FXML
    void btnCancelClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("doctorMyCareer.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnCreateClicked(ActionEvent event) throws IOException, InterruptedException {
        if (Objects.equals(btnCreate.getText(), "Edit")) {
            textError.setVisible(true);
            if (LocalTime.of(Integer.parseInt(tfEnd.getText()), Integer.parseInt(tfEndM.getText())).isAfter(LocalTime.of(Integer.parseInt(tfStart.getText()), Integer.parseInt(tfStartM.getText())))) {
                sched.setEndTime(LocalTime.of(Integer.parseInt(tfEnd.getText()), Integer.parseInt(tfEndM.getText())));
                sched.setStartTime(LocalTime.of(Integer.parseInt(tfStart.getText()), Integer.parseInt(tfStartM.getText())));
                Session.getClient().updateSchedule(sched);
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("doctorMyCareer.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setResizable(false);
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } else {
            textError.setVisible(true);
            if (LocalTime.of(Integer.parseInt(tfEnd.getText()), Integer.parseInt(tfEndM.getText())).isAfter(LocalTime.of(Integer.parseInt(tfStart.getText()), Integer.parseInt(tfStartM.getText())))) {
                Session.getClient().addSchedule(new Schedule(Session.getUser().getId(), clin.getClinicId(), getEnumDay(cboxDay.getValue()), LocalTime.of(Integer.parseInt(tfStart.getText()), Integer.parseInt(tfStartM.getText())), LocalTime.of(Integer.parseInt(tfEnd.getText()), Integer.parseInt(tfEndM.getText()))));
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("doctorMyCareer.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setResizable(false);
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
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

    public static class ScheduleRow {

        private final Schedule schedule;

        private final String startHour;
        private final String endHour;

        private final DayOfWeek day;

        public String getPrettyDayOfWeek() {
            return prettyDayOfWeek;
        }

        private final String prettyDayOfWeek;

        private final Doctor doctor;


        public ScheduleRow(Doctor doctor, Schedule schedule) {

            this.day = schedule.getDay();
            this.startHour = schedule.getStartTime().toString();
            this.endHour = schedule.getEndTime().toString();
            this.doctor = doctor;
            this.schedule = schedule;
            this.prettyDayOfWeek = getPrettyDay(day);

        }

        public Schedule getSchedule() {
            return schedule;
        }

        public String getStartHour() {
            return startHour;
        }

        public String getEndHour() {
            return endHour;
        }

        public DayOfWeek getDay() {
            return day;
        }

        public Doctor getDoctor() {
            return doctor;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ScheduleRow that = (ScheduleRow) o;
            return Objects.equals(schedule, that.schedule) && Objects.equals(startHour, that.startHour) && Objects.equals(endHour, that.endHour) && day == that.day && Objects.equals(doctor, that.doctor);
        }

        @Override
        public int hashCode() {
            return Objects.hash(schedule, startHour, endHour, day, doctor);
        }
    }

    public static String getPrettyDay(DayOfWeek day) {
        switch (day) {
            case MONDAY -> {
                return "Monday";
            }
            case TUESDAY -> {
                return "Tuesday";
            }
            case WEDNESDAY -> {
                return "Wednesday";
            }
            case THURSDAY -> {
                return "Thursday";
            }
            case FRIDAY -> {
                return "Friday";
            }
            case SATURDAY -> {
                return "Saturday";
            }
            case SUNDAY -> {
                return "Sunday";
            }
        }
        return "";
    }

    public static DayOfWeek getEnumDay(String day) {
        return DayOfWeek.valueOf(day.toUpperCase(Locale.ROOT));
    }
}
