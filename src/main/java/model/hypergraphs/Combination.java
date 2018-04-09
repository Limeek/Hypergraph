package model.hypergraphs;

import java.util.ArrayList;
import java.util.List;

public class Combination {
    private List<Edge> edges;
    private int number;

    Combination(List<Edge> edges,int number){
        this.edges=edges;
        this.number=number;
    }
    Combination(List<Integer> integerList,List<Edge> edgeList){
        ArrayList<Edge> tmp=new ArrayList<Edge>();
        for(int i=0;i< integerList.size();i++)
            for(int j=0;j<edgeList.size();j++)
                if(integerList.get(i) == edgeList.get(j).getNumber()-1) tmp.add(edgeList.get(j));
        this.edges=tmp;
    }
    public void sortEdges(){
        edges.sort(new NumberCompEdges());
    }
    public void setNumber(int number){
        this.number = number;
    }
    public int getNumber(){
        return  number;
    }
    public List<Edge> getEdges(){
        return edges;
    }

    @Override
    public String toString() {
        String stredges = new String();
        for(Edge e : edges)
            stredges += e.getNumber() + " ";
        return "Сочетание № " + number + " Ребра: " + stredges;
    }
}
