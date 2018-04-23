package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.hypergraphs.Edge;
import model.hypergraphs.Proportion;
import model.hypergraphs.Vert;
import model.weightedhypergraph.WeightedEdge;
import model.weightedhypergraph.WeightedHypergraph;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import util.AlertUtils;
import util.CheckHypergraph;
import util.ExcelWorker;

import java.io.File;
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
    @FXML
    Button btnLoadFile;

    private WeightedHypergraph hypergraph;

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
            try{
            if (tfPropsCount.getText().equals("") || tfVertsCount.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Ошибка");
                alert.setTitle("Ошибка");
                alert.setContentText("Введите количество вершин и долей.");
                alert.showAndWait();
            } else if (Integer.valueOf(tfPropsCount.getText()) <= 0 || Integer.valueOf(tfVertsCount.getText()) <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Ошибка");
                alert.setTitle("Ошибка");
                alert.setContentText("Введите неотрицательное число.");
                alert.showAndWait();
            } else {
                List<Vert> verts = new ArrayList<>();
                if (Integer.valueOf(tfVertsCount.getText()) != hypergraph.getVerts().size() ||
                        Integer.valueOf(tfPropsCount.getText()) != hypergraph.getProps().size()) {
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

                Stage thisStage = (Stage) btnOk.getScene().getWindow();
                thisStage.close();

                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
            catch (NumberFormatException nfe){
                nfe.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Ошибка");
                alert.setTitle("Ошибка");
                alert.setContentText("Введите число.");
                alert.showAndWait();
            }
        });

        btnCancel.setOnMouseClicked((event -> {
            Stage stage = (Stage)btnCancel.getScene().getWindow();
            stage.close();
        }));

        btnLoadFile.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выберите файл");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Excel files","*.xls")
            );
            File selectedFile = fileChooser.showOpenDialog(btnLoadFile.getScene().getWindow());
            String filePath = selectedFile.getAbsolutePath();
            System.out.println(filePath);
            if(selectedFile != null){
                try {
                    createHypergraphFromFile(filePath);
                    if(checkAndShowAlerts()){
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/fxml/loadResultWindow.fxml"));
                        Parent root = loader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Результаты загрузки");
                        stage.setScene(new Scene(root));
                        stage.setResizable(false);
                        stage.show();
                        LoadResultWindowController controller = loader.getController();
                        controller.setHypergraph(hypergraph);
                        controller.setEvents();
                        controller.setupWindow();
                        Stage thisStage = (Stage) btnLoadFile.getScene().getWindow();
                        thisStage.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                catch (InvalidFormatException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createHypergraphFromFile(String filepath) throws IOException, InvalidFormatException {
        List<List<String>> tableData = ExcelWorker.parse(filepath);
        hypergraph = new WeightedHypergraph();

        List<String> hypergraphInfo = tableData.get(0);
        int vertCount = Integer.parseInt(hypergraphInfo.get(0));
        int propCount = Integer.parseInt(hypergraphInfo.get(1));
        int edgeCount = Integer.parseInt(hypergraphInfo.get(2));
        int weightCount = Integer.parseInt(hypergraphInfo.get(3));

        List<Vert> verts = new ArrayList<Vert>();
        for(int i = 0; i < vertCount; i++)
            verts.add(new Vert(i+1));

        hypergraph.setVerts(verts);

        List<Proportion> props = new ArrayList<>();
        for(int i = 1; i < 1 + propCount; i++){
            List<Vert> propVerts = new ArrayList<>();
            for(String s:tableData.get(i)){
                propVerts.add(hypergraph.getVertWithNumber(Integer.parseInt(s)));
            }
            props.add(new Proportion(propVerts,i));
        }

        hypergraph.setProps(props);

        List<Edge> edges = new ArrayList<>();
        for(int i = 1 + propCount; i < 1 + propCount + edgeCount; i++){
            List<Vert> edgeVerts = new ArrayList<>();
            double[] weight = new double[weightCount];
            int count = 0;
            int number = Integer.parseInt(tableData.get(i).get(0));
            for(int j = 1; j < propCount + 1; j++) {
                edgeVerts.add(hypergraph.getVertWithNumber(Integer.parseInt(tableData.get(i).get(j))));
            }
            for(int w = propCount + 1; w < propCount + 1 + weightCount; w++){
                weight[count] = Double.parseDouble(tableData.get(i).get(w).replace(",","."));
                count++;
            }
            edges.add(new WeightedEdge(edgeVerts,number,weight));
        }

        hypergraph.setEdges(edges);

    }

    private boolean checkAndShowAlerts(){
        boolean result = true;
        if(!CheckHypergraph.checkAllVertsInProps(hypergraph)){
            AlertUtils.showHasRemainingVerts();
            result = false;
            return result;
        }
        if(!CheckHypergraph.checkWeightCountMoreThanFive(hypergraph)){
            AlertUtils.showHasMoreThanFiveWeight();
            result = false;
            return result;
        }
        if(!CheckHypergraph.checkEdgeHasMoreVertsThanProps(hypergraph)){
            AlertUtils.showEdgeHasMoreVertsThenProp();
            result = false;
            return result;
        }
        if(!CheckHypergraph.checkEdgeIsInProp(hypergraph)){
            AlertUtils.showEdgeIsInProp();
            result=false;
            return result;
        }
        if(!CheckHypergraph.checkEqualProps(hypergraph)){
            AlertUtils.showNotEqualPropsAlert();
            result = false;
            return result;
        }
        return result;
    }

    public void setHypergraph(WeightedHypergraph hypergraph){
        this.hypergraph = hypergraph;
    }
}
