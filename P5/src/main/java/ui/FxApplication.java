package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxApplication extends javafx.application.Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        Parent anwendung = FXMLLoader.load(getClass().getResource("/mainview.fxml"));
        Scene scene = new Scene(anwendung);
        stage.setScene(scene);
        stage.setTitle("Bank");
        stage.show();
    }
}
