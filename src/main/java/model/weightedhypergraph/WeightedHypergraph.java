package model.weightedhypergraph;

import model.hypergraphs.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeightedHypergraph extends Hypergraph {
    private Solution solution;
    public WeightedHypergraph(){
        super();
    }
    @Override
    public void fullLoad(){
        //метод загрузки данных из файла
        File file= new File("inputtask.txt");
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
        String[] temp = content.get(0).split(" ");
        for(int i=1;i<Integer.valueOf(temp[0])+1;i++) {
            List<String> tmpverts = new ArrayList<>(Arrays.asList((content.get(i).split(" "))));
            List<Double> tmpweights = new ArrayList<>();
            int n = Integer.valueOf(tmpverts.get(0));
            tmpverts.remove(0);
            for(int j=0;j<tmpverts.size();j++){
                tmpweights.add(Double.valueOf(tmpverts.get(j)));
            }
            this.getVerts().add(new WeightedVert(n,tmpweights));
        }

        for(int i=Integer.valueOf(temp[0])+1;i<content.size();i++){
            List<String> tmp=new ArrayList<>(Arrays.asList((content.get(i).split(" "))));
            List<Vert> tmpVert= new ArrayList<>();
            int n = Integer.valueOf(tmp.get(0));
            tmp.remove(0);
            for(int j=0;j<tmp.size();j++)
                tmpVert.add(this.getVertWithNumber(this.getVerts(),Integer.valueOf(tmp.get(j))));
            this.getEdges().add(new WeightedEdge(tmpVert,n));
        }
        this.getVerts().sort(new NumberCompVerts());
    }

    @Override
    public void hypergraphClear() {
        super.hypergraphClear();
        this.solution = null;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }
}
