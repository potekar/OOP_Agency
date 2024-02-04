package data;

import FXController.Controller;
import forms.CommonForm;
import forms.LoginForm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
public class Main extends Application{
    static Database database=new Database();
    public static void main(String[] args) throws SQLException {

       database.DBConnect();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        LoginForm log=new LoginForm(database,stage);
        CommonForm com=new CommonForm(database,stage);

//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/FXController/clientScene.fxml"));

//        Scene sc=new Scene(fxmlLoader.load());
        Scene sc=LoginForm.getLoginForm();
        sc.getStylesheets().add(getClass().getResource("/style/guiTest.css").toExternalForm());

        stage.setTitle("Agency...");
        stage.getIcons().add(new Image("/style/logo.png"));
        stage.setScene(sc);
        stage.show();
    }
}