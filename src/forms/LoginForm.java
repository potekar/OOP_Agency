package forms;

import FXController.Controller;
import data.Admin;
import data.Client;
import data.Database;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class LoginForm {
    private static Scene scene;
    private static  Database database;
    private TextField tfUsername = new TextField();
    private PasswordField pfPassword = new PasswordField();
    private Label lbError=new Label();
    static Stage primaryStage;

    static public String[] set;
    Controller con=new Controller();
    public LoginForm(Database database, Stage primaryStage) throws FileNotFoundException{

        this.database=database;
        this.primaryStage=primaryStage;


        Image image= new Image("/style/logo.png");
        ImageView imageView=new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);


        tfUsername.setPromptText("Username");
        pfPassword.setPromptText("Password");

        Button btnLogin = new Button("Login");
        Button btnCancel = new Button("Cancel");
        Button btnRegister = new Button("Register");

        btnLogin.setOnAction(e-> {
            try {
                checkLogin();
            } catch (SQLException ee) {
                throw new RuntimeException(ee);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        btnCancel.setOnAction(e -> System.exit(1));
        btnRegister.setOnAction(e -> {
            primaryStage.setScene(RegisterForm.getRegisterForm(database,primaryStage));
            primaryStage.show();
            System.out.println("clicked");
        });

        btnRegister.setStyle("-fx-background-color: #20242b; -fx-text-fill: #4184cf;");
        lbError.setStyle("-fx-text-fill: #e44744;-fx-font-size: 10pt");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll( imageView,tfUsername, pfPassword, lbError,btnLogin, btnCancel, btnRegister);

        scene = new Scene(vbox, 300, 450);
        scene.getStylesheets().add(getClass().getResource("/style/gui.css").toExternalForm());
    }

    public static Scene getLoginForm() throws FileNotFoundException
    {
        LoginForm loginForm=new LoginForm(database,primaryStage);
        return scene;
    }

    private void checkLogin() throws SQLException, IOException {
         set=database.login(tfUsername.getText(),pfPassword.getText());

        if (set==null)
        {
            lbError.setText("Incorrect ussername or password");
        }
        else
        {
            lbError.setText("");
            if (set[0].equals("admin"))
            {
                AdminForm adminForm=new AdminForm(database,primaryStage,set);

                if(pfPassword.getText().equals("12345678"))
                {
                    adminForm.adminFirstLogin(tfUsername.getText());
                }
                else {
                    Admin.setActiveAdmin(database.getActiveAdmin(tfUsername.getText()));
                    FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("/FXController/adminScene.fxml"));
                    Scene sc = new Scene(fxmlLoader.load());
                    sc.getStylesheets().add(getClass().getResource("/style/guiTest.css").toExternalForm());
                    primaryStage.setScene(sc);
                    primaryStage.show();
                }
            }

            if (set[0].equals("user"))
            {
                Client.activeUser=database.getActiveClient(tfUsername.getText());
                FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("/FXController/clientScene.fxml"));
                Scene sc=new Scene(fxmlLoader.load());
                sc.getStylesheets().add(getClass().getResource("/style/guiTest.css").toExternalForm());
                primaryStage.setScene(sc);
                primaryStage.show();
            }
        }

    }
}


