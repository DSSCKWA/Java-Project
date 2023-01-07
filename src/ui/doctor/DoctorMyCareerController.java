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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.clinic.Clinic;
import src.equipment.Equipment;
import src.schedule.Schedule;
import src.ui.Singleton;
import src.users.Doctor;
import src.users.Patient;
import src.users.Permissions;
import src.users.User;
import src.visit.Visit;
import src.visit.VisitStatus;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
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
    private TableColumn<Schedule, Void> tcEdit;

    @FXML
    private TableColumn<?, ?> tcEnd;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TableColumn<Schedule, Void> tcRemove;

    @FXML
    private TableColumn<?, ?> tcStart;

    @FXML
    private Text textStartM;


    @FXML
    private Text textEndM;

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
    private TableView<Schedule> tvSchedule;


    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tcDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        tcStart.setCellValueFactory(new PropertyValueFactory<>("startHour"));
        tcEnd.setCellValueFactory(new PropertyValueFactory<>("endHour"));


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
        textStartM.setVisible(false);
        textEndM.setVisible(false);


        ArrayList<Clinic> clinics = new ArrayList<Clinic>();
        try {
            clinics = ((Doctor) Singleton.getUser()).getDoctorClinics();
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
        tvClinic.getItems().addAll(clinics);

        FilteredList<Clinic> filteredClinics = new FilteredList<>(tvClinic.getItems(), b -> true);

        tvClinic.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                clin = newSelection;
                try {
                    final ArrayList<Schedule> schedules = Singleton.getClient().getDoctorScheduleInClinic(((Doctor) Singleton.getUser()).getId(), newSelection.getClinicId());

                    tvSchedule.getItems().clear();
                    tvSchedule.getItems().addAll(schedules);
                } catch (Exception e) {
                    System.out.println("Error" + e.getMessage());
                }


                FilteredList<Schedule> filteredSchedules = new FilteredList<>(tvSchedule.getItems(), b -> true);

                tcEdit.setCellFactory(tableColumn -> new TableCell<>() {
                    private final Button editButton = new Button("Edit");

                    {
                        editButton.setOnAction((ActionEvent event) -> {
                            Schedule schedule = getTableView().getItems().get(getIndex());

                            filteredSchedules.setPredicate(schedule2 -> schedule2.equals(schedule));
                            tvSchedule.setItems(filteredSchedules);

                            //cboxDay.setVisible(true);
                            //textDay.setVisible(true);
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
                            textStartM.setVisible(true);
                            textEndM.setVisible(true);
                            btnCreate.setText("Edit");
                            textTitle.setText("Editor Tool");


                            sched = schedule;
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
                    private final Button removeButton = new Button("Remove");

                    {
                        removeButton.setOnAction((ActionEvent event) -> {
                            Schedule schedule = getTableView().getItems().get(getIndex());

                            filteredSchedules.setPredicate(schedule2 -> schedule2.equals(schedule));
                            tvSchedule.setItems(filteredSchedules);

                            sched = schedule;
                            try {
                                Singleton.getClient().removeSchedule(sched.getDoctorId(), sched.getClinicId(), sched.getDay());
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
            private final Button addButton = new Button("Add new");

            {
                addButton.setOnAction((ActionEvent event) -> {
                    Clinic clinic = getTableView().getItems().get(getIndex());

                    clin = clinic;
                    filteredClinics.setPredicate(clinic2 -> clinic2.equals(clinic));
                    tvClinic.setItems(filteredClinics);

                    cboxDay.setVisible(true);

                    ArrayList<String> usedDays = new ArrayList<>();

                    cboxDay.getItems().addAll("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday");

                    ArrayList<Schedule> tmp = Singleton.getClient().getDoctorScheduleInClinic(((Doctor) Singleton.getUser()).getId(), clin.getClinicId());
                    for (Schedule s : tmp) {
                        usedDays.add(s.getDay().toString());
                    }
                    cboxDay.getItems().removeAll(usedDays);
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
                    textStartM.setVisible(true);
                    textEndM.setVisible(true);

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
            sched.setEndTime(LocalTime.of(Integer.parseInt(tfEnd.getText()), Integer.parseInt(tfEndM.getText())));
            sched.setStartTime(LocalTime.of(Integer.parseInt(tfStart.getText()), Integer.parseInt(tfStartM.getText())));
            Singleton.getClient().updateSchedule(sched);
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("doctorMyCareer.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Singleton.getClient().addSchedule(new Schedule(((Doctor) Singleton.getUser()).getId(), clin.getClinicId(), DayOfWeek.valueOf(cboxDay.getValue().toString().toUpperCase(Locale.ROOT)), LocalTime.of(Integer.parseInt(tfStart.getText()), Integer.parseInt(tfStartM.getText())), LocalTime.of(Integer.parseInt(tfEnd.getText()), Integer.parseInt(tfEndM.getText()))));
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("doctorMyCareer.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
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

}