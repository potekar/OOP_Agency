package forms;

import data.Database;
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
//        -----------------------------------------------------------------------------
        Image image= new Image("/style/logo.png");
        ImageView imageView=new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);

        BorderPane root =new BorderPane();
        VBox vbLeftNav=new VBox(20);
        VBox vbRightNav=new VBox();

        Label lbName=new Label(" "+set[2]);
        Label lbLastName=new Label(" "+set[3]);
        Label lbUsername=new Label("   "+set[1]);
        lbUsername.setStyle("-fx-font-size: 10pt;-fx-text-fill:#284474;");


        vbLeftNav.setPrefSize(200,600);

        vbLeftNav.getChildren().addAll(imageView);

//        -----------------------------------------------------------------------------

//
//        -----------------------------------------------------------------------------

        root.setLeft(vbLeftNav);
        root.setRight(vbRightNav);
        root.setStyle("-fx-background-color: #20242b;");
        scene=new Scene(root,1000,600);
        scene.getStylesheets().add(getClass().getResource("/style/guiMain.css").toExternalForm());
    }

    //-----------------------------------------------------------------------------
    public Scene getMainForm()
    {
        return scene;
    }
    //-----------------------------------------------------------------------------

    public void adminFirstLogin()
    {
        Label lbError=new Label();
        TextField tfUsername = new TextField();
        PasswordField pfPassword = new PasswordField();
        PasswordField pfPasswordCheck = new PasswordField();
        Image image= new Image("/style/logo.png");
        ImageView imageView=new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        lbError.setStyle("-fx-text-fill: #e44744;-fx-font-size: 10pt");
        tfUsername.setPromptText("Username");
        pfPassword.setPromptText("Password");

        Button btnLogin = new Button("Login");
        Button btnCancel = new Button("Cancel");;

        btnLogin.setOnAction(e-> {
            if(pfPassword.getText().equals(pfPasswordCheck.getText()))
            {
                if(database.changePassword(tfUsername.getText(),pfPassword.getText()))
                {
                    System.out.println("done");
                    AdminForm adminForm=new AdminForm(database,primaryStage,set);
                    adminForm.getMainForm();
                }

            }
        });
        btnCancel.setOnAction(e -> System.exit(1));


        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll( imageView,tfUsername, pfPassword, pfPasswordCheck,btnLogin, btnCancel);

        scene = new Scene(vbox, 300, 450);
        scene.getStylesheets().add(getClass().getResource("/style/gui.css").toExternalForm());
    }

}
