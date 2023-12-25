package forms;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginForm {
    private static Scene scene;
    public LoginForm() {
        TextField tfUsername = new TextField();
        PasswordField pfPassword = new PasswordField();
        tfUsername.setPromptText("Username");
        pfPassword.setPromptText("Password");

        Button btnLogin = new Button("Login");
        Button btnCancel = new Button("Cancel");
        Button btnRegister = new Button("Register");

        btnLogin.setOnAction(e -> System.out.println("Login button clicked"));
        btnCancel.setOnAction(e -> System.exit(1));
        btnRegister.setOnAction(e -> System.out.println("reg"));
        btnRegister.setStyle("-fx-background-color: #20242b; -fx-text-fill: #4184cf;");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll( tfUsername, pfPassword, btnLogin, btnCancel, btnRegister);

        scene = new Scene(vbox, 300, 400);
        scene.getStylesheets().add(getClass().getResource("/style/gui.css").toExternalForm());
    }

    public static Scene getLoginForm()
    {
        return scene;
    }
}


