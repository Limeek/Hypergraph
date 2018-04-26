package model.weightedhypergraph;

import model.hypergraphs.Hypergraph;

public class WeightedHypergraph extends Hypergraph {
    private Solution solution;

    public WeightedHypergraph(){
        super();
    }

    @Override
    public void hypergraphClear() {
        super.hypergraphClear();
        this.solution = null;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

}
