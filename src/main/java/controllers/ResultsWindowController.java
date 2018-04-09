package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.weightedhypergraph.Solution;
import model.weightedhypergraph.WeightedHypergraph;

import java.util.List;

public class ResultsWindowController {

    @FXML
    Button btnExit;
    @FXML
    GridPane gridPane;
    WeightedHypergraph hypergraph;

    @FXML
    public void initialize(){

    }

    public void setEvents(){


        btnExit.setOnMouseClicked(event -> {
            Stage stage = (Stage) btnExit.getScene().getWindow();
            stage.close();
        });
    }

    public void setupWindow() {
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);

        Solution solution = hypergraph.getSolution();

        List<List<Double>> result = solution.calculateSolution();

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

    public void setHypergraph(WeightedHypergraph hypergraph) {
        this.hypergraph = hypergraph;
    }
}
