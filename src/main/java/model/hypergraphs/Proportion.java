package model.hypergraphs;

import java.util.ArrayList;
import java.util.List;

public class Proportion {

    private List<Vert> verts;
    private int number;

    public Proportion(List<Vert> verts, int number){
        this.verts=verts;
        this.number=number;
    }
    public Proportion(int number){
        verts = new ArrayList<Vert>();
        this.number = number;
    }

    public List<Vert> getVerts(){
        return verts;
        }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setVerts(List<Vert> verts) {
        this.verts = verts;
    }

    @Override
    public String toString() {
        String tmpString = "";
        for(Vert v : this.verts){
            tmpString += v.getNumber() + " ";
        }
        return "Доля №" + this.number + " Вершины " + tmpString;
    }
}
