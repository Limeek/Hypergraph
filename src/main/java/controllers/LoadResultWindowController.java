package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.weightedhypergraph.WeightedHypergraph;

import java.io.IOException;

public class LoadResultWindowController {
    @FXML
    TextArea taHypergraphInfo;
    @FXML
    Button btnBack;
    @FXML
    Button btnNext;

    WeightedHypergraph hypergraph;

    public void setEvents(){
        btnBack.setOnMouseClicked(event -> {
            try {
                hypergraph.hypergraphClear();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/vertsWindow.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Вершины и доли");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
                VertsAndPropsWindowController controller = loader.getController();

                controller.setHypergraph(hypergraph);
                controller.setupWindow();
                controller.setEvents();

                Stage thisStage = (Stage) btnBack.getScene().getWindow();
                thisStage.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
        btnNext.setOnMouseClicked(event -> {
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/vectorFunctionWindow.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("ВЦФ");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                VectorFunctionWindowController controller = loader.getController();
                controller.setHypergraph(hypergraph);
                controller.setupWindow();
                controller.setEvents();
                stage.show();

                Stage thisStage = (Stage)btnNext.getScene().getWindow();
                thisStage.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
    }
    public void setupWindow(){
        taHypergraphInfo.setText(hypergraph.hypergraphInfo());
    }

    public void setHypergraph(WeightedHypergraph hypergraph) {
        this.hypergraph = hypergraph;
    }
}
