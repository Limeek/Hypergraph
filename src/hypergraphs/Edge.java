package hypergraphs;

import java.util.ArrayList;
import java.util.List;

public class Edge {
    private List<Vertex> verts;
    private int number;

    protected Edge(List<Vertex> verts,int number){
        this.number=number;
        this.verts=verts;
    }
    //Проверка смежности с ребром
    public boolean checkAdjacency(Edge e){
        boolean b = false;
        for(Vertex i: verts)
            for(Vertex j: e.getVerts())
                if (i.getNumber() == j.getNumber())
                    b = true;
        return b;
    }
    public List<Vertex> getVerts(){
        return verts;
    }
    public int getNumber(){
        return number;
    }

    @Override
    public String toString() {
        String strverts = new String();
        for(Vertex v : verts)
            strverts += v.getNumber() + " ";
        return "Ребро № " + this.number + " Вершины: " + strverts;
    }
}



