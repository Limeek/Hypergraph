package model.weightedhypergraph;


import model.hypergraphs.Vert;

import java.util.ArrayList;
import java.util.List;

public class WeightedVert extends Vert {
    List<Double> weight = new ArrayList<>();

    WeightedVert(int number, List<Double> weight){
        super(number);
        this.weight = weight;
    }

    public List<Double> getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        String tmpstr = null;
        if (weight.size() == 1) tmpstr = " Вес: " + this.weight.get(0);
        if (weight.size() > 1){
            tmpstr = " Веса : ";
            for(Double w : weight)
                tmpstr += w +" ";
        }
        return super.toString() + tmpstr;
    }
}
