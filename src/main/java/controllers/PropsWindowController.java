package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.hypergraphs.Proportion;
import model.hypergraphs.Vert;
import model.weightedhypergraph.WeightedHypergraph;

import java.io.IOException;
import java.util.List;

public class PropsWindowController {
    @FXML
    GridPane gridPane;
    @FXML
    FlowPane fpBtnsVerts;
    @FXML
    Button btnBack;
    @FXML
    Button btnNext;
    @FXML
    Button btnCancel;
    @FXML
    Button btnHelp;
    private WeightedHypergraph hypergraph;
    private int[] propColumnSize;
    private ObservableList<Button> btnsVerts;
    @FXML
    private void initialize(){

    }

    public PropsWindowController(){

    };

    public void setEvents(){
        btnCancel.setOnMouseClicked((event -> {
            Stage stage = (Stage)btnCancel.getScene().getWindow();
            stage.close();
        }));

        btnNext.setOnMouseClicked((event -> {
            if(!fpBtnsVerts.getChildren().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка");
                alert.setContentText("Есть вершины без доли");
                alert.showAndWait();
            }
            else
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/edgesWindow.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Ребра");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();

                EdgesWindowController controller = loader.getController();
                controller.setHypergraph(hypergraph);
                controller.setupWindow();
                controller.setEvents();

                Stage thisStage = (Stage) btnNext.getScene().getWindow();
                thisStage.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }));

        btnBack.setOnMouseClicked((event -> {
            try {
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
        }));

        btnHelp.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/propsWindowHelp.fxml"));
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
        List<Vert> verts = hypergraph.getVerts();
        List<Proportion> props = hypergraph.getProps();
        propColumnSize = new int[props.size()];
        gridPane.setGridLinesVisible(true);

        btnsVerts = FXCollections.observableArrayList();

        for(Vert v: verts){
            Button button = new Button();
            button.setWrapText(true);
            button.setText(String.valueOf(v.getNumber()));
            button.setUserData(v);
            btnsVerts.add(button);
            button.setOnMouseClicked((event -> {
                if(gridPane.getChildren().contains(button)) {
                    propColumnSize[GridPane.getColumnIndex(button)-1]--;
                    props.get(GridPane.getColumnIndex(button)-1).getVerts().remove(button.getUserData());
                    fpBtnsVerts.getChildren().add(button);
                    drawGrid();
                }
            }));
        }
        fpBtnsVerts.getChildren().addAll(btnsVerts);
        drawGrid();
        }

    private void drawGrid() {
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);
        for(int i = 0; i< propColumnSize.length;i++){
            propColumnSize[i] = 1;
        }

        for (Proportion p : hypergraph.getProps()) {

            Button button = new Button();
            button.setWrapText(true);
            button.setText("Доля №" + String.valueOf(p.getNumber()));
            button.setUserData(p);
            button.setFocusTraversable(false);
            gridPane.add(button, p.getNumber(), 0);

            button.setOnMouseClicked((event -> {
                if (button.getScene().getFocusOwner() instanceof Button && !gridPane.getChildren().contains(button.getScene().getFocusOwner())) {
                    Vert vert = (Vert) button.getScene().getFocusOwner().getUserData();
                    Proportion prop = (Proportion) button.getUserData();
                    propColumnSize[prop.getNumber() - 1]++;
                    prop.getVerts().add(vert);
                    gridPane.add((Button) button.getScene().getFocusOwner(), prop.getNumber(), propColumnSize[prop.getNumber() - 1]);
                    System.out.println(propColumnSize[prop.getNumber() - 1]);
                }
            }));
        }
            for (Button btn: btnsVerts) {
                Vert v = (Vert) btn.getUserData();
                for(Proportion p: hypergraph.getProps()){
                    if (p.getVerts().contains(v)){
                        propColumnSize[p.getNumber()-1]++;
                        gridPane.add(btn, p.getNumber(), propColumnSize[p.getNumber()-1]);
                    }
                }
            }

    }
    public void setHypergraph(WeightedHypergraph hypergraph) {
        this.hypergraph = hypergraph;
    }

}
