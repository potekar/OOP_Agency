package FXController;

import data.*;
import forms.CommonForm;
import forms.LoginForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Controller implements Initializable {

    private Scene scene;
    private Stage stage;
    private Database database;

    private final String RED = "#e44744";
    private final String GREEN = "#88c877";


    //-------------------------------------FXML-----------------------------------------------
    @FXML
    private Button btnFilters;

    @FXML
    private Button btnNewReservation;

    @FXML
    private Button btnPasswordChange;

    @FXML
    private Button btnReservations;

    @FXML
    private DatePicker calArrival=new DatePicker();

    @FXML
    private DatePicker calDate=new DatePicker();

    @FXML
    private DatePicker calDepartureC=new DatePicker();

    @FXML
    private DatePicker calReturn=new DatePicker();

    @FXML
    private DatePicker calReturnC=new DatePicker();

    @FXML
    private ChoiceBox<String> cbRoomType = new ChoiceBox<>();

    @FXML
    private ChoiceBox<String> cbMStars = new ChoiceBox<>();

    @FXML
    private ChoiceBox<String> cbMTransport = new ChoiceBox<>();

    @FXML
    private ChoiceBox<String> cbRoomTypeC = new ChoiceBox<>();

    @FXML
    private ChoiceBox<String> cbMStarsC = new ChoiceBox<>();

    @FXML
    private ChoiceBox<String> cbMTransportC = new ChoiceBox<>();

    @FXML
    private Label lblAdminCount=new Label();

    @FXML
    private Label lblNameLname;

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblNewAdminMessage;

    @FXML
    private Label lblNewTripMessage;

    @FXML
    private Label lblMtripMessage;

    @FXML
    private Label lblAllTripsMessageOne;

    @FXML
    private Label lblAllTripsMessageMulti;

    @FXML
    private Label lblMonez = new Label();

    @FXML
    private Label lblMonezS = new Label();

    @FXML
    private Label lblDueTo = new Label();

    @FXML
    private Label lblDueToS = new Label();

    @FXML
    private Label lblClientBalance=new Label();

    @FXML
    Label lblInfo=new Label();

    @FXML
    private Slider sldPrice=new Slider();

    @FXML
    private TextField tfDestination;

    @FXML
    private TextField tfNewAdminName;

    @FXML
    private TextField tfNewAdminLname;

    @FXML
    private TextField tfNewAdminUsername;

    @FXML
    private TextField tfTripName;

    @FXML
    private TextField tfPrice;

    @FXML
    private TextField tfMName;

    @FXML
    private TextField tfMDestination;

    @FXML
    private TextField tfMHotel;

    @FXML
    private TextField tfPricePer;

    @FXML
    private ListView listAdminOne = new ListView<>();

    @FXML
    private ListView listAdminMulti = new ListView<>();

    @FXML
    private ListView listActive = new ListView<>();

    @FXML
    private ListView listPast = new ListView<>();

    @FXML
    private ListView listCanceled = new ListView<>();

    @FXML
    private ListView listOne = new ListView<>();

    @FXML
    private ListView listMulti = new ListView<>();


    //-------------------------------------SceneSwitcher-----------------------------------------------

    private Scene getFxmlScene(String name) {
        try {
            return new Scene(FXMLLoader.load(getClass().getResource(name)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToClientReservations(javafx.event.ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = getFxmlScene("clientReservations.fxml");
        scene.getStylesheets().add(getClass().getResource("/style/guiTest.css").toExternalForm());
        stage.setScene(scene);
    }

    public void switchToClientAllReservations(javafx.event.ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = getFxmlScene("clientNewReservation.fxml");
        scene.getStylesheets().add(getClass().getResource("/style/guiTest.css").toExternalForm());
        stage.setScene(scene);
    }

    public void switchToNewAdmin(javafx.event.ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = getFxmlScene("adminNew.fxml");
        scene.getStylesheets().add(getClass().getResource("/style/guiTest.css").toExternalForm());
        stage.setScene(scene);
    }

    public void switchToAdminAllTrips(javafx.event.ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = getFxmlScene("adminAllTrips.fxml");
        scene.getStylesheets().add(getClass().getResource("/style/guiTest.css").toExternalForm());
        stage.setScene(scene);
    }

    public void switchToAdminNewTrip(javafx.event.ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = getFxmlScene("adminNewTrip.fxml");
        scene.getStylesheets().add(getClass().getResource("/style/guiTest.css").toExternalForm());
        stage.setScene(scene);
    }

    public void changePassword(javafx.event.ActionEvent actionEvent) {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = CommonForm.changePassword(Client.getActiveUser().getUsername(), "client");
        stage.setScene(scene);
    }

    public void changePasswordAdmin(javafx.event.ActionEvent actionEvent) {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = CommonForm.changePassword(Admin.getActiveAdmin().getUsername(), "admin");
        stage.setScene(scene);
    }


    //-------------------------------------ButtonControls------------------------------------------------

    public void addNewAdmin() {
        if (!tfNewAdminUsername.getText().equals("") && !tfNewAdminName.getText().equals("") && !tfNewAdminLname.getText().equals("")) {
            if (database.addNewAdmin(tfNewAdminName.getText(), tfNewAdminLname.getText(), tfNewAdminUsername.getText())) {
                lblNewAdminMessage.setText("New admin added successfully");
                lblNewAdminMessage.setStyle("-fx-text-fill:" + GREEN);

                tfNewAdminLname.clear();
                tfNewAdminName.clear();
                tfNewAdminUsername.clear();
                System.out.println("added");
            } else {
                lblNewAdminMessage.setText("Username already taken");
                lblNewAdminMessage.setStyle("-fx-text-fill:" + RED);
                System.out.println("taken");
            }
        } else {
            lblNewAdminMessage.setText("Fill in all fields!");
            lblNewAdminMessage.setStyle("-fx-text-fill:" + RED);
        }

    }

    public void addTrip() {
        if (!tfTripName.getText().equals("") && !tfDestination.getText().equals("") && !tfPrice.getText().equals("") && calDate.getValue() != null) {
            if (database.addNewTrip(tfTripName.getText(), tfDestination.getText(), calDate.getValue().toString(), tfPrice.getText())) {
                tfTripName.clear();
                tfDestination.clear();
                tfPrice.clear();
                calDate.setValue(null);

                lblNewTripMessage.setText("Trip added successfully");
                lblNewTripMessage.setStyle("-fx-text-fill:" + GREEN);
            } else {
                lblNewTripMessage.setText("An error occured, try again");
                lblNewTripMessage.setStyle("-fx-text-fill:" + RED);
            }
        } else {
            lblNewTripMessage.setText("Fill in add fields!");
            lblNewTripMessage.setStyle("-fx-text-fill:" + RED);
        }

    }

    public void addMultiTrip() {
        String name = tfMName.getText();
        String destination = tfMDestination.getText();
        String transport = "";
        String hotel = tfMHotel.getText();
        String stars = "";
        String pricePer = tfPricePer.getText();
        String roomType = "";
        String arrivalDate = "";
        String returnDate = "";

        try {
            arrivalDate = calArrival.getValue().toString();
            returnDate = calReturn.getValue().toString();
            transport = cbMTransport.getSelectionModel().getSelectedItem().toString();
            stars = cbMStars.getSelectionModel().getSelectedItem().toString();
            roomType = cbRoomType.getSelectionModel().getSelectedItem().toString();
        } catch (NullPointerException e) {
            System.out.println("null");
        }

        ArrayList<String> fields = new ArrayList<>(Arrays.asList(name, destination, arrivalDate, returnDate, transport, hotel, stars, pricePer, roomType));

        if (checkForEntry(fields)) {
            if (pricePer.matches("[0-9]+")) {
                int price = Integer.parseInt(tfPricePer.getText()) * (int) ChronoUnit.DAYS.between(calArrival.getValue(), calReturn.getValue());

                if (database.addNewMultiTrip(name, destination, arrivalDate, returnDate, transport, hotel, stars, pricePer, roomType, Integer.toString(price))) {
                    lblMtripMessage.setText("Trip added successfully");
                    lblMtripMessage.setStyle("-fx-text-fill: #88c877");

                    tfMName.clear();
                    tfMDestination.clear();
                    tfPricePer.clear();
                    calArrival.setValue(null);
                    calReturn.setValue(null);
                    tfMHotel.clear();
                    cbMTransport.getSelectionModel().clearSelection();
                    cbMStars.getSelectionModel().clearSelection();
                    cbRoomType.getSelectionModel().clearSelection();
                }
            } else {
                lblMtripMessage.setText("Price per night must be a number");
                lblMtripMessage.setStyle("-fx-text-fill: #e44744");
            }
        } else {
            lblMtripMessage.setText("Fill in all fields!");
            lblMtripMessage.setStyle("-fx-text-fill: #e44744");
        }
    }


    //-------------------------------------QOLFunctions----------------------------------------------

    private <T> boolean checkForEntry(ArrayList<T> list) {
        for (T element : list) {
            if (element == null || element.equals(""))
                return false;
        }

        return true;
    }

    private void updateProfit() {
        lblMonez.setText(String.valueOf(database.getProfit()));
        lblMonezS.setText(String.valueOf(database.getProfit()));
        lblDueToS.setText(String.valueOf(database.getDueTo()));
        lblDueTo.setText(String.valueOf(database.getDueTo()));
    }

    private void updateBalance()
    {
        lblClientBalance.setText("Balance: "+database.getFounds(Client.getActiveUser().getAccountNumber()));
    }

    private void updateAdminCount()
    {
        lblAdminCount.setText(""+database.getNumberOfAdmins());
    }

    private String loginMessage()
    {
        StringBuilder msg=new StringBuilder();
        List<Reservation> list = database.getThreeDayLeft();

        if(list.size()>0)
        {
            msg.append("You have ").append(list.size()).append(" unpaid reservations:");
            for (Reservation r :list)
            {
                msg.append("\n").append(r.getArrangementId());
            }
        }
        return msg.toString();
    }


    //-------------------------------------TableControls----------------------------------------------

    private void updateAdminLists() {
        listAdminMulti.getItems().clear();
        listAdminOne.getItems().clear();
        List<Arrangment> list = database.getArrangements();

        for (Arrangment arrangment : list) {
            if (arrangment.getDepartureDate().equals(arrangment.getReturnDate())) {
                String entry = new String("Trip name: " + arrangment.getTripName() +
                        " - Destination: " + arrangment.getDestination() +
                        " - Date: " + arrangment.getDepartureDate() +
                        " - Price: " + arrangment.getArrangmentPrice());
                listAdminOne.getItems().add(entry);
            } else {
                String entry = new String("Trip name: " + arrangment.getTripName() +
                        " - Destination: " + arrangment.getDestination() +
                        " - Departure date: " + arrangment.getDepartureDate() +
                        " - Return date: " + arrangment.getReturnDate() +
                        " - Price: " + arrangment.getArrangmentPrice() +
                        " - Id: " + arrangment.getAccommodationID());
                listAdminMulti.getItems().add(entry);
            }
        }
    }

    public void deleteTripOne() {
        lblAllTripsMessageOne.setText("");
        if (listAdminOne.getSelectionModel().getSelectedItem() != null) {
            String[] entry = listAdminOne.getSelectionModel().getSelectedItem().toString().split(" -");
            String tripName = entry[0].substring(11);

            if (database.deleteTrip(tripName)) {
                lblAllTripsMessageOne.setText("Trip deleted");
                lblAllTripsMessageOne.setStyle("-fx-text-fill:" + GREEN);
                updateAdminLists();
            } else {
                lblAllTripsMessageOne.setText("An error occurred");
                lblAllTripsMessageOne.setStyle("-fx-text-fill:" + RED);
            }
        } else {
            lblAllTripsMessageOne.setText("No trips selected");
            lblAllTripsMessageOne.setStyle("-fx-text-fill:" + RED);
        }

    }

    public void deleteTripMulti() {
        lblAllTripsMessageMulti.setText("");
        if (listAdminMulti.getSelectionModel().getSelectedItem() != null) {
            String[] entry = listAdminMulti.getSelectionModel().getSelectedItem().toString().split(" -");
            String tripName = entry[0].substring(11);
            String accommodationId = entry[5].substring(5);
            System.out.println(accommodationId);

            if (database.deleteTripMulti(tripName, accommodationId)) {
                lblAllTripsMessageMulti.setText("Trip deleted");
                lblAllTripsMessageMulti.setStyle("-fx-text-fill:" + GREEN);
                updateAdminLists();
            } else {
                lblAllTripsMessageMulti.setText("An error occurred");
                lblAllTripsMessageMulti.setStyle("-fx-text-fill:" + RED);
            }
        } else {
            lblAllTripsMessageMulti.setText("No trips selected");
            lblAllTripsMessageMulti.setStyle("-fx-text-fill:" + RED);
        }

    }

    public void clientReservations() {
        listAdminOne.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    String currentItemSelected = listAdminOne.getSelectionModel().getSelectedItem().toString();

                    String[] entry = currentItemSelected.split(" -");
                    String tripName = entry[0].substring(11);

                    Alert clientList = new Alert(Alert.AlertType.NONE, database.getAdminReservations(tripName), ButtonType.CLOSE);
                    clientList.setHeaderText(tripName + " client list:");
                    clientList.setTitle("Client list");
                    Stage s = (Stage) clientList.getDialogPane().getScene().getWindow();
                    s.getIcons().add(new Image("/style/logo.png"));

                    clientList.showAndWait();


                }
            }
        });

        listAdminMulti.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    String currentItemSelected = listAdminMulti.getSelectionModel().getSelectedItem().toString();

                    String[] entry = currentItemSelected.split(" -");
                    String tripName = entry[0].substring(11);

                    Alert clientList = new Alert(Alert.AlertType.NONE, database.getAdminReservations(tripName), ButtonType.CLOSE);
                    clientList.setHeaderText(tripName + " client list:");
                    clientList.setTitle("Client list");
                    Stage s = (Stage) clientList.getDialogPane().getScene().getWindow();
                    s.getIcons().add(new Image("/style/logo.png"));

                    clientList.showAndWait();


                }
            }

        });
    }

    public void updateClientLists(List<Arrangment> arrangments) {
        listOne.getItems().clear();
        listMulti.getItems().clear();
        List<Arrangment> list = arrangments;

        for (Arrangment arrangment : list) {
            if (arrangment.getDepartureDate().equals(arrangment.getReturnDate())) {
                String entry = new String("Trip name: " + arrangment.getTripName() +
                        " - Destination: " + arrangment.getDestination() +
                        " - Date: " + arrangment.getDepartureDate() +
                        " - Price: " + arrangment.getArrangmentPrice());
                listOne.getItems().add(entry);
            } else {
                String entry = new String("Trip name: " + arrangment.getTripName() +
                        " - Destination: " + arrangment.getDestination() +
                        " - Departure date: " + arrangment.getDepartureDate() +
                        " - Return date: " + arrangment.getReturnDate() +
                        " - Price: " + arrangment.getArrangmentPrice() +
                        " - Id: " + arrangment.getAccommodationID());
                listMulti.getItems().add(entry);
            }
        }
    }

    public void addReservation()
    {
        listOne.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount()==2)
                {
                    String[] entry = listOne.getSelectionModel().getSelectedItem().toString().split(" -");
                    String tripName = entry[0].substring(11);

                    CommonForm.getTripInfo(tripName);
                }
            }
        });

        listMulti.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount()==2)
                {
                    String[] entry = listMulti.getSelectionModel().getSelectedItem().toString().split(" -");
                    String tripName = entry[0].substring(11);

                    CommonForm.getTripInfo(tripName);
                }
            }
        });

    }
    public void updateClientReservations()
    {
        List<Arrangment> reservedArraingment=database.getReservedArraingment(Client.getActiveUser());
        List<Reservation> reservations=database.getClientReservations(Client.getActiveUser());

        listActive.getItems().clear();
        listPast.getItems().clear();
        listCanceled.getItems().clear();

        for (Reservation reservation:reservations)
        {
            for (Arrangment arrangment:reservedArraingment)
            {
                if(reservation.getArrangementId().equals(arrangment.getId()) && reservation.getPaidPrice()>=0) {
                    if (arrangment.getDepartureDate().equals(arrangment.getReturnDate()))
                    {
                        String entry = new String("Trip name: " + arrangment.getTripName() +
                                " - Destination: " + arrangment.getDestination() +
                                " - Date: " + arrangment.getDepartureDate() +
                                " - Price: " + arrangment.getArrangmentPrice());
                        listActive.getItems().add(entry);
                    }
                    else
                    {
                        String entry = new String("Trip name: " + arrangment.getTripName() +
                                " - Destination: " + arrangment.getDestination() +
                                " - Departure date: " + arrangment.getDepartureDate() +
                                " - Return date: " + arrangment.getReturnDate() +
                                " - Price: " + arrangment.getArrangmentPrice() +
                                " - Id: " + arrangment.getAccommodationID());
                        listActive.getItems().add(entry);
                    }

                }
                if(reservation.getArrangementId().equals(arrangment.getId()) && reservation.getPaidPrice()==-1) {
                    if (arrangment.getDepartureDate().equals(arrangment.getReturnDate()))
                    {
                        String entry = new String("Trip name: " + arrangment.getTripName() +
                                " - Destination: " + arrangment.getDestination() +
                                " - Date: " + arrangment.getDepartureDate() +
                                " - Price: " + arrangment.getArrangmentPrice());
                        listPast.getItems().add(entry);
                    }
                    else
                    {
                        String entry = new String("Trip name: " + arrangment.getTripName() +
                                " - Destination: " + arrangment.getDestination() +
                                " - Departure date: " + arrangment.getDepartureDate() +
                                " - Return date: " + arrangment.getReturnDate() +
                                " - Price: " + arrangment.getArrangmentPrice() +
                                " - Id: " + arrangment.getAccommodationID());
                        listPast.getItems().add(entry);
                    }
                }
                if(reservation.getArrangementId().equals(arrangment.getId()) && reservation.getPaidPrice()==-2) {
                    if (arrangment.getDepartureDate().equals(arrangment.getReturnDate()))
                    {
                        String entry = new String("Trip name: " + arrangment.getTripName() +
                                " - Destination: " + arrangment.getDestination() +
                                " - Date: " + arrangment.getDepartureDate() +
                                " - Price: " + arrangment.getArrangmentPrice());
                        listCanceled.getItems().add(entry);
                    }
                    else
                    {
                        String entry = new String("Trip name: " + arrangment.getTripName() +
                                " - Destination: " + arrangment.getDestination() +
                                " - Departure date: " + arrangment.getDepartureDate() +
                                " - Return date: " + arrangment.getReturnDate() +
                                " - Price: " + arrangment.getArrangmentPrice() +
                                " - Id: " + arrangment.getAccommodationID());
                        listCanceled.getItems().add(entry);
                    }
                }
            }
        }
    }

    public void pay()
    {
        List<Reservation> reservations=database.getClientReservations(Client.getActiveUser());
        Reservation r=null;
        String[] entry = listActive.getSelectionModel().getSelectedItem().toString().split(" -");
        String tripName = entry[0].substring(11);
        for(Reservation reservation:reservations)
        {
            if(reservation.getArrangementId().equals(tripName))
            {
                r=reservation;
                break;
            }
        }
        CommonForm.payTrip(r);
        updateBalance();
    }

    public void calcelReservation()
    {
        String[] entry = listActive.getSelectionModel().getSelectedItem().toString().split(" -");
        String tripName = entry[0].substring(11);

        if(database.cancelReservation(Client.getActiveUser(),tripName))
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION,"Reservation canceled",ButtonType.CLOSE);
            alert.showAndWait();
            updateClientReservations();
        }
    }

    public void filter()
    {

        String destination=null;
        double price=0;
        String departureDate=null;
        String returnDate=null;
        String roomType=null;
        int stars=0;
        String trans=null;

        try {
            destination=tfMDestination.getText();
        }
        catch (Exception e){}

        try {
            price=sldPrice.getValue();
        }
        catch (Exception e){}

        try {
            departureDate=calDepartureC.getValue().toString();
        }
        catch (Exception e){}

        try {
            returnDate=calReturnC.getValue().toString();
        }
        catch (Exception e){}

        try {
            roomType= cbRoomTypeC.getValue();
        }
        catch (Exception e){}

        try {
            stars=Integer.parseInt(cbMStarsC.getValue());
        }
        catch (Exception e){}

        try {
            trans=cbMTransportC.getValue();
        }
        catch (Exception e){}


        List<Arrangment> arrangments=database.getArrangements();
        List<Arrangment> list=new ArrayList<>();

        System.out.println(price);

        for(Arrangment a:arrangments)
        {
            boolean isFiltered=true;
            Accommodation acc=null;
            if (!a.getDepartureDate().equals(a.getReturnDate()))
            {
                acc=database.getAccomodation(a.getAccommodationID());
            }

            if(destination!=null && !a.getDestination().equals(destination))
            {
                isFiltered=false;
            }

            if(isFiltered && price!=0 && a.getArrangmentPrice()>=price)
            {
                isFiltered=false;
            }
            if(isFiltered && departureDate!=null && LocalDate.parse(departureDate).isAfter(LocalDate.parse(a.getDepartureDate())))
            {
                isFiltered=false;
            }
            if(isFiltered && returnDate!=null && LocalDate.parse(returnDate).isBefore(LocalDate.parse(a.getReturnDate())))
            {
                isFiltered=false;
            }
            if(isFiltered && roomType!=null && acc!=null && !roomType.equals(acc.getRoomType()))
            {
                isFiltered=false;
            }

            if(isFiltered && stars!=0 && acc!=null && stars!=acc.getNumberOfStars())
            {
                isFiltered=false;
            }

            if(isFiltered && trans!=null && acc!=null && !trans.equals(a.getTransportation()))
            {
                isFiltered=false;
            }

            if(isFiltered)
            {
                list.add(a);
            }
        }

        updateClientLists(list);

    }

   //-------------------------------------Other-------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        database= Main.getDatabase();

        cbRoomType.getItems().addAll("Single-bed","Two-bed","Triple-bed","Apartman");
        cbMStars.getItems().addAll("1","2","3","4","5");
        cbMTransport.getItems().addAll("Plane","Bus","Personal");

        cbMStarsC.getItems().addAll("1","2","3","4","5");
        cbMTransportC.getItems().addAll("Plane","Bus","Personal");
        cbRoomTypeC.getItems().addAll("Single-bed","Two-bed","Triple-bed","Apartman");


        calReturn.setDayCellFactory(d -> new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(LocalDate.now()));
            }});

        calArrival.setDayCellFactory(d -> new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(LocalDate.now()));
            }});

        calDate.setDayCellFactory(d -> new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(LocalDate.now()));
            }});

        calReturnC.setDayCellFactory(d -> new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(LocalDate.now()));
            }});
        calDepartureC.setDayCellFactory(d -> new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(LocalDate.now()));
            }});



//        lblUsername.setText("test");
//        lblNameLname.setText("test");



        if(LoginForm.set[0].equals("user"))
        {
            lblNameLname.setText(Client.getActiveUser().getName()+" "+Client.getActiveUser().getLname());
            lblUsername.setText(Client.getActiveUser().getUsername());
            updateClientLists(database.getArrangements());
            updateClientReservations();
            updateBalance();
            database.pastReservation();

            lblInfo.setStyle("-fx-text-fill:"+RED);
            lblInfo.setText(loginMessage());
        }
        else
        {
            lblNameLname.setText(Admin.getActiveAdmin().getName()+" "+Admin.getActiveAdmin().getLastName());
            lblUsername.setText(Admin.getActiveAdmin().getUsername());
            updateAdminLists();
            updateProfit();
            updateAdminCount();
        }

    }
}
