package ui;

import bank.*;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionAttributeException;
import bank.exceptions.TransactionDoesNotExistException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class AccountviewController extends MyController {

    @FXML
    Label labelAccountname;

    @FXML
    Label labelKontostand;

    @FXML
    Button buttonBacktomain;

    @FXML
    ListView<Transaction> listviewTransactionsList;

    @FXML
    ChoiceBox<String> cbSortierung;

    @FXML
    public void sortierungAendern() {
        String ausgewaehlt = cbSortierung.getSelectionModel().getSelectedItem();

        switch(ausgewaehlt) {
            case "Aufsteigend":
                listviewTransactionsList.getItems().setAll(pb.getTransactionsSorted(account,true));
                break;
            case "Absteigend":
                listviewTransactionsList.getItems().setAll(pb.getTransactionsSorted(account,false));
                break;
            case "Positive":
                listviewTransactionsList.getItems().setAll(pb.getTransactionsByType(account,true));
                break;
            case "Negative":
                listviewTransactionsList.getItems().setAll(pb.getTransactionsByType(account,false));
                break;
        }
    }

    ObservableList<Transaction> items;

    String account;

    public void setAccount(String account) {
        this.account = account;

        labelAccountname.setText(account);
        updateKontostand();
        try {
            items = FXCollections.observableArrayList(pb.getTransactions(account));
            listviewTransactionsList.setItems(items);
        } catch (Exception e) {
            errorAlert(e);
        }
    }

    private void updateKontostand() {
        labelKontostand.setText(Double.toString(pb.getAccountBalance(account)));
    }

    @FXML
    public void transaktionLoeschen() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Transaktion löschen");
        alert.setHeaderText("Transaktion löschen");
        alert.setContentText("Transaktion wirklich löschen?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                Transaction selected = listviewTransactionsList.getSelectionModel().getSelectedItem();
                pb.removeTransaction(account, selected);
                items.remove(selected);
                updateKontostand();
            } catch (AccountDoesNotExistException | TransactionDoesNotExistException | IOException e) {
                errorAlert(e);
            }
        }
    }

    public void transaktionHinzufuegen() {
        Dialog<Transaction> dialog = new Dialog<>();
        dialog.setTitle("Neue Transaktion hinzufügen");

        // Buttons
        ButtonType buttonOk = new ButtonType("Hinzufügen", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonOk, ButtonType.CANCEL);

        // Dropdown Payment oder Transfer
        ChoiceBox<String> cbType = new ChoiceBox<>();
        cbType.getItems().addAll("Payment", "Transfer");
        cbType.getSelectionModel().selectFirst();

        TextField tfDate = new TextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        TextField tfAmount = new TextField();
        TextField tfDescription = new TextField();

        TextField tfIncoming = new TextField();
        TextField tfOutgoing = new TextField();

        TextField tfSender = new TextField();
        TextField tfRecipient = new TextField();

        // Grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // add(Node, Spalte, Zeile)
        grid.add(new Label("Typ:"), 0, 0);
        grid.add(cbType, 1, 0);

        grid.add(new Label("Datum:"), 0, 1);
        grid.add(tfDate, 1, 1);

        grid.add(new Label("Betrag:"), 0, 2);
        grid.add(tfAmount, 1, 2);

        grid.add(new Label("Beschreibung:"), 0, 3);
        grid.add(tfDescription, 1, 3);

        // Payment Felder standardmäßig anzeigen
        grid.add(new Label("Incoming Interest:"), 0, 4);
        grid.add(tfIncoming, 1, 4);
        grid.add(new Label("Outgoing Interest:"), 0, 5);
        grid.add(tfOutgoing, 1, 5);

        // Listener für die ChoiceBox, um Felder ein- und auszublenden
        cbType.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.equals("Payment")) {
                // Remove Transfer Felder
                grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null &&
                        (GridPane.getRowIndex(node) == 4 || GridPane.getRowIndex(node) == 5));

                // Add Payment Felder
                grid.add(new Label("Incoming Interest:"), 0, 4);
                grid.add(tfIncoming, 1, 4);
                grid.add(new Label("Outgoing Interest:"), 0, 5);
                grid.add(tfOutgoing, 1, 5);
            } else {
                // Remove Payment Felder
                grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null &&
                        (GridPane.getRowIndex(node) == 4 || GridPane.getRowIndex(node) == 5));

                // Add Transfer Felder
                grid.add(new Label("Sender:"), 0, 4);
                grid.add(tfSender, 1, 4);
                grid.add(new Label("Recipient:"), 0, 5);
                grid.add(tfRecipient, 1, 5);
            }
        });

        dialog.getDialogPane().setContent(grid);


        try {
            // Bei Buttonpress Transaction hinzufügen/abbrechen
            dialog.setResultConverter(button -> {
                if (button == buttonOk) {
                    if(tfDate.getText().isBlank() ||
                            tfAmount.getText().isBlank() ||
                            tfDescription.getText().isBlank()) {
                        errorAlert(new Exception("Bitte alles angeben"));
                        return null;
                    }
                    String date = tfDate.getText();
                    double amount = Double.parseDouble(tfAmount.getText());
                    String desc = tfDescription.getText();
                    try {
                        if (cbType.getValue().equals("Payment")) {
                            if (tfIncoming.getText().isBlank() ||
                                    tfOutgoing.getText().isBlank()) {
                                errorAlert(new Exception("Bitte alles angeben"));
                                return null;
                            }
                            double inc = Double.parseDouble(tfIncoming.getText());
                            double out = Double.parseDouble(tfOutgoing.getText());
                            return new Payment(date, amount, desc, inc, out);
                        } else {
                            if (tfSender.getText().isBlank() ||
                                    tfRecipient.getText().isBlank()) {
                                errorAlert(new Exception("Bitte alles angeben"));
                                return null;
                            }
                            String sender = tfSender.getText();
                            String recipient = tfRecipient.getText();
                            if (sender.equals(account))
                                return new OutgoingTransfer(date, amount, desc, sender, recipient);
                            return new IncomingTransfer(date, amount, desc, sender, recipient);
                        }
                    } catch (Exception e) {
                        errorAlert(e);
                        return null;
                    }
                }
                return null;
            });
        } catch (Exception e) {
            errorAlert(e);
            return;
        }

        // null = Keine Transaktion hinzufügen
        dialog.showAndWait().ifPresent(transaction -> {
            try {
                pb.addTransaction(account, transaction);
                items.add(transaction);
                updateKontostand();
            } catch (Exception e) {
                errorAlert(e);
            }
        });
    }


    @FXML
    public void zurueck() {
        Stage stage = (Stage) listviewTransactionsList.getScene().getWindow();
        try {
            Parent main = FXMLLoader.load(getClass().getResource("/mainview.fxml"));
            Scene scene = new Scene(main);
            stage.setScene(scene);
        } catch (IOException | NullPointerException e) {
            errorAlert(e);
        }
    }

    @FXML
    public void initialize() {
        try {
            pb = new PrivateBank("Bank2",0.5,0.5,"C:\\Users\\ziino\\OneDrive\\Studium\\OoS\\OoS\\P5\\accounts\\");
        } catch (TransactionAttributeException | TransactionAlreadyExistException | AccountDoesNotExistException |
                 IOException e) {
            errorAlert(e);
        }
        cbSortierung.getItems().addAll("Aufsteigend","Absteigend","Positive","Negative");
    }
}
