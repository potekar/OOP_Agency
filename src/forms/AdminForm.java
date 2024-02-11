package forms;

import data.Admin;
import data.Database;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AdminForm {
    private static Scene scene;
    static Stage primaryStage;
    private static Database database;
    private String[] set=new String[4];
    public AdminForm(Database database, Stage primaryStage,String[] set)
    {
        this.database=database;
        this.primaryStage= primaryStage;
        this.set=set;
    }

    //-----------------------------------------------------------------------------
    public Scene getMainForm()
    {
        return scene;
    }
    //-----------------------------------------------------------------------------

    public void adminFirstLogin(String username)
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
        Button btnCancel = new Button("Cancel");;

        btnLogin.setOnAction(e-> {
            if(pfPassword.getText().equals(pfPasswordCheck.getText()))
            {
                if(database.changeAdminPassword(username,pfPassword.getText()))
                {
                    System.out.println("done");
                    Admin.setActiveAdmin(database.getActiveAdmin(username));
                    FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("/FXController/adminScene.fxml"));
                    Scene sc = null;
                    try {
                        sc = new Scene(fxmlLoader.load());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    sc.getStylesheets().add(LoginForm.class.getResource("/style/guiTest.css").toExternalForm());
                    primaryStage.setScene(sc);
                    primaryStage.show();
                }
            }
        });
        btnCancel.setOnAction(e -> System.exit(1));


        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll( imageView, pfPassword, pfPasswordCheck,btnLogin, btnCancel);

        scene = new Scene(vbox, 300, 450);
        scene.getStylesheets().add(getClass().getResource("/style/gui.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
