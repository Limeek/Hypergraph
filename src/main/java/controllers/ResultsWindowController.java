package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.hypergraphs.Combination;
import model.hypergraphs.Edge;
import model.hypergraphs.Proportion;
import model.weightedhypergraph.Solution;
import model.weightedhypergraph.VectorFunction;
import model.weightedhypergraph.WeightedHypergraph;
import util.ExcelWorker;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultsWindowController {

    @FXML
    Button btnExit;
    @FXML
    Button btnSave;
    @FXML
    GridPane gridPane;
    WeightedHypergraph hypergraph;
    List<List<Double>> result;


    public void setEvents(){
        btnSave.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выберите файл");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Excel files","*.xls")
            );

            File file = fileChooser.showSaveDialog(btnSave.getScene().getWindow());
            String path = file.getAbsolutePath();

            if(file != null){
                ExcelWorker.createFile(makeTableForExcel(),path,calcSolutionValuesForExcel());
            }
        });

        btnExit.setOnMouseClicked(event -> {
            Stage stage = (Stage) btnExit.getScene().getWindow();
            stage.close();
        });
    }

    public void setupWindow() {
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);

        Solution solution = hypergraph.getSolution();

        result = solution.calculateSolution();

        for (int j = 0; j <= solution.getFuncList().size(); j++)
            gridPane.getColumnConstraints().add(new ColumnConstraints(50));
        for (int i = 0; i < solution.getCombs().size(); i++) {
            Label label = new Label("x" + String.valueOf(i + 1));
            label.setStyle("font-size:20pt");
            gridPane.add(label, 0, i + 1);
        }
        for (int j = 0; j < solution.getFuncList().size(); j++) {
            Label label1 = new Label("F" + String.valueOf(solution.getFuncList().get(j).getNumber()));
            label1.setStyle("font-size:20pt");
            label1.setWrapText(true);
            gridPane.add(label1, j + 1, 0);

            double reqDouble = solution.getNeededDouble(solution.getFuncList().get(j), result.get(j));

            for (int i = 0; i < solution.getCombs().size(); i++) {
                Label label = new Label(String.valueOf(result.get(j).get(i)));
                label.setStyle("font-size:14pt");
                label.setWrapText(true);
                if (result.get(j).get(i) == reqDouble)
                    label.setStyle("-fx-background-color:red;");
                if (i == 0) gridPane.add(label, j + 1, 1);
                else
                    gridPane.add(label, j + 1, i + 1);
            }
        }
    }
    public List<List<String>> makeTableForExcel(){
        List<String> funcHeaders = new ArrayList<>();
        funcHeaders.add("");
        for(int i = 0; i < result.size(); i++){
            funcHeaders.add("F" + hypergraph.getSolution().getFuncList().get(i).getNumber());
        }
        List<List<String>> stringsList = new ArrayList<>();
        stringsList.add(funcHeaders);

        for(int i=0;i<result.get(0).size();i++){
            List<String> stringList = new ArrayList<>();
            stringList.add("x"+ (i + 1));
            for (int j =0 ; j< result.size(); j++){
                stringList.add(String.valueOf(result.get(j).get(i)));
            }
            stringsList.add(stringList);
        }

        stringsList.add(new ArrayList<>(Arrays.asList("")));

        stringsList.add(new ArrayList<>(Arrays.asList("Количество вершин: " + hypergraph.getVerts().size())));

        stringsList.add(new ArrayList<>(Arrays.asList("Доли: ")));
        for(Proportion p : hypergraph.getProps()){
            stringsList.add(new ArrayList<>(Arrays.asList(p.toString())));
        }

        stringsList.add(new ArrayList<>(Arrays.asList("Ребра: ")));
        for(Edge e : hypergraph.getEdges()){
              stringsList.add(new ArrayList<>(Arrays.asList(e.toString())));
        }

        stringsList.add(new ArrayList<>(Arrays.asList("Совершенные сочетания: ")));
        for(Combination c : hypergraph.getPerfCombs()){
              stringsList.add(new ArrayList<>(Arrays.asList(c.toString())));
        }

        stringsList.add(new ArrayList<>(Arrays.asList("ВЦФ: ")));
        for(VectorFunction f: hypergraph.getSolution().getFuncList())
            stringsList.add(new ArrayList<>(Arrays.asList(f.toString())));

        return stringsList;
    }
    public List<Integer> calcSolutionValuesForExcel(){
        List<Integer> neededValues = new ArrayList<>();
        for(int j = 0; j < result.size(); j++){
            for(int i = 0; i < result.get(0).size(); i++){
                if(result.get(j).get(i) == hypergraph.getSolution().getNeededDouble(hypergraph.getSolution().getFuncList().get(j),result.get(j)))
                    neededValues.add(i);
            }
        }
        return neededValues;
    }
    public void setHypergraph(WeightedHypergraph hypergraph) {
        this.hypergraph = hypergraph;
    }
}
