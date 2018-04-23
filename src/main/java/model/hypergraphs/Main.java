package model.hypergraphs;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;

public class Main {
    public static void main(String args[]){
        System.out.println(Time.valueOf(LocalTime.now()));
        Hypergraph h=new Hypergraph();
//        h.fullLoad();
        h.buildEdges();
        h.makeMatrixOfAdj();
        h.makecombs();
        h.calcPerfCombs();
        try {
            h.printinfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Time.valueOf(LocalTime.now()));
    }
}
