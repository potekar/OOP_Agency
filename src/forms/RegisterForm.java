package forms;

import data.Client;
import data.Database;
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
import java.nio.channels.IllegalBlockingModeException;
import java.sql.SQLException;

public class RegisterForm {
    private static Scene scene;
    private static Database database;
    private TextField tfUsername = new TextField();
    private PasswordField pfPassword = new PasswordField();
    private PasswordField pfPasswordCheck = new PasswordField();
    private TextField tfName=new TextField();
    private TextField tfLame=new TextField();
    private TextField tfPhone=new TextField();
    private TextField jmbg=new TextField();
    private TextField tfAccount=new TextField();
    private Label lbError=new Label();
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
        pfPasswordCheck.setPromptText("Repeat password");


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
        lbError.setStyle("-fx-text-fill: #e44744;-fx-font-size: 10pt");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll( imageView,tfName,tfLame,tfPhone,jmbg,tfAccount,tfUsername, pfPassword,pfPasswordCheck,lbError, btnRegister, btnCancel, btnLogin);

        scene = new Scene(vbox, 300, 750);
        scene.getStylesheets().add(getClass().getResource("/style/gui.css").toExternalForm());
    }

    public static Scene getRegisterForm(Database db,Stage primaryStage)
    {
        RegisterForm registerForm=new RegisterForm(db,primaryStage);
        return scene;
    }

    private void register() throws SQLException {

        if ( !tfName.getText().matches("[A-Za-z]+"))
            lbError.setText("Name input is incorrect");

        else if (!tfLame.getText().matches("[A-Za-z]+"))
            lbError.setText("Last name input is incorrect");

        else if (!tfPhone.getText().matches("[0-9]{9}"))
            lbError.setText("Phone number is incorrect");

        else if(!jmbg.getText().matches("[0-9]{13}"))
            lbError.setText("JBMG is incorrect");

        else if (!tfAccount.getText().matches("[0-9]{16}"))
            lbError.setText("Account number is incorrect");

        else if(!tfUsername.getText().matches("[A-Za-z]+"))
            lbError.setText("Username contains invalid chars");

        else if (pfPassword.getText().length()<8)
            lbError.setText("Password must be at least 8 chars");
        else if(
                tfName.getText().matches("[A-Za-z]+") &&
                tfLame.getText().matches("[A-Za-z]+") &&
                tfPhone.getText().matches("[0-9]{9}") &&
                jmbg.getText().matches("[0-9]{13}") &&
                tfAccount.getText().matches("[0-9]{16}") &&
                tfUsername.getText().matches("[A-Za-z]+") &&
                pfPassword.getText().length()>=8
        ){
            if (!database.searchUsername(tfUsername.getText()))
            {
                if (pfPassword.getText().equals(pfPasswordCheck.getText()))
                {
                    if (database.searchBank(jmbg.getText(),tfAccount.getText()))
                    {
                        lbError.setStyle("-fx-text-fill: #88c877;");
                        lbError.setText("Registration complete!");

                        Client client=new Client(0,tfName.getText(),tfLame.getText(),tfPhone.getText(),jmbg.getText(),tfAccount.getText(),tfUsername.getText(),pfPassword.getText());
                        database.register(client);

                        try {
                            Thread.sleep(2000);
                            try {
                                primaryStage.setScene(LoginForm.getLoginForm());
                            } catch (FileNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                            primaryStage.show();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else
                    {
                        lbError.setText("Account number does not exist");
                    }
                }
                else
                {
                    lbError.setText("Passwords must match");
                }
            }
            else
            {
                lbError.setText("Username already taken");
            }
        }
        else
            lbError.setText("Incorrect input");
    }
}
