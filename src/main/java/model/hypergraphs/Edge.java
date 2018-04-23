package model.hypergraphs;

import java.util.List;

public class Edge {
    private List<Vert> verts;
    private int number;

    protected Edge(List<Vert> verts, int number){
        this.number=number;
        this.verts=verts;
    }
    //Проверка смежности с ребром
    public boolean checkAdjacency(Edge e){
        boolean b = false;
        for(Vert i: verts)
            for(Vert j: e.getVerts())
                if (i.getNumber() == j.getNumber())
                    b = true;
        return b;
    }
    public List<Vert> getVerts(){
        return verts;
    }
    public int getNumber(){
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public void sortVerts(){
        verts.sort(new NumberCompVerts());
    }
    @Override
    public String toString() {
        StringBuilder vertsString = new StringBuilder();
        for(Vert v : verts)
            vertsString.append(v.getNumber()).append(" ");
        return "Ребро № " + this.number + " Вершины: " + vertsString.toString();
    }
}




