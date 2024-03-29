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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.ui.Session;
import src.users.Permissions;
import src.users.User;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AdminUsersController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private User userMain;


    @FXML
    private TableColumn<?, ?> tcCity;

    @FXML
    private TableColumn<?, ?> tcEmail;

    @FXML
    private TableColumn<User, Void> tcModify;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TableColumn<?, ?> tcPermission;

    @FXML
    private TableColumn<?, ?> tcSurname;

    @FXML
    private TableView<User> tvUser;

    @FXML
    private AnchorPane anchorPane1;

    @FXML
    private Button btnCancel;

    @FXML
    private Text textTitle;

    @FXML
    private Text textName;

    @FXML
    private Text textSurname;


    @FXML
    private Text tFaliure;


    @FXML
    private Button btnConfirm;

    @FXML
    private ComboBox<String> cboxPermission;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfSurname;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnCancel.setVisible(false);
        tFaliure.setVisible(false);
        btnConfirm.setVisible(false);
        btnCancel.setVisible(false);
        cboxPermission.setVisible(false);

        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tcCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcPermission.setCellValueFactory(new PropertyValueFactory<>("prettyPermissions"));
        ArrayList<User> users = new ArrayList<User>();
        try {
            users = Session.getClient().getUsers();
        } catch (Exception e) {
            System.out.println("Error");
        }

        tvUser.getItems().addAll(users);

        FilteredList<User> filteredUsers = new FilteredList<>(tvUser.getItems(), b -> true);

        tfSurname.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUsers.setPredicate(user -> {
                if (newValue.isEmpty() || newValue.isBlank()) return user.getName().contains(tfName.getText());
                return user.getSurname().contains(newValue) && user.getName().contains(tfName.getText());
            });
            SortedList<User> sortedUsers = new SortedList<>(filteredUsers);
            sortedUsers.comparatorProperty().bind(tvUser.comparatorProperty());
            tvUser.setItems(sortedUsers);

        });
        tfName.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUsers.setPredicate(user -> {
                if (newValue.isEmpty() || newValue.isBlank()) return user.getSurname().contains(tfSurname.getText());
                return user.getName().contains(newValue) && user.getSurname().contains(tfSurname.getText());
            });
            SortedList<User> sortedUsers = new SortedList<>(filteredUsers);
            sortedUsers.comparatorProperty().bind(tvUser.comparatorProperty());
            tvUser.setItems(sortedUsers);
        });


        tcModify.setCellFactory(tableColumn -> new TableCell<>() {
            private final Button modifyButton = new Button("Edit");

            {
                modifyButton.setOnAction((ActionEvent event) -> {
                    btnCancel.setVisible(true);
                    modifyButton.setVisible(false);
                    tcModify.setVisible(false);
                    User user = getTableView().getItems().get(getIndex());
                    textTitle.setText("Edit Permission");
                    btnConfirm.setVisible(true);
                    btnCancel.setVisible(true);
                    cboxPermission.getItems().clear();
                    cboxPermission.getItems().addAll(
                            "Admin",
                            "Moderator",
                            "Doctor",
                            "Patient"
                    );
                    cboxPermission.setValue("Patient");
                    cboxPermission.setVisible(true);

                    textName.setVisible(false);
                    textSurname.setVisible(false);
                    tfName.setVisible(false);
                    tfSurname.setVisible(false);

                    filteredUsers.setPredicate(user2 -> user2.equals(user));
                    tvUser.setItems(filteredUsers);

                    userMain = user;


                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(modifyButton);
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
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminUsers.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnConfirmClicked(ActionEvent event) throws IOException, InterruptedException {
        userMain.setPermissions(getEnumPermission(cboxPermission.getValue()));
        Session.getClient().updateUser(userMain);
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminUsers.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public Permissions getEnumPermission(String permissions) {
        return Permissions.valueOf(permissions.toUpperCase(Locale.ROOT));
    }
}
