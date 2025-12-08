package ui;

import bank.PrivateBank;
import javafx.scene.control.Alert;

public abstract class MyController {

    public static PrivateBank pb;

    protected static void errorAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setWidth(500);
        alert.setTitle("Fehler");
        alert.setHeaderText("Error");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

}
