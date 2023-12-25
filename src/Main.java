import forms.LoginForm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;

public class Main extends Application{
    public static void main(String[] args) throws SQLException {
        Database database=new Database();
        database.DBConnect();
        database.login("marko","marko123");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNDECORATED);

        LoginForm login=new LoginForm();
        Scene loginscene=login.getLoginForm();

        primaryStage.setScene(loginscene);
        primaryStage.show();
    }
}