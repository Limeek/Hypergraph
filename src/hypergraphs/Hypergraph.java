package hypergraphs;

import hypergraphs.Edge;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ArrayList;

public class Hypergraph {
    private ArrayList<Vertex> verts;
    private ArrayList<Edge> edges;
    private ArrayList<Combination> combs;
    private ArrayList<Combination> perfCombs;
    private ArrayList<Proportion> props;
    private int[][] matrixofadj;

    protected Hypergraph(){
        verts = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        combs = new ArrayList<Combination>();
        perfCombs = new ArrayList<Combination>();
        props = new ArrayList<Proportion>();
    }
    public Vertex getVertWithNumber(ArrayList<Vertex> verts, int j){
        Vertex result = null;
        for(Vertex v : verts) {
            if (v.getNumber() == j) {
                result = v;
                break;
            }
        }
        return result;
    }
    public void load(){

        File file= new File("input.txt");
        BufferedReader bufferedReader= null;
        String text=null;
        ArrayList<String> content = new ArrayList<String>();

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
            verts.add(new Vertex(i+1));

        for(int i=2;i<Integer.valueOf(content.get(1))+2;i++){
            ArrayList<String> tmp=new ArrayList<>(Arrays.asList((content.get(i).split(" "))));
            ArrayList<Vertex> tmpvertex= new ArrayList<>();
            int n = Integer.valueOf(tmp.get(0));
            tmp.remove(0);
            for(int j=0;j<tmp.size();j++)
                tmpvertex.add(getVertWithNumber(verts,Integer.valueOf(tmp.get(j))));
            edges.add(new Edge(tmpvertex,n));
        }
        verts.sort(new NumberCompVertexes());
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
       ArrayList<Integer>[] simp = new ArrayList[edges.size()];

       for (int i = 0; i < simp.length; i++)                                                                            //
           simp[i] = new ArrayList<Integer>();                                                                          //
                                                                                                                        //  Формирование упрощенной матрицы смежности
       for (int i = 0; i < edges.size(); i++)                                                                           //
           for (int j = 0; j < edges.size(); j++)                                                                       //
               if (matrixofadj[i][j] == -1) simp[i].add(j);                                                             //


       ArrayList<Integer> tmp = new ArrayList<>();
       ArrayList<Integer> index = new ArrayList<>();
       ArrayList<ArrayList<Integer>> edgesofcomb = new ArrayList<>();

       for(int i=0;i<simp.length;i++){                                                                                  //
           index.removeAll(index);                                                                                      //
           tmp.removeAll(tmp);                                                                                          //
           tmp.add(i);                                                                                                  //  Запуск алгоритма по поиску сочетаний
                                                                                                                        //
           for(int i1=0;i1<simp[i].size();i1++){                                                                        //
               index.add(simp[i].get(i1));                                                                              //
           }                                                                                                            //
           rt(simp,tmp,index,edgesofcomb);                                                                              //
       }                                                                                                                //

       for(int i=0;i<combs.size();i++)                                                                                  // Удаление повторяющихся сочетаний
           for(int i1=0;i1<combs.size();i1++)
              if((combs.get(i).getEdges().equals(combs.get(i1).getEdges()) && i!=i1) || combs.get(i1).getEdges().size() == 1) combs.remove(i1);

       for(int i=0;i<combs.size();i++)                                                                                  // Присваивание номеров сочетаниям
           combs.get(i).setNumber(i+1);
   }
    public void rt(ArrayList<Integer>[] simp,ArrayList<Integer> tmp,ArrayList<Integer> index,ArrayList<ArrayList<Integer>> edgesofcomb){         //Рекурсивный метод для пробега по упрощенной матрице смежности
        edgesofcomb.add(tmp);
        combs.add(new Combination(edgesofcomb.get(edgesofcomb.size()-1),edges));
        combs.get(combs.size()-1).sortEdges();

        for(int i=0;i<index.size();++i){
            ArrayList<Integer> tmpindex=new ArrayList<>();
            ArrayList<Integer> copy= new ArrayList<>();
            ArrayList<Integer> tmpp ;
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
        ArrayList<Vertex> tmpVerts = new ArrayList<>();
        Boolean tmpFlag = false;
        for(Combination c : combs){
            tmpVerts.clear();
            for (Edge e: c.getEdges())
                for (Vertex v: e.getVerts())
                    if(!tmpVerts.contains(v)) tmpVerts.add(v);
            tmpVerts.sort(new NumberCompVertexes());
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
        for(Vertex v : verts){
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

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public ArrayList<Vertex> getVerts() {
        return verts;
    }

    public ArrayList<Proportion> getProps() {
        return props;
    }

    public ArrayList<Combination> getCombs() {
        return combs;
    }

    public ArrayList<Combination> getPerfCombs() {
        return perfCombs;
    }

    public int[][] getMatrixofadj() {
        return matrixofadj;
    }

    public void setCombs(ArrayList<Combination> combs) {
        this.combs = combs;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public void setMatrixofadj(int[][] matrixofadj) {
        this.matrixofadj = matrixofadj;
    }

    public void setPerfCombs(ArrayList<Combination> perfCombs) {
        this.perfCombs = perfCombs;
    }

    public void setProps(ArrayList<Proportion> props) {
        this.props = props;
    }

    public void setVerts(ArrayList<Vertex> verts) {
        this.verts = verts;
    }
}
