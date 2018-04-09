package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.weightedhypergraph.WeightedEdge;


public class EdgeWeightController {
    @FXML
    GridPane gridPane;
    @FXML
    Button btnOk;

    WeightedEdge edge;



    public void setupWindow(){
        for(int i = 0; i< edge.getWeight().length;i++){
            Label weightLabel = new Label("Вес №" + String.valueOf(i+1));
            TextField tfWeight = new TextField();
            tfWeight.setUserData(i);
            tfWeight.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if(tfWeight.getText().equals(""))
                    edge.getWeight()[(int) tfWeight.getUserData()] = 0;
                else{
                    edge.getWeight()[(int) tfWeight.getUserData()] = Double.valueOf(tfWeight.getText());
                }
            });
            gridPane.add(weightLabel,0,i);
            gridPane.add(tfWeight,1,i);
        }
    }

    public void setEvents(){
        btnOk.setOnMouseClicked(event -> {
            Stage stage = (Stage)btnOk.getScene().getWindow();
            stage.close();
        });
    }

    public void setEdge(WeightedEdge edge) {
        this.edge = edge;
    }
}
