package model.hypergraphs;


import java.util.Comparator;

public class NumberCompVerts implements Comparator<Vert>{
    public int compare(Vert o1, Vert o2){
        return o1.getNumber() - o2.getNumber();
    }
}