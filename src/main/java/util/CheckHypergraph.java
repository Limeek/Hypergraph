package util;

import model.hypergraphs.Edge;
import model.hypergraphs.Proportion;
import model.hypergraphs.Vert;
import model.weightedhypergraph.WeightedEdge;
import model.weightedhypergraph.WeightedHypergraph;

import java.util.ArrayList;
import java.util.List;

public class CheckHypergraph {
    public static boolean checkEqualProps(WeightedHypergraph hypergraph){
        boolean result = true;
        for(int i = 1; i< hypergraph.getProps().size(); i++){
            if (hypergraph.getProps().get(i-1).getVerts().size() != hypergraph.getProps().get(i).getVerts().size()) {
                result = false;
                break;
            }
        }
        return result;
    }

    public static boolean checkAllVertsInProps(WeightedHypergraph hypergraph) {
        boolean result = true;
        List<Vert> propVerts = new ArrayList<>();
        for(Proportion p: hypergraph.getProps()){
            for(Vert v: p.getVerts()){
                propVerts.add(v);
            }
        }
        if(!hypergraph.getVerts().equals(propVerts)) result = false;
        return result;
    }

    public static boolean checkEdgeIsInProp(WeightedHypergraph hypergraph){
        boolean result = true;
        int count;
        cycle:
        for(Edge e : hypergraph.getEdges()) {
            for (Proportion p : hypergraph.getProps()) {
                count = 0;
                for (Vert v : e.getVerts()) {
                    for (Vert v1 : p.getVerts()) {
                        if (v.equals(v1)) count++;
                    }
                    if (count > 1) {
                        result = false;
                        break cycle;
                    }
                }
            }
        }
        return result;
    }

    public static boolean checkEdgeHasMoreVertsThanProps(WeightedHypergraph hypergraph){
        boolean result = true;
        for(Edge e: hypergraph.getEdges()){
            if (e.getVerts().size() != hypergraph.getProps().size()) result = false;
        }
        return result;
    }

    public static boolean checkWeightCountMoreThanFive(WeightedHypergraph hypergraph){
       boolean result = true;
       if(((WeightedEdge)hypergraph.getEdges().get(0)).getWeight().length > 5) result = false;
       return result;
    }
}
