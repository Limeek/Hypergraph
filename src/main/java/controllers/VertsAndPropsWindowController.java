package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.hypergraphs.Proportion;
import model.hypergraphs.Vert;
import model.weightedhypergraph.WeightedHypergraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VertsAndPropsWindowController {
    @FXML
    TextField tfVertsCount;
    @FXML
    TextField tfPropsCount;
    @FXML
    Button btnOk;
    @FXML
    Button btnCancel;
    private WeightedHypergraph hypergraph;

    @FXML
    private void initialize(){}

    public VertsAndPropsWindowController(){}

    public void setupWindow(){
        if(!hypergraph.getVerts().isEmpty() && !hypergraph.getProps().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Ошибка");
            alert.setTitle("Ошибка");
            alert.setContentText("При изменении количества вершин или долей гиперграф удалится.");
            alert.showAndWait();
        }
        if(!hypergraph.getVerts().isEmpty()){
            tfVertsCount.setText(String.valueOf(hypergraph.getVerts().size()));
        }
        if(!hypergraph.getProps().isEmpty()){
            tfPropsCount.setText(String.valueOf(hypergraph.getProps().size()));
        }
    }

    public void setEvents(){
        btnOk.setOnMouseClicked((event)-> {
            if (tfPropsCount.getText().equals("") || tfVertsCount.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Ошибка");
                alert.setTitle("Ошибка");
                alert.setContentText("Введите количество вершин и долей.");
                alert.showAndWait();
            }
            else
            if(Integer.valueOf(tfPropsCount.getText()) <=0 || Integer.valueOf(tfVertsCount.getText()) <=0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Ошибка");
                alert.setTitle("Ошибка");
                alert.setContentText("Введите неотрицательное число.");
                alert.showAndWait();
            }
            else
            try{
                List<Vert> verts = new ArrayList<>();
                if(Integer.valueOf(tfVertsCount.getText()) != hypergraph.getVerts().size() || Integer.valueOf(tfPropsCount.getText()) != hypergraph.getProps().size()) {
                    hypergraph.hypergraphClear();
                    for (int i = 0; i < Integer.valueOf(tfVertsCount.getText()); i++) {
                        verts.add(new Vert(i + 1));
                    }
                    hypergraph.setVerts(verts);

                    List<Proportion> props = new ArrayList<>();

                    for (int i = 0; i < Integer.valueOf(tfPropsCount.getText()); i++) {
                        props.add(new Proportion(i + 1));
                    }
                    hypergraph.setProps(props);
                }

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/propsWindow.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Доли");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();

                PropsWindowController controller = loader.getController();
                controller.setHypergraph(hypergraph);
                controller.setupWindow();
                controller.setEvents();

                Stage thisStage = (Stage)btnOk.getScene().getWindow();
                thisStage.close();


            }
            catch (IOException e){
                e.printStackTrace();
            }
        });

        btnCancel.setOnMouseClicked((event -> {
            Stage stage = (Stage)btnCancel.getScene().getWindow();
            stage.close();
        }));

    }
    public void setHypergraph(WeightedHypergraph hypergraph){
        this.hypergraph = hypergraph;
    }
}
