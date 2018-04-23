package model.weightedhypergraph;

import model.hypergraphs.Edge;
import model.hypergraphs.Vert;

import java.util.List;

public class WeightedEdge extends Edge {
    private double[] weight;

    public WeightedEdge(List<Vert> verts, int number){
        super(verts,number);
        weight=new double[3];
    }

    public WeightedEdge(List<Vert> verts,int number,int weightCount){
        super(verts, number);
        weight = new double[weightCount];
    }

    public  WeightedEdge(List<Vert> verts,int number,double[] weight){
        super(verts,number);
        this.weight = weight;
    }

    public double[] getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        StringBuilder strweight = new StringBuilder();
        for(Double d: weight)
            strweight.append(d).append(" ");
        return super.toString() + " Веса " + strweight.toString();
    }

}
