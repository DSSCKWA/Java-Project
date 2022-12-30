package src.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.ui.login.LoginController;

import java.util.Objects;

public class MainUI extends Application {

    @Override
    public void start(Stage stage){
try {
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login/login.fxml")));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setResizable(false);
    stage.setTitle("Login Page");
    stage.show();
}catch(Exception e) {
    e.printStackTrace();
}
    }

    public static void main(String[] args) {Application.launch(args);}
}
