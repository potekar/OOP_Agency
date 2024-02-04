package forms;

import data.Database;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class CommonForm {
    private static Scene scene;
    static Stage primaryStage;
    private static Database database;

    private String[] set=new String[4];
    public CommonForm(Database database, Stage primaryStage)
    {
        this.database=database;
        this.primaryStage= primaryStage;
    }

    public static Scene changePassword(String ussername)
    {
        Label lbError=new Label();
        PasswordField pfPassword = new PasswordField();
        PasswordField pfPasswordCheck = new PasswordField();
        Image image= new Image("/style/logo.png");
        ImageView imageView=new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        lbError.setStyle("-fx-text-fill: #e44744;-fx-font-size: 10pt");
        pfPassword.setPromptText("Password");
        pfPasswordCheck.setPromptText("Repeat password");

        Button btnLogin = new Button("Change");
        Button btnCancel = new Button("Back");;

        btnLogin.setOnAction(e-> {
            if(pfPassword.getText().equals(pfPasswordCheck.getText()))
            {
                if(database.changeClientPassword(ussername,pfPassword.getText()))
                {
                    System.out.println("password changed");
                }
            }
            else
            {
                lbError.setText("Passwords must match");
            }
        });
        btnCancel.setOnAction(e ->{
            FXMLLoader fxmlLoader = new FXMLLoader(CommonForm.class.getResource("/FXController/clientScene.fxml"));
            try {
                scene=new Scene(fxmlLoader.load());
                primaryStage.setScene(scene);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll( imageView, pfPassword, pfPasswordCheck,btnLogin, btnCancel);

        scene = new Scene(vbox, 300, 450);
        scene.getStylesheets().add(CommonForm.class.getResource("/style/gui.css").toExternalForm());

        return scene;
    }

    public static Scene getMainForm()
    {
        return scene;
    }
}
