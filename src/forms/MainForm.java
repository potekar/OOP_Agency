package forms;

import data.Database;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainForm {
    private static Scene scene;
    static Stage primaryStage;
    private static Database database;

    private String[] set=new String[4];
    public MainForm(Database database,Stage primaryStage,String[] set)
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

        Label lbName=new Label("  "+set[2]);
        Label lbLastName=new Label(""+set[3]);
        Label lbUsername=new Label("   "+set[1]);
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

//        -----------------------------------------------------------------------------

//        TextArea taInfo=new TextArea("");
//        taInfo.setPrefSize(700,200);

        TableView table=new TableView();
        TableColumn ID=new TableColumn("ID");
        TableColumn name=new TableColumn("Name");
        TableColumn destination=new TableColumn("Destination");
        TableColumn departureDate=new TableColumn("Departure");
        TableColumn returnDate=new TableColumn("Return");
        TableColumn price=new TableColumn("Price");
        table.getColumns().addAll(ID,name,destination,departureDate,returnDate,price);

        ID.setResizable(false);
        name.setResizable(false);
        destination.setResizable(false);
        departureDate.setResizable(false);
        returnDate.setResizable(false);
        price.setResizable(false);

        //table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ID.prefWidthProperty().bind(table.widthProperty().divide(6));
        name.prefWidthProperty().bind(table.widthProperty().divide(6));
        destination.prefWidthProperty().bind(table.widthProperty().divide(6));
        departureDate.prefWidthProperty().bind(table.widthProperty().divide(6));
        returnDate.prefWidthProperty().bind(table.widthProperty().divide(6));
        price.prefWidthProperty().bind(table.widthProperty().divide(6));
        table.setPrefSize(700,300);

        vbRightNav.getChildren().addAll(table);
//        -----------------------------------------------------------------------------

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
