package forms;

import data.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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

    public static Scene changePassword(String username,String role)
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
                if(role.equals("client"))
                {
                    if(database.changeClientPassword(username,pfPassword.getText()))
                    {
                        lbError.setText("Password changed");
                        lbError.setStyle("-fx-text-fill: #88c877");
                        System.out.println("password changed");
                    }
                }

                if(role.equals("admin"))
                {
                    if(database.changeAdminPassword(username,pfPassword.getText()))
                    {
                        lbError.setText("Password changed");
                        lbError.setStyle("-fx-text-fill: #88c877");
                        System.out.println("password changed");
                    }
                }

            }
            else
            {
                lbError.setText("Passwords must match");
            }
        });
        btnCancel.setOnAction(e ->{
            if(role.equals("admin"))
            {
                FXMLLoader fxmlLoader = new FXMLLoader(CommonForm.class.getResource("/FXController/adminScene.fxml"));
                try {
                    scene=new Scene(fxmlLoader.load());
                    primaryStage.setScene(scene);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if (role.equals("client"))
            {
                FXMLLoader fxmlLoader = new FXMLLoader(CommonForm.class.getResource("/FXController/clientScene.fxml"));
                try {
                    scene=new Scene(fxmlLoader.load());
                    primaryStage.setScene(scene);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });


        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll( imageView, pfPassword, pfPasswordCheck,btnLogin, btnCancel,lbError);

        scene = new Scene(vbox, 300, 450);
        scene.getStylesheets().add(CommonForm.class.getResource("/style/gui.css").toExternalForm());

        return scene;
    }

    public static Scene getMainForm()
    {
        return scene;
    }

    public static void getTripInfo(String id)
    {
        Arrangment arg;
        List<Arrangment> list=database.getArrangements();
        arg = list.stream().filter(a -> a.getTripName().equals(id)).findFirst().orElse(null);

        Image image= new Image("/style/logo.png");
        ImageView imageView=new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        System.out.println(LocalDate.now().isBefore(LocalDate.parse(arg.getDepartureDate())));
        if(arg.getReturnDate().equals(arg.getDepartureDate()))
        {


            Label lblName=new Label("Trip name: "+arg.getTripName());
            Label lblDestiantion=new Label("Destination: "+arg.getDestination());
            Label lblTransport=new Label("Transport Type: "+arg.getTransportation());
            Label lblDeparture=new Label("Departure date: "+arg.getDepartureDate());
            Label lblPrice=new Label("Price: "+String.valueOf(arg.getArrangmentPrice()));
            Label lblError=new Label();

            PasswordField pfPassword=new PasswordField();
            pfPassword.setPromptText("Password");

            Button btnReserve = new Button("Add reservation");
            btnReserve.setStyle("-fx-pref-width: 250");

            VBox vbox=new VBox(15);
            VBox logo=new VBox(15);
            VBox buttons=new VBox(15);

            vbox.setPadding(new Insets(10));
            buttons.setPadding(new Insets(10));

            logo.setAlignment(Pos.CENTER);
            buttons.setAlignment(Pos.CENTER);
            vbox.setAlignment(Pos.TOP_LEFT);
            logo.getChildren().add(imageView);
            buttons.getChildren().addAll(pfPassword,btnReserve,lblError);
            vbox.getChildren().addAll(logo,lblName,lblDestiantion,lblTransport,lblDeparture,lblPrice,buttons);

            btnReserve.setOnAction(e-> {
                if(pfPassword.getText().equals(Client.getActiveUser().getPassword())) {
                    if (LocalDate.now().isBefore(LocalDate.parse(arg.getDepartureDate()))) {
                        if (database.getFounds(Client.getActiveUser().getAccountNumber()) > arg.getArrangmentPrice() / 2) {
                            List<Reservation> reservations = database.getClientReservations(Client.getActiveUser());
                            for (Reservation reservation : reservations) {
                                if (reservation.getArrangementId().equals(arg.getId()) && reservation.getPaidPrice() < 0) {
                                    database.deleteReservation(Client.getActiveUser(), arg.getId());
                                    break;
                                }
                            }
                            database.addReservation(Client.getActiveUser(), arg.getTripName());

                            lblError.setText("Reservation added successfully,\n new account balance is " + database.getFounds(Client.getActiveUser().getAccountNumber()));
                            lblError.setStyle("-fx-text-fill: #88c877");
                        } else {
                            lblError.setText("Insufficient founds!");
                            lblError.setStyle("-fx-text-fill: #e44744");
                        }
                    }
                    else
                    {
                        lblError.setText("Reservation is no longer available.");
                        lblError.setStyle("-fx-text-fill: #e44744");
                    }
                }
                else
                {
                    lblError.setText("Incorrect password");
                    lblError.setStyle("-fx-text-fill: #e44744");
                }

            });

            Stage s=new Stage();
            s.setResizable(false);
            Scene sc=new Scene(vbox,360,540);
            sc.getStylesheets().add(CommonForm.class.getResource("/style/gui.css").toExternalForm());

            s.setScene(sc);
            s.show();

        }
        else
        {
            Accommodation accommodation=database.getAccomodation(arg.getAccommodationID());
            Label lblName=new Label("Trip name: "+arg.getTripName());
            Label lblDestination=new Label("Destination: "+arg.getDestination());
            Label lblTransport=new Label("Transport Type: "+arg.getTransportation());
            Label lblDeparture=new Label("Departure date: "+arg.getDepartureDate());
            Label lblReturn=new Label("Return date: "+arg.getReturnDate());
            Label lblPrice=new Label("Price: "+String.valueOf(arg.getArrangmentPrice()));
            Label lblPricePer=new Label("Price per night: "+String.valueOf(accommodation.getPricePerNight()));
            Label lblStars=new Label("# of stars: "+String.valueOf(accommodation.getNumberOfStars()));
            Label lblHotel=new Label("Hotel: "+accommodation.getName());
            Label lblRoomType=new Label("Room type: "+accommodation.getRoomType());
            Label lblError=new Label();


            PasswordField pfPassword=new PasswordField();
            pfPassword.setPromptText("Password");

            Button btnReserve = new Button("Add reservation");
            btnReserve.setStyle("-fx-pref-width: 250");

            VBox vbox=new VBox(15);
            VBox logo=new VBox(15);
            VBox buttons=new VBox(15);

            vbox.setPadding(new Insets(10));
            buttons.setPadding(new Insets(10));

            logo.setAlignment(Pos.CENTER);
            buttons.setAlignment(Pos.CENTER);
            vbox.setAlignment(Pos.TOP_LEFT);
            logo.getChildren().add(imageView);
            buttons.getChildren().addAll(pfPassword,btnReserve,lblError);
            vbox.getChildren().addAll(logo,lblName,lblDestination,lblTransport,lblDeparture,lblReturn,lblPrice,lblPricePer,lblStars,lblHotel,lblRoomType,buttons);

            btnReserve.setOnAction(e-> {

                if(pfPassword.getText().equals(Client.getActiveUser().getPassword())) {
                    if (LocalDate.now().isBefore(LocalDate.parse(arg.getDepartureDate()))) {

                        if (database.getFounds(Client.getActiveUser().getAccountNumber()) > arg.getArrangmentPrice() / 2) {
                            List<Reservation> reservations = database.getClientReservations(Client.getActiveUser());
                            for (Reservation reservation : reservations) {
                                if (reservation.getArrangementId().equals(arg.getId()) && reservation.getPaidPrice() < 0) {
                                    database.deleteReservation(Client.getActiveUser(), arg.getId());
                                    break;
                                }
                            }
                            database.addReservation(Client.getActiveUser(), arg.getTripName());

                            lblError.setText("Reservation added successfully,\n new account balance is " + database.getFounds(Client.getActiveUser().getAccountNumber()));
                            lblError.setStyle("-fx-text-fill: #88c877");
                        } else {
                            lblError.setText("Insufficient founds!");
                            lblError.setStyle("-fx-text-fill: #e44744");
                        }
                    }
                    else
                    {
                        lblError.setText("Reservation is no longer available.");
                        lblError.setStyle("-fx-text-fill: #e44744");
                    }
                }
                else
                {
                    lblError.setText("Incorrect password");
                    lblError.setStyle("-fx-text-fill: #e44744");
                }
            });

            Stage s=new Stage();
            s.setResizable(false);
            Scene sc=new Scene(vbox,360,750);
            sc.getStylesheets().add(CommonForm.class.getResource("/style/gui.css").toExternalForm());

            s.setScene(sc);
            s.show();
        }

    }

    public static void payTrip(Reservation reservation)
    {
        Label lblError=new Label();
        if((reservation.getTotalPrice()-reservation.getPaidPrice())!=0)
        {
            Image image= new Image("/style/logo.png");
            ImageView imageView=new ImageView(image);
            imageView.setFitHeight(80);
            imageView.setFitWidth(80);


            double max=reservation.getTotalPrice()-reservation.getPaidPrice();

            Label lblName=new Label("Max amount to pay: "+max);
            TextField tfMoney=new TextField();
            tfMoney.setPromptText("input the amount");

            PasswordField pfPassword=new PasswordField();
            pfPassword.setPromptText("Password");

            Button btnPay = new Button("Pay");
            btnPay.setStyle("-fx-pref-width: 250");

            VBox vbox=new VBox(15);
            VBox logo=new VBox(15);
            VBox buttons=new VBox(15);

            vbox.setPadding(new Insets(10));
            buttons.setPadding(new Insets(10));

            logo.setAlignment(Pos.CENTER);
            buttons.setAlignment(Pos.CENTER);
            vbox.setAlignment(Pos.CENTER);
            logo.getChildren().add(imageView);
            buttons.getChildren().addAll(tfMoney,pfPassword,btnPay,lblError);
            vbox.getChildren().addAll(logo,lblName,buttons);

            Stage s=new Stage();
            s.setResizable(false);
            Scene sc=new Scene(vbox,360,500);
            sc.getStylesheets().add(CommonForm.class.getResource("/style/gui.css").toExternalForm());

            s.setScene(sc);
            s.show();


            btnPay.setOnAction(e->
            {
                if(pfPassword.getText().equals(Client.getActiveUser().getPassword()) && tfMoney.getText().matches("[0-9]+")&&Double.parseDouble(tfMoney.getText())<=max)
                {
                    if(database.getFounds(Client.getActiveUser().getAccountNumber())>reservation.getTotalPrice()-reservation.getPaidPrice())
                    {
                        if(database.processPayment(reservation,Client.getActiveUser(),Double.parseDouble(tfMoney.getText())));
                        {
                            lblError.setText("Payment successful.\n new balance: "+database.getFounds(Client.getActiveUser().getAccountNumber()));
                            lblError.setStyle("-fx-text-fill: #79e444");
                        }
                    }
                    else
                    {
                        lblError.setText("Insufficient founds");
                        lblError.setStyle("-fx-text-fill: #e44744");
                    }
                }
                else
                {
                    lblError.setText("Incorrect password \nor amount of money");
                    lblError.setStyle("-fx-text-fill: #e44744");
                }
            });
        }

        else
        {
            Alert messsage=new Alert(Alert.AlertType.INFORMATION,"Trip already paid",ButtonType.CLOSE);
            messsage.showAndWait();
        }

    }
}

