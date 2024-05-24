package org.apollo.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apollo.template.Controller.MainController;
import org.apollo.template.Service.ConfigLoader;


import java.io.IOException;

public class App extends Application {

    public static int screenWidth = 1200;
    public static int screenHeight = 800;
    private String screenTitle = "Apollo - JavaFx Template";
    private Image icon = new Image("file:src/main/resources/org/apollo/template/images/TempLogoBG_shadow.png");

    @Override
    public void start(Stage stage) throws IOException {

        ConfigLoader.get(); // initializing configLoader.

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MainView.fxml"));
        fxmlLoader.setController(MainController.getInstance());
        Scene scene = new Scene(fxmlLoader.load(), screenWidth, screenHeight);

        // setting stage props.
        stage.setTitle(screenTitle);
        stage.setScene(scene);
        stage.getIcons().add(icon);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}