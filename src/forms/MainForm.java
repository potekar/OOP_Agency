package forms;

import com.mysql.cj.protocol.x.XProtocolRowInputStream;
import data.Database;
import data.Main;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.sql.Statement;
import java.time.LocalDate;
import data.Client;

public class MainForm {
    private static Scene scene;
    static Stage primaryStage;
    private static Database database;
    public MainForm(Database database,Stage primaryStage)
    {
        this.database=database;
        this.primaryStage= primaryStage;

        Image image= new Image("/style/logo.png");
        ImageView imageView=new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);

        BorderPane root =new BorderPane();
        VBox vbLeftNav=new VBox(20);
        VBox vbRightNav=new VBox();

        Label lbName=new Label("  Mile");
        Label lbLastName=new Label("Milic");
        Label lbUsername=new Label("   milojca12");
        lbUsername.setStyle("-fx-font-size: 10pt;-fx-text-fill:#284474;");


        VBox info=new VBox(1);
        HBox inerinfo=new HBox(5);
        inerinfo.getChildren().addAll(lbName,lbLastName);
        info.getChildren().addAll(inerinfo,lbUsername);

        Button btnReservation=new Button("Reservations");
        Button btnNewReservation=new Button("New Reservation");
        btnReservation.setAlignment(Pos.BASELINE_LEFT);
        btnNewReservation.setAlignment(Pos.BASELINE_LEFT);
        vbLeftNav.setPrefSize(200,600);

        vbLeftNav.getChildren().addAll(imageView,info,btnReservation,btnNewReservation);
        root.setLeft(vbLeftNav);
        root.setRight(vbRightNav);
        root.setStyle("-fx-background-color: #20242b;");
        scene=new Scene(root,1000,600);
        scene.getStylesheets().add(getClass().getResource("/style/guiMain.css").toExternalForm());
    }


    public static Scene getMainForm()
    {
        return scene;
    }
}
