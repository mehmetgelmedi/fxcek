package sample.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stg) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/view/MainView.fxml"));

        Scene scene = new Scene(root,300,500);
        stage=stg;
        stage.setTitle("fx");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
