package FXController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class Controller {

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

}
