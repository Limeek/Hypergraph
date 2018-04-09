package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.weightedhypergraph.Solution;
import model.weightedhypergraph.VectorFunction;
import model.weightedhypergraph.WeightedEdge;
import model.weightedhypergraph.WeightedHypergraph;
import java.io.IOException;

public class VectorFunctionWindowController {
    @FXML
    TextArea taFuncDescr;
    @FXML
    FlowPane fpWeight;
    @FXML
    FlowPane fpFuncs;
    @FXML
    Button btnDeleteFunc;
    @FXML
    Button btnExit;
    @FXML
    Button btnNext;
    @FXML
    Button btnBack;
    @FXML
    Button btnMaxSum;
    @FXML
    Button btnMinSum;
    @FXML
    Button btnMaxAvg;
    @FXML
    Button btnMinAvg;
    @FXML
    Button btnMaxMax;
    @FXML
    Button btnMinMin;
    @FXML
    Button btnMaxMin;
    @FXML
    Button btnMinMax;
    @FXML
    Button btnMaxProd;
    @FXML
    Button btnMinProd;
    @FXML
    Button btnHelp;
    @FXML
    private void initialize(){}

    private WeightedHypergraph hypergraph;
    private static int funcCount = 1;

    public VectorFunctionWindowController(){

    }

    public void setupWindow(){
        if(hypergraph.getSolution() == null){
            hypergraph.setSolution(new Solution());
        }

        if(!hypergraph.getSolution().getFuncList().isEmpty()){
            taFuncDescr.setText("");
            for(VectorFunction v : hypergraph.getSolution().getFuncList())
                taFuncDescr.setText(taFuncDescr.getText() + v.toString() +"\n");
        }


        int weightCount;
        weightCount = ((WeightedEdge) hypergraph.getEdges().get(0)).getWeight().length;
        for(int i=0;i<weightCount;i++){
            Button button = new Button("w" + String.valueOf(i+1));
            button.setUserData(i);
            button.setFocusTraversable(false);
            button.setOnMouseClicked(event -> {
                if(button.getScene().getFocusOwner() instanceof Button){
                    VectorFunction f = new VectorFunction(((Button) button.getScene().getFocusOwner()).getText());

                    f.setNumber(funcCount);
                    f.setWeightNumber((int) button.getUserData());

                    hypergraph.getSolution().getFuncList().add(f);
                    funcCount ++;
                    taFuncDescr.setText(taFuncDescr.getText() + f.toString() + "\n");
                }
            });
            fpWeight.getChildren().add(button);
        }
    }

    public void setEvents(){
        btnBack.setOnMouseClicked((event -> {
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/edgesWindow.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("HypergraphApp");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                EdgesWindowController controller = loader.getController();
                controller.setHypergraph(hypergraph);
                controller.setupWindow();
                controller.setEvents();
                stage.show();
                Stage thisStage = (Stage)btnBack.getScene().getWindow();
                thisStage.close();

            }
            catch (IOException e ){
                e.printStackTrace();
            }
        }));
        btnNext.setOnMouseClicked(event -> {
            try {
                hypergraph.getCombs().clear();
                hypergraph.getPerfCombs().clear();
                Solution solution = hypergraph.getSolution();
                hypergraph.makeMatrixOfAdj();
                hypergraph.makecombs();
                hypergraph.calcPerfCombs();
                solution.setCombs(hypergraph.getPerfCombs());

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/resultsWindow.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Результат");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                ResultsWindowController controller = loader.getController();
                controller.setHypergraph(hypergraph);
                controller.setupWindow();
                controller.setEvents();
                stage.showAndWait();

            }
            catch (IOException e){
                e.printStackTrace();
            }
            catch (IndexOutOfBoundsException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка");
                alert.setContentText("Совершенных сочетаний не найдено");
                alert.showAndWait();
            }
        });
        btnDeleteFunc.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/funcDeleteWindow.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Удаление ВЦФ");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                FuncDeleteWindowController controller = loader.getController();
                controller.setVectorFunctionList(hypergraph.getSolution().getFuncList());
                controller.setupWindow();
                controller.setEvents();
                stage.showAndWait();
                funcCount = 1;
                for (VectorFunction v: hypergraph.getSolution().getFuncList()){
                    v.setNumber(funcCount);
                    funcCount++;
                }
                taFuncDescr.setText("");
                if(!hypergraph.getSolution().getFuncList().isEmpty()){
                    for(VectorFunction v:hypergraph.getSolution().getFuncList()){
                        taFuncDescr.setText(taFuncDescr.getText() + v.toString() + "\n");
                    }
                }

            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
        btnExit.setOnMouseClicked((event -> {
            Stage stage =  (Stage)btnExit.getScene().getWindow();
            stage.close();
        }));
        btnHelp.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/vectorFunctionHelp.fxml"));
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

    public void setHypergraph(WeightedHypergraph hypergraph) {
        this.hypergraph = hypergraph;
    }
}
