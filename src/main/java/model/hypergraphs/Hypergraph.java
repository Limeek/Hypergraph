package model.hypergraphs;

import java.util.ArrayList;
import java.util.List;

public class Hypergraph {
    private List<Vert> verts;
    private List<Edge> edges;
    private List<Combination> combs;
    private List<Combination> perfCombs;
    private List<Proportion> props;
    private int[][] matrixofadj;

    protected Hypergraph(){
        verts = new ArrayList<Vert>();
        edges = new ArrayList<Edge>();
        combs = new ArrayList<Combination>();
        perfCombs = new ArrayList<Combination>();
        props = new ArrayList<Proportion>();
    }

    public Vert getVertWithNumber(int number){
        Vert result = null;
        for(Vert v : verts) {
            if (v.getNumber() == number) {
                result = v;
                break;
            }
        }
        return result;
    }

    public void buildEdges(){
        int number = 1;
        List<Vert> vertsOfEdge = new ArrayList<>();
        for(Vert v: props.get(0).getVerts()){
            vertsOfEdge.clear();
            Proportion workProp = props.get(1);
            vertsOfEdge.add(v);
            recBuildEdges(number,vertsOfEdge,workProp);
        }
        for(Edge e : edges) {
            e.setNumber(number);
            number++;
        }
    }

    private void recBuildEdges(int number, List<Vert> vertsOfEdge, Proportion workProp){
        List<Vert> instanceOfVerts = new ArrayList<>();
        instanceOfVerts.addAll(vertsOfEdge);
        for (Vert v : workProp.getVerts()){
            vertsOfEdge.clear();
            vertsOfEdge.addAll(instanceOfVerts);
            vertsOfEdge.add(v);
            if(vertsOfEdge.size() != props.size()){
                Proportion newWorkProp;
                newWorkProp = props.get(props.indexOf(workProp) + 1);
                recBuildEdges(number,vertsOfEdge,newWorkProp);
            }
            else{
                edges.add(new Edge(new ArrayList<Vert>(vertsOfEdge),number));
            }
        }
    }

    public void makeMatrixOfAdj(){
        matrixofadj = new int[edges.size()][edges.size()];
        for (int i = 0; i < edges.size(); i++)
            for (int j = 0; j < edges.size(); j++) {
                if (edges.get(i).checkAdjacency(edges.get(j))) matrixofadj[i][j] = 1;
                if (!edges.get(i).checkAdjacency(edges.get(j))) matrixofadj[i][j] = -1;
                if (i == j) matrixofadj[i][j] = 0;
            }
    }

    public void makecombs() {
       List<Integer>[] simpleMatrixOfAdj = new ArrayList[edges.size()];

       for (int i = 0; i < simpleMatrixOfAdj.length; i++)
           simpleMatrixOfAdj[i] = new ArrayList<Integer>();

       for (int i = 0; i < edges.size(); i++)
           for (int j = 0; j < edges.size(); j++)
               if (matrixofadj[i][j] == -1) simpleMatrixOfAdj[i].add(j);


       List<Integer> futureComb = new ArrayList<>();
       List<Integer> index = new ArrayList<>();
       List<List<Integer>> combsInEdgesList = new ArrayList<>();

       for(int i=0;i<simpleMatrixOfAdj.length;i++){
           index.removeAll(index);
           futureComb.removeAll(futureComb);
           futureComb.add(i);
           for(int i1=0;i1<simpleMatrixOfAdj[i].size();i1++){
               index.add(simpleMatrixOfAdj[i].get(i1));
           }
           recBuildCombs(simpleMatrixOfAdj,futureComb,index,combsInEdgesList);
       }

       if(combs.size() != 1) {
           combs.removeIf(comb -> comb.getEdges().size() == 1);
           for (int i = 0; i < combs.size(); i++) {
               for (int i1 = 0; i1 < combs.size(); i1++) {
                   if (combs.get(i1).getEdges().equals(combs.get(i).getEdges()) && i != i1)
                       combs.remove(combs.get(i1));
               }
           }
       }

       for(int i=0;i<combs.size();i++) {
           combs.get(i).setNumber(i + 1);
       }
   }

    private void recBuildCombs(List<Integer>[] simpleMatrixOfAdj, List<Integer> futureComb, List<Integer> index, List<List<Integer>> combsInEdgesList){
        combsInEdgesList.add(futureComb);
        combs.add(new Combination(combsInEdgesList.get(combsInEdgesList.size()-1),edges));
        combs.get(combs.size()-1).sortEdges();

        for(int i=0;i<index.size();++i){
            List<Integer> newIndex =new ArrayList<>();
            List<Integer> copy= new ArrayList<>();
            List<Integer> newFutureComb ;
            if (simpleMatrixOfAdj[index.get(i)].containsAll(futureComb)){
                copy.addAll(simpleMatrixOfAdj[index.get(i)]);
                newFutureComb =new ArrayList<>(futureComb);
                newFutureComb.add(index.get(i));
                copy.removeAll(futureComb);
                for(int i1=0;i1<copy.size();i1++)
                    newIndex.add(copy.get(i1));
                recBuildCombs(simpleMatrixOfAdj,newFutureComb,newIndex,combsInEdgesList);
            }
        }
    }

    public void calcPerfCombs(){
        List<Vert> tmpVerts = new ArrayList<>();
        Boolean isPerfectComb = false;
        for(Combination c : combs){
            tmpVerts.clear();
            for (Edge e: c.getEdges())
                for (Vert v: e.getVerts())
                    if(!tmpVerts.contains(v)) tmpVerts.add(v);
            tmpVerts.sort(new NumberCompVerts());
            for(int i = 0 ; i < verts.size(); i++){
                if(verts.get(i).equals(tmpVerts.get(i))) isPerfectComb = true;
                else {isPerfectComb = false; break;}
            }
            if (isPerfectComb) perfCombs.add(c);
        }
    }

    public String hypergraphInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append("Количество вершин: " + verts.size());
        sb.append("\nДоли: \n");
        for(Proportion p:props){
            sb.append(p.toString() + "\n");
        }
        sb.append("Ребра \n");
        for(Edge e:edges){
            sb.append(e.toString() + "\n");
        }
        return sb.toString();
    }

    public void hypergraphClear(){
        this.verts.clear();
        this.combs.clear();
        this.edges.clear();
        this.matrixofadj = null;
        this.perfCombs.clear();
        this.props.clear();
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Vert> getVerts() {
        return verts;
    }

    public List<Proportion> getProps() {
        return props;
    }

    public List<Combination> getCombs() {
        return combs;
    }

    public List<Combination> getPerfCombs() {
        return perfCombs;
    }

    public int[][] getMatrixofadj() {
        return matrixofadj;
    }

    public void setCombs(List<Combination> combs) {
        this.combs = combs;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public void setMatrixofadj(int[][] matrixofadj) {
        this.matrixofadj = matrixofadj;
    }

    public void setPerfCombs(List<Combination> perfCombs) {
        this.perfCombs = perfCombs;
    }

    public void setProps(List<Proportion> props) {
        this.props = props;
    }

    public void setVerts(List<Vert> verts) {
        this.verts = verts;
    }
}
