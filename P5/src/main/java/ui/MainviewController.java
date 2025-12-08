package ui;

import bank.PrivateBank;
import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainviewController extends MyController {
    @FXML
    private ListView<String> listviewAccountsList;

    @FXML
    Button buttonAddAccount;

    @FXML
    ContextMenu contextMenu;

    @FXML
    MenuItem contextAuswaehlen;

    @FXML
    MenuItem contextLoeschen;

    ObservableList<String> items;

    @FXML
    public void initialize() {
        try {
            pb = new PrivateBank("Bank",0.5,0.5,"C:\\Users\\ziino\\OneDrive\\Studium\\OoS\\OoS\\P5\\accounts\\");
        } catch ( bank.exceptions.TransactionAttributeException | bank.exceptions.TransactionAlreadyExistException | bank.exceptions.AccountDoesNotExistException | java.io.IOException e) {
            errorAlert(e);
        }
        items = FXCollections.observableArrayList(pb.getAllAccounts());
        listviewAccountsList.setItems(items);
    }

    @FXML
    private void accountAuswaehlen(ActionEvent event) {
        String account = listviewAccountsList.getSelectionModel().getSelectedItem();

        Stage stage = (Stage) listviewAccountsList.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/accountview.fxml"));
            Parent root = loader.load();

            // Controller, welcher die FXML Datei geladen hat
            AccountviewController controller = loader.getController();
            // Account übergeben
            controller.setAccount(account);

            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException | NullPointerException e) {
            errorAlert(e);
        }
    }


    @FXML
    public void accountErstellen() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Accountname Eingabe");
        dialog.setHeaderText("Namen des Accounts, den du erstellen möchtest");
        dialog.setContentText("Zu erstellender Account");

        String name;
        try {
            name = dialog.showAndWait().get();
        } catch (RuntimeException e) {
            errorAlert(e);
            return;
        }
        if(name.isBlank()) {
            errorAlert(new RuntimeException("Bitte Accountnamen eingeben"));
            return;
        }

        try {
            pb.createAccount(name);
            items.add(name);
        } catch (AccountAlreadyExistsException | IOException e) {
            errorAlert(e);
        }
    }

    @FXML
    public void accountLoeschen(ActionEvent actionEvent) {
        String account = listviewAccountsList.getSelectionModel().getSelectedItem();
        System.out.println(account);
        try {
            pb.deleteAccount(account);
            items.remove(account);
        } catch (AccountDoesNotExistException | IOException e) {
            errorAlert(e);
        }
    }


}
