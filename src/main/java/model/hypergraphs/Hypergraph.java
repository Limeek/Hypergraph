package model.hypergraphs;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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

    public Vert getVertWithNumber(List<Vert> verts, int j){
        Vert result = null;
        for(Vert v : verts) {
            if (v.getNumber() == j) {
                result = v;
                break;
            }
        }
        return result;
    }

    public void fullLoad(){

        File file= new File("input3.txt");
        BufferedReader bufferedReader= null;
        String text=null;
        List<String> content = new ArrayList<String>();

        try{
            bufferedReader=new BufferedReader(new FileReader(file));
            while((text=bufferedReader.readLine()) != null){
                content.add(text);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        for(int i=0;i<Integer.valueOf(content.get(0));i++)
            verts.add(new Vert(i+1));

        for(int i=2;i<Integer.valueOf(content.get(1))+2;i++){
            List<String> tmp=new ArrayList<>(Arrays.asList((content.get(i).split(" "))));
            List<Vert> tmpVert= new ArrayList<>();
            int n = Integer.valueOf(tmp.get(0));
            tmp.remove(0);
            for(int j=0;j<tmp.size();j++)
                tmpVert.add(getVertWithNumber(verts,Integer.valueOf(tmp.get(j))));
            edges.add(new Edge(tmpVert,n));
        }
        verts.sort(new NumberCompVerts());
    }

    public void partLoad(){
        File file = new File("inputprops.txt");
        BufferedReader bufferedReader= null;
        String text=null;
        List<String> content = new ArrayList<String>();

        try{
            bufferedReader=new BufferedReader(new FileReader(file));
            while((text=bufferedReader.readLine()) != null){
                content.add(text);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        for(int i=0;i<Integer.valueOf(content.get(0));i++)
            verts.add(new Vert(i+1));

        for(int i=2;i<Integer.valueOf(content.get(1))+2;i++){
            List<String> tmp=new ArrayList<>(Arrays.asList((content.get(i).split(" "))));
            List<Vert> tmpVert= new ArrayList<>();
            int n = Integer.valueOf(tmp.get(0));
            tmp.remove(0);
            for(int j=0;j<tmp.size();j++)
                tmpVert.add(getVertWithNumber(verts,Integer.valueOf(tmp.get(j))));
            props.add(new Proportion(tmpVert,n));
        }
    }

    public void buildEdges(){
        int number = 1;
        List<Vert> tmpVert = new ArrayList<>();
        for(Vert v: props.get(0).getVerts()){
            tmpVert.clear();
            Proportion workProp = props.get(1);
            tmpVert.add(v);
            recBuildEdges(number,tmpVert,workProp);
        }
        for(Edge e : edges) {
            e.setNumber(number);
            number++;
        }
        System.out.println("Edge count " + edges.size() );
    }
    public void recBuildEdges(int number, List<Vert> tmpVert, Proportion workProp){
        List<Vert> instanceOfVerts = new ArrayList<>();
        instanceOfVerts.addAll(tmpVert);
        for (Vert v : workProp.getVerts()){
            tmpVert.clear();
            tmpVert.addAll(instanceOfVerts);
            tmpVert.add(v);
            if(tmpVert.size() != props.size()){
                Proportion newWorkProp;
                newWorkProp = props.get(props.indexOf(workProp) + 1);
                recBuildEdges(number,tmpVert,newWorkProp);
            }
            else{
                edges.add(new Edge(new ArrayList<Vert>(tmpVert),number));
            }
        }
    }

    public void makeMatrixOfAdj(){                                                                                      //метод создания матрицы смежности
        matrixofadj = new int[edges.size()][edges.size()];
        for (int i = 0; i < edges.size(); i++)
            for (int j = 0; j < edges.size(); j++) {
                if (edges.get(i).checkAdjacency(edges.get(j))) matrixofadj[i][j] = 1;
                if (!edges.get(i).checkAdjacency(edges.get(j))) matrixofadj[i][j] = -1;
                if (i == j) matrixofadj[i][j] = 0;
            }
    }

    public void makecombs() {                                                                                           //метод создания сочетаний
       List<Integer>[] simp = new ArrayList[edges.size()];

       for (int i = 0; i < simp.length; i++)                                                                            //
           simp[i] = new ArrayList<Integer>();                                                                          //
                                                                                                                        //  Формирование упрощенной матрицы смежности
       for (int i = 0; i < edges.size(); i++)                                                                           //
           for (int j = 0; j < edges.size(); j++)                                                                       //
               if (matrixofadj[i][j] == -1) simp[i].add(j);                                                             //


       List<Integer> tmp = new ArrayList<>();
       List<Integer> index = new ArrayList<>();
       List<List<Integer>> edgesofcomb = new ArrayList<>();

       for(int i=0;i<simp.length;i++){                                                                                  //
           index.removeAll(index);                                                                                      //
           tmp.removeAll(tmp);                                                                                          //
           tmp.add(i);                                                                                                  //  Запуск алгоритма по поиску сочетаний
                                                                                                                        //
           for(int i1=0;i1<simp[i].size();i1++){                                                                        //
               index.add(simp[i].get(i1));                                                                              //
           }
           rt(simp,tmp,index,edgesofcomb);                                                                              //
       }                                                                                                                //
       if(combs.size()!=1)
       for(int i=0;i<combs.size();i++)                                                                                  // Удаление повторяющихся сочетаний
          for(int i1=0;i1<combs.size();i1++)
            if(((combs.get(i).getEdges().equals(combs.get(i1).getEdges())) && i!=i1) || combs.get(i).getEdges().size() == 1) combs.remove(i);

       for(int i=0;i<combs.size();i++)                                                                                  // Присваивание номеров сочетаниям
           combs.get(i).setNumber(i+1);
   }

    private void rt(List<Integer>[] simp,List<Integer> tmp,List<Integer> index,List<List<Integer>> edgesofcomb){         //Рекурсивный метод для пробега по упрощенной матрице смежности
        edgesofcomb.add(tmp);
        combs.add(new Combination(edgesofcomb.get(edgesofcomb.size()-1),edges));
        combs.get(combs.size()-1).sortEdges();

        for(int i=0;i<index.size();++i){
            List<Integer> tmpindex=new ArrayList<>();
            List<Integer> copy= new ArrayList<>();
            List<Integer> tmpp ;
            if (simp[index.get(i)].containsAll(tmp)){                                                                   // Проверка основого условия
                copy.addAll(simp[index.get(i)]);
                tmpp=new ArrayList<>(tmp);
                tmpp.add(index.get(i));
                copy.removeAll(tmp);
                for(int i1=0;i1<copy.size();i1++)
                    tmpindex.add(copy.get(i1));
                rt(simp,tmpp,tmpindex,edgesofcomb);
            }
        }
    }

    public void calcPerfCombs(){
        List<Vert> tmpVerts = new ArrayList<>();
        Boolean tmpFlag = false;
        for(Combination c : combs){
            tmpVerts.clear();
            for (Edge e: c.getEdges())
                for (Vert v: e.getVerts())
                    if(!tmpVerts.contains(v)) tmpVerts.add(v);
            tmpVerts.sort(new NumberCompVerts());
            for(int i = 0 ; i < verts.size(); i++){
                if(verts.get(i).equals(tmpVerts.get(i))) tmpFlag = true;
                else {tmpFlag = false; break;}
            }
            if (tmpFlag == true) perfCombs.add(c);
        }
    }

    public void printinfo() throws IOException {                                                                        // Метод для проверки полученных данных
        File outputfile = new File("output.txt");
        FileWriter fileWriter = new FileWriter(outputfile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write("Список вершин: \n");
        for(Vert v : verts){
            bufferedWriter.write(v.toString() + "\n");
        }

        bufferedWriter.write("\n");

        bufferedWriter.write("Список ребер: \n");
        for(Edge e: edges) {
            bufferedWriter.write(e.toString());
            bufferedWriter.write("\n");
        }
        bufferedWriter.write("\n");

        bufferedWriter.write("Сочетания : \n");
        for(Combination c : combs){
            bufferedWriter.write(c.toString());
            bufferedWriter.write("\n");
        }
        bufferedWriter.write("\n");

        bufferedWriter.write("Совершенные сочетания : \n");
        for(Combination c : perfCombs){
            bufferedWriter.write(c.toString());
            bufferedWriter.write("\n");
        }

        bufferedWriter.close();
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
