package hypergraphs;

import java.io.IOException;

public class Main {
    public static void main(String args[]){
        Hypergraph h=new Hypergraph();
        h.load();
        h.makeMatrixOfAdj();
        h.makecombs();
        h.calcPerfCombs();
        try {
            h.printinfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
