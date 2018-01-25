package TicketTask;

import hypergraphs.Combination;
import hypergraphs.Edge;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Solution {
    private List<Combination> combs;

    Solution(List<Combination> combs){
        this.combs = combs;
    }

    public void makeSolution() {
        List<String> solutions = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        //maxquality
        List<Double> weightformaxquality = new ArrayList<>();
        double maxquality;
        int maxqualitycombid;
        Double sum;
        for (Combination c : combs) {
            sum = 0.0;
            edges = c.getEdges();
            for (Edge e : edges) {
                sum += ((WeightedEdge) e).getWeight()[0];
            }
            weightformaxquality.add(sum);
        }
        maxquality = Collections.max(weightformaxquality);
        maxqualitycombid = weightformaxquality.indexOf(maxquality);
        if (maxqualitycombid == -1) {
            System.out.println("Все решения имеют одинаковое макс.качество: " + maxquality);
        } else {
            System.out.println("Макс качество: " + maxquality + " " + combs.get(maxqualitycombid));
        }


        //mintechtime
        List<Double> techtimemin = new ArrayList<>();
        double techtime;
        int mintechtimeid;
        for (Combination c : combs) {
            sum = 0.0;
            edges = c.getEdges();
            for (Edge e : edges) {
                sum += (((WeightedEdge) e).getWeight()[1]);
            }
            sum = sum / c.getEdges().size();
            techtimemin.add(sum);
        }
        techtime = Collections.min(techtimemin);
        mintechtimeid = techtimemin.indexOf(techtime);

            System.out.println("Мин. время обработки сервером: " + techtime + " " + combs.get(mintechtimeid));

        //minoveralltime
        List<Double> overalltimemin = new ArrayList<>();
        double overalltime;
        int minoveralltimeid;
        for (Combination c : combs) {
            sum = 0.0;
            edges = c.getEdges();
            for (Edge e : edges) {
                sum += (((WeightedEdge) e).getWeight()[2]);
            }
            sum = sum / c.getEdges().size();
            overalltimemin.add(sum);
        }
        overalltime = Collections.min(overalltimemin);
        minoveralltimeid = overalltimemin.indexOf(overalltime);
        System.out.println("Мин. общее время обработки: " + overalltime + " " + combs.get(minoveralltimeid));

    }
    public List<Combination> getCombs() {
        return combs;
    }
}
