package TicketTask;

import hypergraphs.Edge;
import hypergraphs.Vertex;

import java.util.List;

public class WeightedEdge extends Edge {
    private double[] weight;

    WeightedEdge(List<Vertex> verts, int number){
        super(verts,number);
        weight=new double[3];
    }

    public void calculateWeight(){
        weight[0] = ((WeightedVertex) getVerts().get(0)).getWeight().get(0) * ((WeightedVertex) getVerts().get(2)).getWeight().get(0);
        weight[1] = ((WeightedVertex) getVerts().get(1)).getWeight().get(0);
        weight[2] = ((WeightedVertex) getVerts().get(1)).getWeight().get(0) + ((WeightedVertex) getVerts().get(2)).getWeight().get(1);
    }

    public double[] getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        String strweight;
        strweight = weight[0] +" "+ weight[1] +" "+ weight[2];
        return super.toString() + " Веса " + strweight;
    }
}
