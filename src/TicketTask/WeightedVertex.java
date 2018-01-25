package TicketTask;

import hypergraphs.Vertex;

import java.util.ArrayList;
import java.util.List;

public class WeightedVertex extends Vertex {
    List<Double> weight = new ArrayList<>();

    WeightedVertex(int number, List<Double> weight){
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
