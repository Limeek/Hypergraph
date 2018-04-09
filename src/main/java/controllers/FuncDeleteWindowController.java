package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.weightedhypergraph.VectorFunction;

import java.util.List;

public class FuncDeleteWindowController {
    @FXML
    Button btnOk;
    @FXML
    FlowPane fpButtons;

    private List<VectorFunction> vectorFunctionList;

    public void setEvents(){
        btnOk.setOnMouseClicked(event -> {
            Stage stage = (Stage) btnOk.getScene().getWindow();
            stage.close();
        });
    }

    public void setupWindow(){
        for(VectorFunction v: vectorFunctionList){
            Button button = new Button();
            button.setText("F"+v.getNumber());
            button.setUserData(v);
            fpButtons.getChildren().add(button);
            button.setOnMouseClicked(event -> {
                VectorFunction func = (VectorFunction) button.getUserData();
                vectorFunctionList.remove(func);
                fpButtons.getChildren().remove(button);
            });
        }
    }

    public void setVectorFunctionList(List<VectorFunction> vectorFunctionList) {
        this.vectorFunctionList = vectorFunctionList;
    }
}

