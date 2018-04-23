package model.weightedhypergraph;

import java.io.IOException;

public class Main {
    public static void main(String args[]){
        WeightedHypergraph weightedHypergraph = new WeightedHypergraph();
//        weightedHypergraph.fullLoad();
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
