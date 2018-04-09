package model.weightedhypergraph;

public class VectorFunction {
//    MAXSUM("MAXSUM"),MINSUM("MINSUM"),AVGSUM("AVGSUM"),MAXAVG("MAXAVG"),
//    MINAVG("MINAVG"),MAXMAX("MAXMAX"),MINMIN("MINMIN"),MAXMIN("MAXMIN"),
//    MINMAX("MINMAX");

    private String name;
    private int weightNumber;
    private int number;

    public VectorFunction(String name,int weightNumber){
        this.name = name;
        this.weightNumber = weightNumber;
    }
    public VectorFunction(String name){
        this.name = name;
    }


    public void setWeightNumber(int weightNumber) {
        this.weightNumber = weightNumber;
    }

    public int getWeightNumber() {
        return weightNumber;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "F"+ this.number+ " " +this.name  + " Номер веса: " + String.valueOf(this.weightNumber + 1);
    }
}
