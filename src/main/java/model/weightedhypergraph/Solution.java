package model.weightedhypergraph;

import model.hypergraphs.Combination;
import model.hypergraphs.Edge;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
    private List<Combination> combs;
    private List<VectorFunction> funcList;

    public Solution(){
        funcList = new ArrayList<>();
    }

    public Solution(List<Combination> combs){
        this.combs = combs;
    }

    public List<List<Double>> calculateSolution(){
        List<List<Double>> result = new ArrayList<>();
        for (VectorFunction v : funcList) {
            String name = v.getName();
            switch (name) {
                case "MAXSUM":
//                    {
//                    List<Double> sum = new ArrayList<>();
//                    double thissum = 0;
//                    for (Combination c : combs) {
//                        for (Edge e : c.getEdges()) {
//                            thissum += ((WeightedEdge) e).getWeight()[v.getWeightNumber()];
//                        }
//                        sum.add(thissum);
//                        thissum = 0;
//                    }
//                    result.add(sum);
//                    break;
//                }

                case "MINSUM": {
                    List<Double> sum = new ArrayList<>();
                    double thissum = 0;
                    for (Combination c : combs) {
                        for (Edge e : c.getEdges()) {
                            thissum += ((WeightedEdge) e).getWeight()[v.getWeightNumber()];
                        }
                        sum.add(thissum);
                        thissum = 0;
                    }
                    result.add(sum);
                    break;
                }

                case "MAXAVG":
//                    {
//                    List<Double> avg = new ArrayList<>();
//                    double thissum = 0;
//                    for (Combination c : combs) {
//                        for (Edge e : c.getEdges()) {
//                            thissum += ((WeightedEdge) e).getWeight()[v.getWeightNumber()];
//                        }
//                        avg.add(thissum/c.getEdges().size());
//                        thissum = 0;
//                    }
//                    result.add(avg);
//                    break;
//                }

                case "MINAVG": {
                    List<Double> avg = new ArrayList<>();
                    double thissum = 0;
                    for (Combination c : combs) {
                        for (Edge e : c.getEdges()) {
                            thissum += ((WeightedEdge) e).getWeight()[v.getWeightNumber()];
                        }
                        avg.add(thissum/c.getEdges().size());
                        thissum = 0;
                    }
                    result.add(avg);
                    break;
                }

                case "MAXMAX":
//                    {
//                    List<Double> max = new ArrayList<>();
//                    double maxvalue = 0;
//                    for (Combination c : combs) {
//                        maxvalue = ((WeightedEdge)c.getEdges().get(0)).getWeight()[v.getWeightNumber()];
//                        for (int i = 1 ; i < c.getEdges().size(); i++) {
//                            if(((WeightedEdge) c.getEdges().get(i)).getWeight()[v.getWeightNumber()] > maxvalue)
//                                maxvalue = ((WeightedEdge) c.getEdges().get(i)).getWeight()[v.getWeightNumber()];
//                        }
//                        max.add(maxvalue);
//                        maxvalue = 0;
//                    }
//                    result.add(max);
//                    break;
//                }

                case "MINMAX": {
                    List<Double> max = new ArrayList<>();
                    double maxvalue = 0;
                    for (Combination c : combs) {
                        maxvalue = ((WeightedEdge)c.getEdges().get(0)).getWeight()[v.getWeightNumber()];
                        for (int i = 1 ; i < c.getEdges().size(); i++) {
                            if(((WeightedEdge) c.getEdges().get(i)).getWeight()[v.getWeightNumber()] > maxvalue)
                                maxvalue = ((WeightedEdge) c.getEdges().get(i)).getWeight()[v.getWeightNumber()];
                        }
                        max.add(maxvalue);
                        maxvalue = 0;
                    }
                    result.add(max);
                    break;
                }

                case "MINMIN":
//                    {
//                    List<Double> min = new ArrayList<>();
//                    double minvalue = 0;
//                    for (Combination c : combs) {
//                        minvalue = ((WeightedEdge)c.getEdges().get(0)).getWeight()[v.getWeightNumber()];
//                        for (int i = 1 ; i < c.getEdges().size(); i++) {
//                            if(((WeightedEdge) c.getEdges().get(i)).getWeight()[v.getWeightNumber()] < minvalue)
//                                minvalue = ((WeightedEdge) c.getEdges().get(i)).getWeight()[v.getWeightNumber()];
//                        }
//                        min.add(minvalue);
//                        minvalue = 0;
//                    }
//                    result.add(min);
//                    break;
//                }

                case "MAXMIN": {
                    List<Double> min = new ArrayList<>();
                    double minvalue = 0;
                    for (Combination c : combs) {
                        minvalue = ((WeightedEdge)c.getEdges().get(0)).getWeight()[v.getWeightNumber()];
                        for (int i = 1 ; i < c.getEdges().size(); i++) {
                            if(((WeightedEdge) c.getEdges().get(i)).getWeight()[v.getWeightNumber()] < minvalue)
                                minvalue = ((WeightedEdge) c.getEdges().get(i)).getWeight()[v.getWeightNumber()];
                        }
                        min.add(minvalue);
                        minvalue = 0;
                    }
                    result.add(min);
                    break;
                }

                case "MAXPROD":
//                    {
//                    List<Double> prod = new ArrayList<>();
//                    double thisprod = 1;
//                    for (Combination c : combs) {
//                        for (Edge e : c.getEdges()) {
//                            thisprod *= ((WeightedEdge) e).getWeight()[v.getWeightNumber()];
//                        }
//                        prod.add(thisprod);
//                        thisprod = 1;
//                    }
//                    result.add(prod);
//                    break;
//                }

                case "MINPROD": {
                    List<Double> prod = new ArrayList<>();
                    double thisprod = 1;
                    for (Combination c : combs) {
                        for (Edge e : c.getEdges()) {
                            thisprod *= ((WeightedEdge) e).getWeight()[v.getWeightNumber()];
                        }
                        prod.add(thisprod);
                        thisprod = 1;
                    }
                    result.add(prod);
                    break;
                }
            }
        }

        return result;
    }

    public double getNeededDouble(VectorFunction v, List<Double> list){
        double result =0 ;
        String name = v.getName();
        switch(name) {
            case "MAXPROD":
            case "MAXMIN":
            case "MAXMAX":
            case "MAXAVG":
            case "MAXSUM": {
                double max = list.get(0);
                for(int i = 1; i<list.size();i++)
                    if(list.get(i) > max) max = list.get(i);
                result = max;
                break;
            }
            case "MINPROD":
            case "MINMIN":
            case "MINMAX":
            case "MINAVG":
            case "MINSUM":{
                double min = list.get(0);
                for(int i = 1; i<list.size();i++)
                    if(list.get(i) < min) min = list.get(i);
                result = min;
                break;
            }
        }
        return result;
    }

    public void setCombs(List<Combination> combs) {
        this.combs = combs;
    }

    public List<Combination> getCombs() {
        return combs;
    }

    public List<VectorFunction> getFuncList() {
        return funcList;
    }
}
