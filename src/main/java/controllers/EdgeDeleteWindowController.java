package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.hypergraphs.Edge;

import java.util.List;

public class EdgeDeleteWindowController {
    @FXML
    FlowPane fpEdges;
    @FXML
    Button btnOk;
    @FXML
    public void initialize(){}

    List<Edge> edges;

    public void setEvents(){
        btnOk.setOnMouseClicked(event -> {
            Stage stage = (Stage) btnOk.getScene().getWindow();
            stage.close();
        });
    }

    public void setupWindow(){
        for(Edge e: edges){
            Button button = new Button (String.valueOf(e.getNumber()));
            button.setUserData(e);
            fpEdges.getChildren().add(button);
            button.setOnMouseClicked(event -> {
                Edge edge =(Edge) button.getUserData();
                edges.remove(edge);
                fpEdges.getChildren().remove(button);
            });
        }
    }

    public void setEdges(List<Edge> edges){
        this.edges = edges;
    }
}
