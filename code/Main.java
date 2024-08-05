package code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;

    public Main() {}

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Paint");
        primaryStage.setMaximized(true);
        primaryStage.setWidth(2880);
        primaryStage.setHeight(1620);
        showCanvas();
    }

    public void showCanvas() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/Canvas.fxml"));
        Parent root;
        try {
            root = loader.load();
        }
        catch(Exception e) {
            System.out.println(e);
            return;
        }

        setScene(root, "/res/styles.css");
    }

    private void setScene(Parent root, String path) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(path).toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}