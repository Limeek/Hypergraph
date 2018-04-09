package model.weightedhypergraph;

import model.hypergraphs.Edge;
import java.io.IOException;

public class Main {
    public static void main(String args[]){
        WeightedHypergraph weightedHypergraph = new WeightedHypergraph();
        weightedHypergraph.fullLoad();
        for(Edge e : weightedHypergraph.getEdges()){
            ((WeightedEdge) e).calculateWeight();
        }
        weightedHypergraph.makeMatrixOfAdj();
        weightedHypergraph.makecombs();
        weightedHypergraph.calcPerfCombs();
        try {
            weightedHypergraph.printinfo();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
