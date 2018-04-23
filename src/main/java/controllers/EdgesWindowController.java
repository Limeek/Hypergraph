package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.hypergraphs.Edge;
import model.hypergraphs.Proportion;
import model.hypergraphs.Vert;
import model.weightedhypergraph.WeightedEdge;
import model.weightedhypergraph.WeightedHypergraph;
import util.AlertUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EdgesWindowController {
    @FXML
    Button btnBack;
    @FXML
    Button btnNext;
    @FXML
    Button btnCancel;
    @FXML
    Button btnCreateEdge;
    @FXML
    TextArea taEdgeDescr;
    @FXML
    GridPane edgeGrid;
    @FXML
    TextField tfEdge;
    @FXML
    TextField tfEdgeWeight;
    @FXML
    Label warningLabel;
    @FXML
    Button btnDelete;
    @FXML
    Button btnClear;
    @FXML
    Button btnHelp;
    private WeightedHypergraph hypergraph;

    private static int edgeCount = 1;

    private int edgeWeightCount;

    private List<Vert> tmpEdgeVerts;

    public EdgesWindowController(){}

    public void setEvents(){
        btnBack.setOnMouseClicked((event -> {
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/propsWindow.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Доли");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                PropsWindowController controller = loader.getController();
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
        }));
        btnNext.setOnMouseClicked((event -> {
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
        }));
        btnCancel.setOnMouseClicked(event -> {
            Stage stage = (Stage)btnCancel.getScene().getWindow();
            stage.close();
        });

        btnCreateEdge.setOnMouseClicked((event -> {
            int count;
            boolean flagProp = true;
            boolean flagEdge = true;
            boolean flagWeight = true;
            boolean flagWeightCount = true;
            cycle:
            for(Proportion p : hypergraph.getProps()){
                count = 0;
                for(Vert v:tmpEdgeVerts){
                    for (Vert v1 : p.getVerts()){
                        if(v.equals(v1)) count++;
                    }
                if(count > 1) {
                    flagProp = false;
                    break cycle;}
                }
            }
            for(Edge e: hypergraph.getEdges()){
                if(e.getVerts().equals(tmpEdgeVerts))
                    flagEdge = false;
            }

            if(edgeWeightCount == 0) flagWeight = false;
            if(edgeWeightCount > 5) flagWeightCount = false;
            if(tmpEdgeVerts.size() > hypergraph.getProps().size()){
                AlertUtils.showEdgeHasMoreVertsThenProp();
                tfEdge.setText("");
                tmpEdgeVerts.clear();
            }else if(!flagProp){
                AlertUtils.showEdgeIsInProp();
                tfEdge.setText("");
                tmpEdgeVerts.clear();
            }else if (!flagEdge){
                AlertUtils.showEdgeExists();
                tfEdge.setText("");
                tmpEdgeVerts.clear();
            }else if(!flagWeight){
                AlertUtils.showNoWeight();
            }else if(!flagWeightCount){
                AlertUtils.showHasMoreThanFiveWeight();
            }
            else if(tmpEdgeVerts.isEmpty()){
                AlertUtils.showEdgeIsEmpty();
                tfEdge.setText("");
            }
            else {
                try {
                    List<Vert> edgeVerts = new ArrayList<>(tmpEdgeVerts);
                    tmpEdgeVerts.clear();
                    WeightedEdge edge = new WeightedEdge(edgeVerts, edgeCount, edgeWeightCount);
                    edge.sortVerts();
                    hypergraph.getEdges().add(edge);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/fxml/edgeWeightWindow.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Веса");
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    EdgeWeightController controller = loader.getController();
                    controller.setEdge(edge);
                    controller.setupWindow();
                    controller.setEvents();
                    stage.showAndWait();

                    edgeCount++;
                    tfEdge.setText("");
                    taEdgeDescr.setText(taEdgeDescr.getText() + edge.toString() + "\n");
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }));

        tfEdgeWeight.setOnMouseClicked((event -> {
            if(!hypergraph.getEdges().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING,"Удалить все ребра?",ButtonType.YES,ButtonType.NO);
                alert.setHeaderText("Предупреждение");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.YES) {
                    hypergraph.getEdges().clear();
                    edgeCount = 1;
                    taEdgeDescr.setText("");
                    edgeWeightCount = 0;
                }
            }
        }));

        tfEdgeWeight.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                try{
                    if(!tfEdgeWeight.getText().equals("")){
                        edgeWeightCount = Integer.valueOf(tfEdgeWeight.getText());
                        warningLabel.setText("При изменении все ребра удалятся");
                    }
                        else{
                            warningLabel.setText("Введите количество весов!");
                        }
                    }
                    catch (NumberFormatException e){
                        warningLabel.setText("Введите число!");
                    }
            }
        });

        btnDelete.setOnMouseClicked((event -> {
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/edgeDeleteWindow.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("HypergraphApp");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                EdgeDeleteWindowController controller = loader.getController();
                controller.setEdges(hypergraph.getEdges());
                controller.setupWindow();
                controller.setEvents();
                stage.showAndWait();
                edgeCount = 1;
                for(Edge e : hypergraph.getEdges()){
                    e.setNumber(edgeCount);
                    edgeCount++;
                }
                taEdgeDescr.setText("");
                if(!hypergraph.getEdges().isEmpty())
                    for(Edge e: hypergraph.getEdges()){
                        taEdgeDescr.setText(taEdgeDescr.getText() + e.toString() +"\n");
                    }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }));

        btnClear.setOnMouseClicked(event -> {
            tmpEdgeVerts.clear();
            tfEdge.setText("");
        });

        btnHelp.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/edgeWindowHelp.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Помощь");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.showAndWait();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    public void setupWindow(){
        edgeWeightCount = 0;
        if(!hypergraph.getEdges().isEmpty()){
            tfEdgeWeight.setText(String.valueOf(((WeightedEdge) hypergraph.getEdges().get(0)).getWeight().length));
            for(Edge e: hypergraph.getEdges()){
                taEdgeDescr.setText(taEdgeDescr.getText() + e.toString() +"\n");
            }
        }
        tmpEdgeVerts = new ArrayList<>();
        List<Proportion> props = hypergraph.getProps();
        List<Vert> verts = hypergraph.getVerts();


        for(Proportion p:props){
            edgeGrid.add(new Label("Доля №" + p.getNumber()),p.getNumber(),0);
            int count = 0;
            for(Vert v:p.getVerts()){
                count++;
                Button button = new Button();
                button.setWrapText(true);
                button.setUserData(v);
                button.setText(String.valueOf(v.getNumber()));
                edgeGrid.add(button,p.getNumber(),count);
                button.setOnMouseClicked((event -> {
                    tmpEdgeVerts.add((Vert) button.getUserData());
                    tfEdge.setText(tfEdge.getText() + " " + v.getNumber());
                }));
            }
        }



    }

    public void setHypergraph(WeightedHypergraph hypergraph) {
        this.hypergraph = hypergraph;
    }
}
