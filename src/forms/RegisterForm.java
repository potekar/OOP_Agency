package forms;

import data.Client;
import data.Database;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class RegisterForm {
    private static Scene scene;
    private static Database database;
    private TextField tfUsername = new TextField();
    private PasswordField pfPassword = new PasswordField();
    private TextField tfName=new TextField();
    private TextField tfLame=new TextField();
    private TextField tfPhone=new TextField();
    private TextField jmbg=new TextField();
    private TextField tfAccount=new TextField();
    static  Stage primaryStage;

    public RegisterForm (Database database, Stage primaryStage){
        this.database=database;
        this.primaryStage=primaryStage;

        Image image= new Image("/style/logo.png");
        ImageView imageView=new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);

        tfName.setPromptText("First Name");
        tfLame.setPromptText("Last Name");
        tfPhone.setPromptText("Phone number");
        jmbg.setPromptText("JMBG");
        tfAccount.setPromptText("Bank Account");
        tfUsername.setPromptText("Username");
        pfPassword.setPromptText("Password");



        Button btnRegister = new Button("Register");
        Button btnCancel = new Button("Cancel");
        Button btnLogin = new Button("Login");

        btnRegister.setOnAction(e-> {
            try {
                register();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        btnCancel.setOnAction(e -> System.exit(1));
        btnLogin.setOnAction(e -> {
            try {
                primaryStage.setScene(LoginForm.getLoginForm());
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            primaryStage.show();
        });
        btnLogin.setStyle("-fx-background-color: #20242b; -fx-text-fill: #4184cf;");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll( imageView,tfName,tfLame,tfPhone,jmbg,tfAccount,tfUsername, pfPassword, btnRegister, btnCancel, btnLogin);

        scene = new Scene(vbox, 300, 700);
        scene.getStylesheets().add(getClass().getResource("/style/gui.css").toExternalForm());
    }

    public static Scene getRegisterForm(Database db,Stage primaryStage)
    {
        RegisterForm registerForm=new RegisterForm(db,primaryStage);
        return scene;
    }

    private void register() throws SQLException {
        
        if(
                tfName.getText().matches("[A-Za-z]+") &&
                tfLame.getText().matches("[A-Za-z]+") &&
                tfPhone.getText().matches("[0-9]{9}") &&
                jmbg.getText().matches("[0-9]{13}") &&
                tfAccount.getText().matches("[0-9]{16}") &&
                tfUsername.getText().matches("[A-Za-z]+") &&
                pfPassword.getText().length()>8
        ){
            if (!database.searchUsername(tfUsername.getText()))
            {
                Client client=new Client(tfName.getText(),tfLame.getText(),tfPhone.getText(),jmbg.getText(),tfAccount.getText(),tfUsername.getText(),pfPassword.getText());
                database.register(client);
            }
            else
            {
                System.out.println("Username already taken");
            }
        }
        else
            System.out.println("jedno ili vise polja nema ispravan unos");
    }
}
