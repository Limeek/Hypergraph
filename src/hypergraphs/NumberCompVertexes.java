package hypergraphs;


import java.util.Comparator;

public class NumberCompVertexes implements Comparator<Vertex>{
    public int compare(Vertex o1, Vertex o2){
        return o1.getNumber() - o2.getNumber();
    }
}