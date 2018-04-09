package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.weightedhypergraph.WeightedHypergraph;


public class MainApp extends Application{
    WeightedHypergraph hypergraph = new WeightedHypergraph();
    public MainApp(){
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/vertsWindow.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Вершины и доли");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        VertsAndPropsWindowController controller = loader.getController();
        controller.setHypergraph(hypergraph);
        controller.setEvents();
    }

    public static void main(String args[]) {
        launch(args);
    }

}
