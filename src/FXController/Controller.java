package FXController;

import data.Admin;
import data.Client;
import data.Database;
import forms.CommonForm;
import forms.LoginForm;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{

    public Label lblNameLnameee=new Label("");
    private Scene scene;
    private Stage stage;
    private Parent root;

    @FXML
    private Button btnFilters;

    @FXML
    private Button btnNewReservation;

    @FXML
    private Button btnPasswordChange;

    @FXML
    private Button btnReservations;

    @FXML
    private DatePicker calDeparture;

    @FXML
    private DatePicker calReturn;

    @FXML
    private ComboBox<?> combRoomType;

    @FXML
    private ComboBox<?> combStars;

    @FXML
    private ComboBox<?> combTransport;

    @FXML
    private Label lblNameLname;

    @FXML
    private Label lblUsername;

    @FXML
    private Slider sldPrice;

    @FXML
    private TextField tfDestination;


    //-------------------------------------FXML-----------------------------------------------




    private Scene getFxmlScene(String name)
    {
        try {
            return new Scene(FXMLLoader.load(getClass().getResource(name)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToClientReservations(javafx.event.ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene=getFxmlScene("clientReservations.fxml");
        scene.getStylesheets().add(getClass().getResource("/style/guiTest.css").toExternalForm());
        stage.setScene(scene);
    }

    public void switchToClientAllReservations(javafx.event.ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene=getFxmlScene("clientNewReservation.fxml");
        scene.getStylesheets().add(getClass().getResource("/style/guiTest.css").toExternalForm());
        stage.setScene(scene);
    }

    public void changePassword(javafx.event.ActionEvent actionEvent)
    {
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene= CommonForm.changePassword(Client.getActiveUser().getUsername());
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(LoginForm.set[0].equals("user"))
        {
            lblNameLname.setText(Client.getActiveUser().getName()+" "+Client.getActiveUser().getLname());
            lblUsername.setText(Client.getActiveUser().getUsername());
        }
        else
        {
            lblNameLname.setText(Admin.getActiveAdmin().getName()+" "+Admin.getActiveAdmin().getLastName());
            lblUsername.setText(Admin.getActiveAdmin().getUsername());
        }

    }
}
