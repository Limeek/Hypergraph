package model.hypergraphs;

import java.util.List;

public class Vert {
    private int number;
    Vert(){};
    public Vert(int number){
        this.number=number;
    }
    private int propNumber;

    //Проверка инциндентности с ребром
    public boolean checkIncidence(Edge e){
        boolean b = false;
        for(Vert i:e.getVerts())
            if(number == i.getNumber()) {
            b = true;
            break;
        }
        return b;
    }
    //Проверка смежности с вершиной

    public boolean checkAdjacency(Vert v, List<Edge> edges){
        boolean b = false;
        for(Edge i: edges)
            if(checkIncidence(i))
                for(Vert j:i.getVerts())
                    if ((v.getNumber() == j.getNumber()) && (v.getNumber()!=number)) {
                        b = true;
                        break;
                    }
        return b;
    }


    public int getNumber(){
        return number;
    }
    public int getPropNumber() {return propNumber;}

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Вершина № " + this.number;
    }
}
