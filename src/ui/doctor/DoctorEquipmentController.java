package src.ui.doctor;

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
import src.clinic.Clinic;
import src.equipment.Equipment;
import src.equipment.EquipmentStatus;
import src.ui.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class DoctorEquipmentController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Equipment eq;

    private Clinic clin;
    @FXML
    private Button btnCancel;

    @FXML
    private Button btnConfirm;

    @FXML
    private ComboBox<String> cboxStatus;
    @FXML
    private TableColumn<EquipmentRow, Void> tcAction;

    @FXML
    private TableColumn<Clinic, Void> tcAdd;

    @FXML
    private TableColumn<?, ?> tcAddress;

    @FXML
    private TableColumn<?, ?> tcCity;

    @FXML
    private TableColumn<?, ?> tcEName;

    @FXML
    private TableColumn<Clinic, Void> tcEdit;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TableColumn<?, ?> tcStatus;

    @FXML
    private TableColumn<?, ?> tcClinicName;

    @FXML
    private TableColumn<Clinic, Void> tcTransfer;

    @FXML
    private Text textCEName;

    @FXML
    private Text textCName;

    @FXML
    private Text textCreate;

    @FXML
    private Text textEName;

    @FXML
    private Text textEStatus;

    @FXML
    private Text textSearch;

    @FXML
    private TextField tfCEName;

    @FXML
    private TextField tfCName;

    @FXML
    private TextField tfEName;

    @FXML
    private TextField tfEStatus;

    @FXML
    private TableView<Clinic> tvClinic;

    @FXML
    private TableView<EquipmentRow> tvEquipment;


    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        cboxStatus.getItems().addAll(
                "IN_USE", "IN_REPAIR", "DECOMISSIONED", "BROKEN"
        );

        textCreate.setVisible(false);
        textEName.setVisible(false);
        textEStatus.setVisible(false);
        tfEName.setVisible(false);
        cboxStatus.setVisible(false);
        btnCancel.setVisible(false);
        btnConfirm.setVisible(false);
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("address"));


        ArrayList<Clinic> clinics = new ArrayList<Clinic>();
        try {
            clinics = Session.getClient().getClinics();
        } catch (Exception e) {
            System.out.println("Error");
        }
        tvClinic.getItems().addAll(clinics);

        FilteredList<Clinic> filteredClinics = new FilteredList<>(tvClinic.getItems(), b -> true);

        tfCName.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredClinics.setPredicate(clinic -> {
                if (newValue.isEmpty() || newValue.isBlank()) return true;
                return clinic.getName().contains(newValue);
            });
            SortedList<Clinic> sortedClinics = new SortedList<>(filteredClinics);
            sortedClinics.comparatorProperty().bind(tvClinic.comparatorProperty());
            tvClinic.setItems(sortedClinics);
        });

        tvClinic.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {


                tcEName.setCellValueFactory(new PropertyValueFactory<>("name"));
                tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
                tcClinicName.setCellValueFactory(new PropertyValueFactory<>("clinicName"));

                try {
                    final ArrayList<Equipment> equipments = Session.getClient().getEquipmentByClinicId(newSelection.getClinicId());
                    ArrayList<DoctorEquipmentController.EquipmentRow> equipmentRows = new ArrayList<>();

                    for (Equipment equipmentTmp : equipments) {
                        Clinic clinicTmp = Session.getClient().getClinic(equipmentTmp.getClinicId());
                        equipmentRows.add(new DoctorEquipmentController.EquipmentRow(clinicTmp, equipmentTmp));
                    }

                    tvEquipment.getItems().clear();
                    tvEquipment.getItems().addAll(equipmentRows);
                } catch (Exception e) {
                    System.out.println("Error" + e.getMessage());
                }


                FilteredList<EquipmentRow> filteredEquipments = new FilteredList<>(tvEquipment.getItems(), b -> true);

                tfCEName.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredClinics.setPredicate(equipment -> {
                        if (newValue.isEmpty() || newValue.isBlank()) return true;
                        return equipment.getName().contains(newValue);
                    });
                    SortedList<EquipmentRow> sortedEquipments = new SortedList<>(filteredEquipments);
                    sortedEquipments.comparatorProperty().bind(tvEquipment.comparatorProperty());
                    tvEquipment.setItems(sortedEquipments);
                });

                tcAction.setCellFactory(tableColumn -> new TableCell<>() {
                    private final Button actionButton = new Button("Edit");

                    {
                        actionButton.setOnAction((ActionEvent event) -> {
                            EquipmentRow equipmentRow = getTableView().getItems().get(getIndex());

                            filteredEquipments.setPredicate(equipment2 -> equipment2.equals(equipmentRow));
                            tvEquipment.setItems(filteredEquipments);

                            textCreate.setText("Edition Tool");
                            btnConfirm.setText("Edit");
                            textCreate.setVisible(true);
                            textEName.setVisible(true);
                            textEStatus.setVisible(true);
                            tfEName.setVisible(true);
                            cboxStatus.setVisible(true);
                            btnCancel.setVisible(true);
                            btnConfirm.setVisible(true);

                            textSearch.setVisible(false);
                            textCEName.setVisible(false);
                            textCName.setVisible(false);
                            tfCEName.setVisible(false);
                            tfCName.setVisible(false);


                            eq = equipmentRow.getEquipment();
                            tfEName.setText(eq.getName());
                            cboxStatus.setValue("IN_USE");

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(actionButton);
                        }
                    }

                });
            }
        });

        tcAdd.setCellFactory(tableColumn -> new TableCell<>() {
            private final Button addButton = new Button("Add new");

            {
                addButton.setOnAction((ActionEvent event) -> {
                    Clinic clinic = getTableView().getItems().get(getIndex());

                    clin = clinic;
                    filteredClinics.setPredicate(clinic2 -> clinic2.equals(clinic));
                    tvClinic.setItems(filteredClinics);

                    textCreate.setVisible(true);
                    textEName.setVisible(true);
                    tfEName.setVisible(true);
                    btnCancel.setVisible(true);
                    btnConfirm.setVisible(true);

                    textSearch.setVisible(false);
                    textCEName.setVisible(false);
                    textCName.setVisible(false);
                    tfCEName.setVisible(false);
                    tfCName.setVisible(false);

                    tcAdd.setVisible(false);
                    tcEdit.setVisible(false);
                    tcTransfer.setVisible(false);
                    tcAction.setVisible(false);

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

        tcTransfer.setCellFactory(tableColumn -> new TableCell<>() {
            private final Button transferButton = new Button("Transfer");

            {
                transferButton.setOnAction((ActionEvent event) -> {
                    Clinic clinic = getTableView().getItems().get(getIndex());

                    clin = clinic;
                    filteredClinics.setPredicate(clinic2 -> clinic2.equals(clinic));
                    tvClinic.setItems(filteredClinics);

                    tcEName.setCellValueFactory(new PropertyValueFactory<>("name"));
                    tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
                    tcClinicName.setCellValueFactory(new PropertyValueFactory<>("clinicName"));

                    tcAdd.setVisible(false);
                    tcEdit.setVisible(false);
                    tcTransfer.setVisible(false);

                    try {
                        ArrayList<Equipment> equipments = Session.getClient().getEquipment();
                        equipments.removeAll(Session.getClient().getEquipmentByClinicId(clinic.getClinicId()));
                        ArrayList<DoctorEquipmentController.EquipmentRow> equipmentRows = new ArrayList<>();

                        for (Equipment equipmentTmp : equipments) {
                            Clinic clinicTmp = Session.getClient().getClinic(equipmentTmp.getClinicId());
                            equipmentRows.add(new DoctorEquipmentController.EquipmentRow(clinicTmp, equipmentTmp));
                        }
                        tvEquipment.getItems().clear();
                        tvEquipment.getItems().addAll(equipmentRows);
                    } catch (Exception e) {
                        System.out.println("Error" + e.getMessage());
                    }

                    FilteredList<EquipmentRow> filteredEquipments = new FilteredList<>(tvEquipment.getItems(), b -> true);

                    tfCEName.textProperty().addListener((observable, oldValue, newValue) -> {
                        filteredClinics.setPredicate(equipment -> {
                            if (newValue.isEmpty() || newValue.isBlank()) return true;
                            return equipment.getName().contains(newValue);
                        });
                        SortedList<EquipmentRow> sortedEquipments = new SortedList<>(filteredEquipments);
                        sortedEquipments.comparatorProperty().bind(tvEquipment.comparatorProperty());
                        tvEquipment.setItems(sortedEquipments);
                    });

                    tcAction.setCellFactory(tableColumn -> new TableCell<>() {
                        private final Button actionButton = new Button("Transfer");

                        {
                            actionButton.setOnAction((ActionEvent event) -> {
                                EquipmentRow equipmentRow = getTableView().getItems().get(getIndex());

                                filteredEquipments.setPredicate(equipment2 -> equipment2.equals(equipmentRow));
                                tvEquipment.setItems(filteredEquipments);

                                eq = equipmentRow.getEquipment();

                                eq.setClinicId(clinic.getClinicId());
                                try {
                                    Session.getClient().updateEquipment(eq);
                                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("doctorEquipment.fxml")));
                                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
                                setGraphic(actionButton);
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
                    setGraphic(transferButton);
                }
            }

        });


    }

    @FXML
    void btnEquipmentClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("doctorEquipment.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

    @FXML
    void btnCancelClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("doctorEquipment.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnConfirmClicked(ActionEvent event) throws IOException, InterruptedException {
        if (Objects.equals(btnConfirm.getText(), "Edit")) {
            eq.setName(tfEName.getText());
            eq.setEquipmentStatus(EquipmentStatus.valueOf(cboxStatus.getValue().toString().toUpperCase(Locale.ROOT)));
            Session.getClient().updateEquipment(eq);
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("doctorEquipment.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Session.getClient().addEquipment(new Equipment(tfEName.getText(), EquipmentStatus.IN_USE, clin.getClinicId()));
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("doctorEquipment.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    public static class EquipmentRow {
        private final String name;

        private final String status;

        private final String clinicName;

        private final Clinic clinic;
        private final Equipment equipment;


        public EquipmentRow(Clinic clinic, Equipment equipment) {

            this.name = equipment.getName();
            this.clinicName = clinic.getName();
            this.status = equipment.getEquipmentStatus().toString();
            this.clinic = clinic;
            this.equipment = equipment;

        }

        public String getName() {
            return name;
        }

        public String getStatus() {
            return status;
        }

        public String getClinicName() {
            return clinicName;
        }

        public Clinic getClinic() {
            return clinic;
        }

        public Equipment getEquipment() {
            return equipment;
        }
    }

}
