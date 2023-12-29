package data;

import forms.LoginForm;
import forms.MainForm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;

public class Main extends Application{
    static Database database=new Database();
    public static void main(String[] args) throws SQLException {

        database.DBConnect();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNDECORATED);

        //LoginForm login=new LoginForm(database,primaryStage);
        MainForm main=new MainForm(database,primaryStage);
        LoginForm log=new LoginForm(database,primaryStage);
        Scene loginscene=log.getLoginForm();

        primaryStage.setScene(loginscene);
        primaryStage.show();
    }
}