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

    public void calculateWeight(){
        weight[0] = ((WeightedVert) getVerts().get(0)).getWeight().get(0) * ((WeightedVert) getVerts().get(2)).getWeight().get(0);
        weight[1] = ((WeightedVert) getVerts().get(1)).getWeight().get(0);
        weight[2] = ((WeightedVert) getVerts().get(1)).getWeight().get(0) + ((WeightedVert) getVerts().get(2)).getWeight().get(1);
    }

    public double[] getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        String strweight = "";
        for(Double d: weight)
            strweight += d + " ";
        return super.toString() + " Веса " + strweight;
    }

}
