package model.hypergraphs;

import java.util.Comparator;

public class NumberCompEdges implements Comparator<Edge> {
    @Override
    public int compare(Edge o1, Edge o2) {
        return  o1.getNumber() - o2.getNumber();
    }
}

